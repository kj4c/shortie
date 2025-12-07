/*
 * SHORTIE - Root Build Configuration
 * 
 * This is a Kotlin/Spring Boot monorepo for a URL shortener + secure file drop system.
 * 
 * ARCHITECTURE OVERVIEW:
 * - api-service: REST API gateway, serves frontend, calls resource-service via gRPC
 * - resource-service: Core business logic, Postgres, Kafka events, Temporal workflows
 * - proto: Shared gRPC/Protobuf definitions
 * - frontend: React + Vite SPA
 * - infra: Docker Compose for local development
 */

plugins {
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    id("com.google.protobuf") apply false
}

// Shared configuration for all subprojects
subprojects {
    group = "com.shortie"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    // Use consistent JVM target (works with JDK 21+)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

// Version catalog - centralized dependency versions
// These are read from gradle.properties
val grpcVersion: String by project
val grpcKotlinVersion: String by project
val protobufVersion: String by project
val kotlinCoroutinesVersion: String by project
val temporalVersion: String by project

// Extra properties available to all subprojects
extra["grpcVersion"] = grpcVersion
extra["grpcKotlinVersion"] = grpcKotlinVersion
extra["protobufVersion"] = protobufVersion
extra["kotlinCoroutinesVersion"] = kotlinCoroutinesVersion
extra["temporalVersion"] = temporalVersion

