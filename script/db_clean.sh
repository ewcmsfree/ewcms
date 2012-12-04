#!/bin/bash
#
#clean backup file script
DIR=/home/backup
FILE="*.tar.gz"
DAY=15
echo "start clean backup database file..."
find ${DIR} -name ${FILE} -mtime +${DAY} -exec rm -f "{}" \;
echo "clean backup database file end."
