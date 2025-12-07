package com.shortie.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * API Service - REST Gateway Application
 *
 * This is the entry point for the api-service.
 *
 * STARTUP SEQUENCE:
 * 1. Spring Boot initializes
 * 2. gRPC client connects to resource-service
 * 3. REST endpoints become available
 * 4. Static files (frontend) are served from /static
 *
 * ENDPOINTS EXPOSED:
 * - POST /api/resources/url   - Shorten a URL
 * - POST /api/resources/file  - Upload a file
 * - GET  /s/{shortCode}       - Resolve and redirect/download
 * - GET  /                    - Serve frontend SPA
 */
@SpringBootApplication
class ApiServiceApplication

fun main(args: Array<String>) {
    runApplication<ApiServiceApplication>(*args)
}
