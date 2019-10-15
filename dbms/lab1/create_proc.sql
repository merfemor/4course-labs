-- RETURNs count of numeric columns of table 'tableName'
CREATE OR REPLACE FUNCTION numberColumns(
    tableName IN VARCHAR2
) RETURN NUMBER IS
    counter NUMBER := 0;
BEGIN
    SELECT count(*) INTO counter FROM All_TAB_COLUMNS WHERE TABLE_NAME = tableName AND DATA_TYPE = 'NUMBER';
    RETURN counter;
END;
/


-- RETURNs count of numeric columns of table 'tableName' which are not primary-key columns
CREATE OR REPLACE FUNCTION nonPrimaryNumberColumns(
    tableName IN VARCHAR2
) RETURN NUMBER IS
    counter NUMBER := 0;
BEGIN
    SELECT count(*)
    INTO counter
    FROM All_TAB_COLUMNS
    WHERE TABLE_NAME = tableName
      AND DATA_TYPE = 'NUMBER'
      AND ALL_TAB_COLUMNS.COLUMN_NAME NOT IN (
        SELECT COLUMN_NAME
        FROM ALL_CONS_COLUMNS
        WHERE CONSTRAINT_NAME IN
              (SELECT CONSTRAINT_NAME FROM ALL_CONSTRAINTS WHERE CONSTRAINT_TYPE = 'P' AND TABLE_NAME = tableName));
    RETURN counter;
END;
/


-- converts number to date, interpreting number as unix time in seconds
CREATE OR REPLACE FUNCTION unix_to_date(unix_sec NUMBER) RETURN DATE IS
    ret_date DATE;
BEGIN
    ret_date := TO_DATE('19700101', 'YYYYMMDD') + (1 / 24 / 60 / 60) * unix_sec;
    RETURN ret_date;
END;
/


CREATE or REPLACE FUNCTION checkTable(
    tableName IN VARCHAR2
) RETURN NUMBER IS
    check_table NUMBER;
BEGIN
    SELECT count(*) INTO check_table FROM ALL_TABLES WHERE TABLE_NAME = tableName;
    RETURN check_table;
END;


CREATE OR REPLACE
    PROCEDURE addDateColumnsToNumbers(tableName VARCHAR2) AS
    new_columns_num NUMBER;
    new_column_name VARCHAR(256);
    chTable         NUMBER := checkTable(tableName);
    --Exceptions
    incorrect_table_name EXCEPTION;
BEGIN
    IF chTable != 1 THEN
        RAISE incorrect_table_name;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Таблица: ' || tableName);
    DBMS_OUTPUT.PUT_LINE('Целочисленных столбцов: ' || numberColumns(tableName));

    new_columns_num := 0;
    FOR col IN (
        SELECT COLUMN_NAME
        FROM All_TAB_COLUMNS
        WHERE TABLE_NAME = tableName
          AND DATA_TYPE = 'NUMBER'
          AND ALL_TAB_COLUMNS.COLUMN_NAME NOT IN (
            SELECT COLUMN_NAME
            FROM ALL_CONS_COLUMNS
            WHERE CONSTRAINT_NAME in (
                SELECT CONSTRAINT_NAME
                FROM ALL_CONSTRAINTS
                WHERE CONSTRAINT_TYPE = 'P'
                  AND TABLE_NAME = tableName
            )
        )
        )
        LOOP
            new_column_name := col.COLUMN_NAME || '_DATE';
            EXECUTE IMMEDIATE  'ALTER TABLE ' || tableName || ' ADD ' || new_column_name || ' DATE';
            EXECUTE IMMEDIATE 'UPDATE ' || tableName || ' SET ' || new_column_name ||
                              ' = unix_to_date(' || col.COLUMN_NAME || ')';
            new_columns_num := new_columns_num + 1;

        END LOOP;
    DBMS_OUTPUT.PUT_LINE('Столбцов добавлено: ' || new_columns_num);

EXCEPTION
    WHEN incorrect_table_name THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: table with name ' || tableName || ' does not exist');
    WHEN OTHERS THEN
        IF sqlcode = -1430 THEN DBMS_OUTPUT.PUT_LINE('ERROR: table is already modified'); END IF;
        IF sqlcode = -942 THEN DBMS_OUTPUT.PUT_LINE('ERROR: you do not have rights on this table'); END IF;
END;
/