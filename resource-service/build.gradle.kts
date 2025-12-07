/*
 * RESOURCE-SERVICE Module - Core Business Logic
 * 
 * This service is the backend that:
 * - Manages the Postgres database (resources table)
 * - Implements gRPC server for api-service to call
 * - Runs Temporal workflows for expiration
 * - Emits Kafka events for created/downloaded/expired
 * - Generates unique short codes
 * 
 * IMPLEMENTATION STEPS:
 * 1. Configure Spring Boot with JPA/Postgres
 * 2. Set up gRPC server
 * 3. Implement database entities and repositories
 * 4. Implement gRPC service methods
 * 5. Set up Kafka producer
 * 6. Implement Temporal workflows and activities
 */

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val grpcVersion: String by rootProject.extra
val kotlinCoroutinesVersion: String by rootProject.extra
val temporalVersion: String by rootProject.extra

dependencies {
    // Project dependency - gRPC stubs
    implementation(project(":proto"))
    
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Database
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql") // Required for Flyway 10+ with PostgreSQL
    
    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
    
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    // gRPC Server
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-services:$grpcVersion") // For health checks
    
    // Temporal
    implementation("io.temporal:temporal-sdk:$temporalVersion")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:postgresql:1.19.3")
    testImplementation("org.testcontainers:kafka:1.19.3")
}

tasks.bootJar {
    archiveFileName.set("resource-service.jar")
}

