# Shortie - URL Shortener & Secure File Drop

A secure file drop and URL shortener system built with Kotlin, Spring Boot, and React.

## Features

- **Shorten URLs** - Create short, memorable links
- **Upload Files** - Share files securely with short links
- **Expiration** - Set optional expiration times
- **Visit Limits** - Set maximum visit/download counts
- **Events** - Kafka events for analytics and integrations

## Architecture

```
┌─────────────────┐     ┌──────────────────┐     ┌─────────────┐
│   React SPA     │────▶│   api-service    │────▶│  resource   │
│   (frontend/)   │     │   REST + Files   │gRPC │   service   │
└─────────────────┘     └──────────────────┘     └──────┬──────┘
                                                        │
                        ┌───────────────────────────────┼───────────┐
                        │                               │           │
                        ▼                               ▼           ▼
                 ┌─────────────┐              ┌─────────────┐ ┌──────────┐
                 │  Postgres   │              │   Kafka     │ │ Temporal │
                 │  Database   │              │   Events    │ │ Workflows│
                 └─────────────┘              └─────────────┘ └──────────┘
```

## Project Structure

```
shortie/
├── api-service/          # REST API gateway (Spring Boot)
│   └── src/main/kotlin/  # Controllers, gRPC client, file handling
├── resource-service/     # Core business logic (Spring Boot)
│   └── src/main/kotlin/  # Entities, services, gRPC server, Temporal
├── proto/                # gRPC/Protobuf definitions
│   └── src/main/proto/   # resource.proto
├── frontend/             # React + Vite SPA
│   └── src/              # Components, styles
├── infra/                # Docker & deployment
│   ├── docker-compose.yml
│   ├── Dockerfile.*
│   └── dev-*.sh
└── build.gradle.kts      # Root Gradle config
```

## Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 1.9 |
| Framework | Spring Boot 3.2 |
| Database | PostgreSQL 16 |
| Messaging | Apache Kafka |
| Workflows | Temporal |
| RPC | gRPC |
| Frontend | React 18 + TypeScript + Vite |
| Containerization | Docker |

## Getting Started

### Prerequisites

- JDK 21
- Docker & Docker Compose
- Node.js 18+ (for frontend development)

### Quick Start with Docker

```bash
# Start all services
cd infra
./dev-up.sh

# View logs
docker-compose logs -f api-service resource-service

# Stop services
./dev-down.sh
```

### Local Development

#### 1. Generate gRPC Code

```bash
./gradlew :proto:generateProto
```

#### 2. Start Infrastructure

```bash
cd infra
docker-compose up -d postgres kafka temporal temporal-ui
```

#### 3. Run Services

```bash
# Terminal 1: Resource Service
./gradlew :resource-service:bootRun

# Terminal 2: API Service
./gradlew :api-service:bootRun
```

#### 4. Run Frontend

```bash
cd frontend
npm install
npm run dev
```

### Service URLs

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| API Service | http://localhost:8080 |
| Resource Service | http://localhost:8081 |
| Temporal UI | http://localhost:8088 |
| Kafka UI | http://localhost:8089 (with --tools) |

## API Endpoints

### Create Shortened URL

```bash
POST /api/resources/url
Content-Type: application/json

{
  "url": "https://example.com/very/long/url",
  "maxVisits": 100,
  "expiresInHours": 24
}
```

### Upload File

```bash
POST /api/resources/file
Content-Type: multipart/form-data

file: <binary>
maxVisits: 10
expiresInHours: 48
```

### Access Short Link

```bash
GET /s/{shortCode}
# Returns: 302 Redirect (URL) or File Download (FILE)
```

## Implementation Status

### Phase 1: Scaffolding ✅
- [x] Project structure
- [x] Gradle multi-module setup
- [x] Proto definitions
- [x] Entity classes
- [x] Docker Compose
- [x] Frontend scaffold

### Phase 2: Core Logic (TODO)
- [ ] Short code generation
- [ ] Database operations
- [ ] gRPC implementation
- [ ] REST endpoints
- [ ] File upload handling

### Phase 3: Features (TODO)
- [ ] Temporal expiry workflows
- [ ] Kafka event publishing
- [ ] Visit counting
- [ ] Limit enforcement

### Phase 4: Polish (TODO)
- [ ] Frontend API integration
- [ ] Error handling
- [ ] Logging & metrics
- [ ] Testing

## Implementation Guide

### Step 1: Proto Generation

First, generate the gRPC stubs:

```bash
./gradlew :proto:generateProto
```

This creates Kotlin classes in `proto/build/generated/`.

### Step 2: Resource Service

Implement in this order:

1. **ShortCodeGenerator** - Generate unique codes
2. **ResourceRepository** - Database operations
3. **ResourceService** - Business logic
4. **ResourceGrpcService** - gRPC server implementation
5. **KafkaEventPublisher** - Event publishing
6. **TemporalConfig** - Workflow setup
7. **ResourceExpiryWorkflowImpl** - Expiry logic

### Step 3: API Service

1. **GrpcClientConfig** - Connect to resource-service
2. **ResourceApiService** - Call gRPC, handle responses
3. **ResourceController** - REST endpoints
4. **RedirectController** - Short code resolution
5. **File upload handling** - Save files, track paths

### Step 4: Frontend

1. **API service** - HTTP client for backend
2. **Form validation** - Client-side checks
3. **Loading states** - User feedback
4. **Error handling** - Display errors nicely

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `POSTGRES_HOST` | localhost | Database host |
| `POSTGRES_PORT` | 5432 | Database port |
| `POSTGRES_DB` | shortie | Database name |
| `POSTGRES_USER` | shortie | Database user |
| `POSTGRES_PASSWORD` | shortie | Database password |
| `KAFKA_BOOTSTRAP_SERVERS` | localhost:9092 | Kafka brokers |
| `TEMPORAL_ADDRESS` | localhost:7233 | Temporal server |
| `GRPC_PORT` | 9090 | gRPC server port |
| `UPLOAD_DIR` | /uploads | File storage path |

## Kafka Topics

| Topic | Description |
|-------|-------------|
| `resource.created` | New resource created |
| `resource.downloaded` | Resource accessed |
| `resource.expired` | Resource expired |

## License

MIT
