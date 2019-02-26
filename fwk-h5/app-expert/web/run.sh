#!/bin/sh
set -- "$@" '$AG_MONGO_ISS_DATABASE'
set -- "$@" '$AG_MONGO_ISS_BASE'
set -- "$@" '$AG_MONGO_IOT_DATABASE'
set -- "$@" '$AG_MONGO_IOT_BASE'
set -- "$@" '$AG_MONGO_FWK_DATABASE'
set -- "$@" '$AG_MONGO_FWK_BASE'
set -- "$@" '$AG_REDIS_BASE'
set -- "$@" '$AG_GRPC_WS'
set -- "$@" '$AG_OAUTH2_SERVER'
set -- "$@" '$AG_OAUTH2_CLIENT_ID'
set -- "$@" '$AG_FS_SERVER'
set -- "$@" '$AG_ISS_SERVER'
set -- "$@" '$AG_ISS_PROVINCE_ID'
set -- "$@" '$AG_ISS_PROVINCE_NAME'
set -- "$@" '$AG_ISS_PROVINCE_CODE'
set -- "$@" '$AG_ISS_PROVINCE_LAT'
set -- "$@" '$AG_ISS_PROVINCE_LNG'
set -- "$@" '$AG_FWK_SERVER'
set -- "$@" '$AG_YS7_SERVER'
set -- "$@" '$AG_YS7_APP_KEY'
set -- "$@" '$AG_YS7_APP_SECRET'
HTML_DIR=/usr/share/nginx/html
DIST_DIR=/usr/share/nginx/dist
rm -rf $HTML_DIR
cp -rf $DIST_DIR $HTML_DIR
for FILE in $(find $HTML_DIR -name *.js); do
  FILE_TPL=$FILE.tpl
  for ENV in "$@"; do
    mv $FILE $FILE_TPL
    envsubst $ENV < $FILE_TPL > $FILE
    rm -rf $FILE_TPL
  done
done
nginx -g 'daemon off;'
