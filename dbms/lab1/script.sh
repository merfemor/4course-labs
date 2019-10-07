#!/bin/bash

echo Enter TableName
read tablename

sqlplus s225102/4dctpYPF @main.sql $tablename
