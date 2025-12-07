#!/bin/bash
# SHORTIE - Start Development Environment
#
# This script starts all services needed for local development.
#
# USAGE:
#   ./dev-up.sh           # Start core services
#   ./dev-up.sh --tools   # Start with development tools (Kafka UI)
#   ./dev-up.sh --build   # Rebuild images before starting
#
# SERVICES STARTED:
# - postgres (port 5432)
# - kafka (port 9092)
# - temporal (port 7233, UI: 8088)
# - resource-service (port 8081, gRPC: 9090)
# - api-service (port 8080)
#
# TODO:
# - Add wait-for-healthy logic
# - Add colored output
# - Add service status check

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "🚀 Starting Shortie development environment..."

# Parse arguments
BUILD_FLAG=""
PROFILE_FLAG=""

for arg in "$@"; do
    case $arg in
        --build)
            BUILD_FLAG="--build"
            ;;
        --tools)
            PROFILE_FLAG="--profile tools"
            ;;
    esac
done

# Start infrastructure services first
echo "📦 Starting infrastructure services (postgres, kafka, temporal)..."
docker-compose up -d postgres zookeeper kafka temporal temporal-ui $BUILD_FLAG

# Wait for postgres to be healthy
echo "⏳ Waiting for Postgres to be ready..."
until docker-compose exec -T postgres pg_isready -U shortie > /dev/null 2>&1; do
    sleep 1
done
echo "✅ Postgres is ready"

# Wait for Kafka to be ready
echo "⏳ Waiting for Kafka to be ready..."
sleep 5  # Simple wait for Kafka startup

# Wait for Temporal to be ready
echo "⏳ Waiting for Temporal to be ready..."
sleep 10  # Simple wait for Temporal startup

# Start application services
echo "🔧 Starting application services..."
docker-compose up -d resource-service api-service $BUILD_FLAG $PROFILE_FLAG

echo ""
echo "✅ Shortie is starting up!"
echo ""
echo "📋 Service URLs:"
echo "   API Service:     http://localhost:8080"
echo "   API Health:      http://localhost:8080/actuator/health"
echo "   Resource Health: http://localhost:8081/actuator/health"
echo "   Temporal UI:     http://localhost:8088"
echo ""
echo "📊 Infrastructure:"
echo "   Postgres:        localhost:5432 (user: shortie, pass: shortie)"
echo "   Kafka:           localhost:9092"
echo "   Temporal:        localhost:7233"
echo ""
echo "💡 Useful commands:"
echo "   View logs:       docker-compose logs -f [service]"
echo "   Stop all:        ./dev-down.sh"
echo "   Rebuild:         ./dev-up.sh --build"
echo ""

# If tools profile is enabled, show additional URLs
if [[ "$PROFILE_FLAG" == *"tools"* ]]; then
    echo "🛠️  Development Tools:"
    echo "   Kafka UI:        http://localhost:8089"
    echo ""
fi

