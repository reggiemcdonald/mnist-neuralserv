#!/bin/bash

# Created with reference to:
# https://github.com/mrts/docker-postgresql-multiple-databases/blob/master/create-multiple-postgresql-databases.sh

set -eu

function create_database() {
  echo "Creating $1..."
  local db_name=$1
  psql -v --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER $db_name;
    CREATE DATABASE $db_name;
    GRANT ALL PRIVILEGES ON DATABASE $db_name TO $db_name;
EOSQL
  psql --username "$POSTGRES_USER" -d "$db_name" -a -f ./docker-entrypoint-initdb.d/sql/schema.sql
  psql --username "$POSTGRES_USER" -d "$db_name" -a -f ./docker-entrypoint-initdb.d/sql/authorities.sql

}

if [[ -n $POSTGRES_MULTIPLE_DATABASES ]]; then
  for database in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
    create_database "$database"
  done
fi
