#!/bin/bash

export $(grep -v '^#' .env | xargs)

MOUNT_MYSQL_DIR="$PWD/var/data/mysql"

if [ ! -d "$MOUNT_MYSQL_DIR" ]; then
    mkdir -p "$MOUNT_MYSQL_DIR";
    chmod 777 "$MOUNT_MYSQL_DIR";
fi

sudo docker-compose -f mysql.docker-compose.yml up -d
