#!/bin/bash
#
# www backup script
#
TIME="$(date +"%Y-%m-%d")"
FILENAME=www_#TIME
WWWDIR=/home/www
BAKDIR=/backup
BAKFILE=${BACKDIR}/$FILENAME.tar.gz

echo "start backup www site ..."
cd ${WWWDIR}
tar -zcf ${BAKFILE} .
echo "backup www site end."
