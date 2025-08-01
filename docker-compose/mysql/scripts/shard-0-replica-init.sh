#!/bin/bash
set -e

echo "Waiting for master to be ready..."
until mysql -h url-db-shard-0-master -u root -p"$MYSQL_ROOT_PASSWORD" -e "SELECT 1" &>/dev/null; do
  echo "Waiting for master..."
  sleep 2
done

echo "Configuring replication for shard 0..."

# Check binary logging
mysql -h url-db-shard-0-master -u root -p"$MYSQL_ROOT_PASSWORD" -e "SHOW VARIABLES LIKE 'log_bin';"

# Get master status (MySQL 8+ syntax)
MASTER_STATUS=$(mysql -h url-db-shard-0-master -u root -p"$MYSQL_ROOT_PASSWORD" -e "SHOW BINARY LOG STATUS" --skip-column-names --raw 2>/dev/null)
echo "Master status output: $MASTER_STATUS"

if echo "$MASTER_STATUS" | grep -q "ERROR"; then
  echo "ERROR: Could not get master status"
  exit 1
fi

# Parse the raw output
FILE=$(echo "$MASTER_STATUS" | awk '{print $1}')
POS=$(echo "$MASTER_STATUS" | awk '{print $2}')

if [ -z "$FILE" ] || [ -z "$POS" ]; then
  echo "ERROR: Could not extract log file or position from master status"
  exit 1
fi

echo "Master log file: $FILE, position: $POS"

# Set replication defaults if not provided
REPL_USER=${REPL_USER:-root}
REPL_PASSWORD=${REPL_PASSWORD:-$MYSQL_ROOT_PASSWORD}

mysql -u root -p"$MYSQL_ROOT_PASSWORD" <<EOF
STOP REPLICA;
CHANGE REPLICATION SOURCE TO
  SOURCE_HOST='url-db-shard-0-master',
  SOURCE_USER='$REPL_USER',
  SOURCE_PASSWORD='$REPL_PASSWORD',
  SOURCE_LOG_FILE='$FILE',
  SOURCE_LOG_POS=$POS;
START REPLICA;
EOF

echo "Replication for shard 0 configured successfully."