-- returns count of numeric columns of table 'tableName'
create or replace function numberColumns(
    tableName in varchar2
) return number is
    counter number := 0;
begin
    SELECT count(*) into counter FROM All_TAB_COLUMNS WHERE TABLE_NAME = tableName AND DATA_TYPE = 'NUMBER';
    return counter;
end;
/


-- returns count of numeric columns of table 'tableName' which are not primary-key columns
create or replace function nonPrimaryNumberColumns(
    tableName in varchar2
) return number is
    counter number := 0;
begin
    SELECT count(*)
    into counter
    FROM All_TAB_COLUMNS
    WHERE TABLE_NAME = tableName
      AND DATA_TYPE = 'NUMBER'
      AND ALL_TAB_COLUMNS.COLUMN_NAME NOT IN (
        SELECT COLUMN_NAME
        FROM ALL_CONS_COLUMNS
        WHERE CONSTRAINT_NAME IN
              (SELECT CONSTRAINT_NAME FROM ALL_CONSTRAINTS WHERE CONSTRAINT_TYPE = 'P' AND TABLE_NAME = tableName));
    return counter;
end;
/


-- converts number to date, interpreting number as unix time in seconds
CREATE OR REPLACE FUNCTION unix_to_date(unix_sec NUMBER) RETURN DATE IS
    ret_date DATE;
BEGIN
    ret_date := TO_DATE('19700101', 'YYYYMMDD') + (1 / 24 / 60 / 60) * unix_sec;
    RETURN ret_date;
END;
/


CREATE OR REPLACE
    PROCEDURE addDateColumnsToNumbers(table_name VARCHAR2) AS
    new_columns_num    NUMBER;
    new_column_name    VARCHAR(256);
BEGIN
    DBMS_OUTPUT.PUT_LINE('Таблица: ' || table_name);
    DBMS_OUTPUT.PUT_LINE('Целочисленных столбцов: ' || numberColumns(table_name));

    new_columns_num := 0;
    FOR col IN (
        SELECT COLUMN_NAME
        FROM USER_TAB_COLUMNS
        WHERE TABLE_NAME = table_name
          AND DATA_TYPE = 'NUMBER'
          AND USER_TAB_COLUMNS.COLUMN_NAME NOT IN (
            SELECT COLUMN_NAME
            FROM ALL_CONS_COLUMNS
            WHERE CONSTRAINT_NAME in (
                SELECT CONSTRAINT_NAME
                FROM ALL_CONSTRAINTS
                WHERE CONSTRAINT_TYPE = 'P'
                  AND TABLE_NAME = table_name
            )
        )
        )
        LOOP
            BEGIN
                new_column_name := col.COLUMN_NAME || '_DATE';

                -- FIXME: now for debug, only print commands instead of executing
                DBMS_OUTPUT.PUT_LINE('ALTER TABLE ' || table_name || ' ADD ' || new_column_name || ' DATE');
                DBMS_OUTPUT.PUT_LINE('UPDATE ' || table_name || ' SET ' || new_column_name ||
                                     ' = unix_to_date(' || col.COLUMN_NAME || ')');

                --execute immediate 'ALTER TABLE ' || table_name || ' ADD ' || new_column_name || ' DATE';
                --execute immediate 'UPDATE ' || table_name || ' SET ' || new_column_name ||
                -- ' = unix_to_date(' || col.COLUMN_NAME || ')';
                new_columns_num := new_columns_num + 1;
            END;
        END LOOP;
    DBMS_OUTPUT.PUT_LINE('Столбцов добавлено: ' || new_columns_num);
END;
/
