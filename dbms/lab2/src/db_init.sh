#!/usr/bin/env bash

base_dir=/u01/ged38

db_name=${1:-leftlake}
dba_pwd=${2:-admin}

orapwd_dir="${base_dir}/orapwd"
oradata="${base_dir}/${db_name}"
oractl_dir="${base_dir}/${db_name}/ora_control"
db_recovery_file_dest="${base_dir}/recovery/${db_name}/flash_recovery_area"


if [ ! -e "$oradata" ] ; then
    mkdir -p "${oradata}/logs"

    mkdir -p "${oradata}/node01"
    mkdir -p "${oradata}/node02"
    mkdir -p "${oradata}/node03"
    mkdir -p "${oradata}/node04"
fi

if [ ! -e "$orapwd" ] ; then
    mkdir -p "$orapwd_dir"
fi

if [ ! -e "$db_recovery_file_dest" ] ; then
    mkdir -p "$db_recovery_file_dest"
fi

cat << EOF > "${ORACLE_HOME}/dbs/init${ORACLE_SID}.ora"
db_name='$db_name'
db_block_size=4096
sga_target=440M
audit_trail='db'
processes=150
memory_target=1G
db_recovery_file_dest='${db_recovery_file_dest}'
db_recovery_file_dest_size=2G
dispatchers='(PROTOCOL=TCP) (SERVICE=ORCLXDB)'
open_cursors=300
remote_login_passwordfile='EXCLUSIVE'
undo_tablespace='UNDOTBS1'
control_files=(${oractl_dir})
compatible='11.2.0'
EOF

cat << EOF > "create_db.sql"
CREATE DATABASE $db_name
USER SYS IDENTIFIED BY $dba_pwd
USER SYSTEM IDENTIFIED BY $dba_pwd
LOGFILE GROUP 1 ('$oradata/logs/redo01.log') SIZE 100 M,
GROUP 2 ('$oradata/logs/redo02.log') SIZE 100 M,
GROUP 3 ('$oradata/logs/redo03.log') SIZE 100 M
MAXLOGFILES 5
MAXLOGMEMBERS 5
MAXLOGHISTORY 1
MAXDATAFILES 100
CHARACTER SET UTF8
NATIONAL CHARACTER SET UTF8
—
EXTENT MANAGEMENT LOCAL DATAFILE
'$oradata/node03/ejuki23.dbf' SIZE 200 M REUSE,
'$oradata/node04/avupu17.dbf' SIZE 200 M REUSE,
'$oradata/node02/ebowe91.dbf' SIZE 200 M REUSE
—
SYSAUX DATAFILE
'$oradata/node03/jaw84.dbf' SIZE 200 M REUSE AUTOEXTEND ON
—
DEFAULT TABLESPACE USERS DATAFILE
'$oradata/node04/oyawaha166.dbf' SIZE 50 M REUES AUTOEXTEND ON MAXSIZE UNLIMITED
DEFAULT TEMPORARY TABLESPACE TEMP
TEMPFILE '$oradata/temp01.dbf'
SIZE 20 M REUSE
UNDO TABLESPACE UNDOTBS1
DATAFILE '$oradata/undotbs01.dbf'
SIZE 200 M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;
EOF

cat << EOF > "create_tbs.sql"
CREATE TABLESPACE EASY_ORANGE_CITY
DATAFILE '$oradata/node02/easyorangecity01.dbf' SIZE 10 M,
         '$oradata/node01/easyorangecity02.dbf' SIZE 10 M;
CREATE TABLESPACE GOOD_BLUE_FOOD
DATAFILE '$oradata/node01/goodbluefood01.dbf' SIZE 10 M;
CREATE TABLESPACE LEFT_WHITE_SONG
DATAFILE '$oradata/node03/leftwhitesong01.dbf' SIZE 10 M,
EOF
