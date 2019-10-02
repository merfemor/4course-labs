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


