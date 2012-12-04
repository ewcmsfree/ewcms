#!/bin/bash
#
#clean backup file script
DIR=/home/backup
FILE="www_*.tar.gz"
DAY=1
echo "start clean backup database file..."
find ${DIR} -name ${FILE} -mtime +${DAY} -exec rm -f "{}" \;
echo "clean backup database file end."
