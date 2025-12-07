package com.shortie.resource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Resource Service - Core Business Logic Application
 * 
 * This is the entry point for the resource-service.
 * 
 * STARTUP SEQUENCE:
 * 1. Spring Boot initializes
 * 2. Flyway runs database migrations
 * 3. JPA entities are mapped
 * 4. Kafka producer is configured
 * 5. Temporal worker is started
 * 6. gRPC server starts listening
 * 
 * RESPONSIBILITIES:
 * - Database CRUD operations for resources
 * - Short code generation
 * - Expiration workflow management
 * - Event publishing
 */
@SpringBootApplication
class ResourceServiceApplication

fun main(args: Array<String>) {
    runApplication<ResourceServiceApplication>(*args)
}

