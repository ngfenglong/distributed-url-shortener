#!/bin/bash
set -e

echo "Setting up master database for shard 0..."

# Wait for MySQL to be ready
echo "Waiting for MySQL to be ready..."
until mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "SELECT 1" &>/dev/null; do
  echo "Waiting for MySQL..."
  sleep 2
done

# Create replication user
mysql -u root -p"$MYSQL_ROOT_PASSWORD" <<EOF
CREATE USER IF NOT EXISTS 'repl_user'@'%' IDENTIFIED BY 'repl_password';
GRANT REPLICATION SLAVE ON *.* TO 'repl_user'@'%';
FLUSH PRIVILEGES;
EOF

echo "Master setup for shard 0 completed."