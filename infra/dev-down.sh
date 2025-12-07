#!/bin/bash
# SHORTIE - Stop Development Environment
#
# This script stops all services and optionally cleans up data.
#
# USAGE:
#   ./dev-down.sh           # Stop services, keep data
#   ./dev-down.sh --clean   # Stop services and remove volumes

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "🛑 Stopping Shortie development environment..."

# Check for clean flag
if [[ "$1" == "--clean" ]]; then
    echo "🧹 Removing volumes and data..."
    docker-compose down -v --remove-orphans
    echo "✅ All services stopped and data removed"
else
    docker-compose down --remove-orphans
    echo "✅ All services stopped (data preserved)"
    echo ""
    echo "💡 To remove all data, run: ./dev-down.sh --clean"
fi

