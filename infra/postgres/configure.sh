#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local DB=$(echo "$1" | tr ':' ' ' | awk '{print $1}')
	local SCHEMA=$(echo "$1" | tr ':' ' ' | awk '{print $2}')
	local OWNER=$(echo "$1" | tr ':' ' ' | awk '{print $3}')
	echo "DB: '$DB', SCHEMA: '$SCHEMA', OWNER: '$OWNER'"

	if psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_roles WHERE rolname='$OWNER'" | grep -q 1; then
		echo "User $OWNER already exists"
	else
		if ! psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -tAc "CREATE USER $OWNER;"; then
			echo "Failed to create user $OWNER"
			exit 1
		fi
	fi

	if psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_database WHERE datname='$DB'" | grep -q 1; then
		echo "Database '$DB' already exists"
	else
		if ! psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "CREATE DATABASE $DB;"; then
			echo "Failed creating '$DB'"
			exit 1
		fi
	fi

	if ! psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -tAc "GRANT ALL PRIVILEGES ON DATABASE $DB TO $OWNER;"; then
		echo "Failed to grant privileges to user $OWNER on database $DB"
		exit 1
	fi

	if [ -n "$SCHEMA" ]; then
		if ! psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$DB" -c "CREATE SCHEMA IF NOT EXISTS $SCHEMA AUTHORIZATION $OWNER"; then
			echo "Failed to create schema '$SCHEMA'"
			exit 1
		fi
	fi
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple DB creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for PROPERTIES in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
		echo "Running properties '$PROPERTIES'"
		create_user_and_database "$PROPERTIES"
	done
	echo "Multiple databases created"
fi
