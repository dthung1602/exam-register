#!/usr/bin/env bash

BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
SCHEMA_FILE="${BASE_DIR}/schema.sql"
PROCEDURE_FILE="${BASE_DIR}/procedure.sql"

function runsql() {
    mysql -u "${DB_USERNAME}" -p"${DB_PASSWORD}" < "$1"
    code=$?
    if [[ ${code} != 0 ]]; then
        echo ">>> Failed"
        exit ${code}
    else
        echo ">>> Done"
    fi
}

echo ">>> Creating schema"
runsql "${SCHEMA_FILE}"

echo ">>> Creating procedures"
runsql "${PROCEDURE_FILE}"