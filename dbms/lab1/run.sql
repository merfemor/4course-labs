declare
begin
    addDateColumnsToNumbers('DOG');
end;
/


SELECT COLUMN_NAME
        FROM All_TAB_COLUMNS
        WHERE TABLE_NAME = 'Н_ЛЮДИ'
          AND DATA_TYPE = 'NUMBER'
          AND ALL_TAB_COLUMNS.COLUMN_NAME NOT IN (
            SELECT COLUMN_NAME
            FROM ALL_CONS_COLUMNS
            WHERE CONSTRAINT_NAME in (
                SELECT CONSTRAINT_NAME
                FROM ALL_CONSTRAINTS
                WHERE CONSTRAINT_TYPE = 'P'
                  AND TABLE_NAME = 'Н_ЛЮДИ'
            )
        )

