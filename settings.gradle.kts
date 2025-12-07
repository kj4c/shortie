/*
 * SHORTIE - URL Shortener + Secure File Drop System
 * 
 * This is the root settings file that defines the multi-module project structure.
 * 
 * IMPLEMENTATION ORDER:
 * 1. First, set up the proto/ module - this generates the gRPC stubs both services need
 * 2. Then implement resource-service - it owns the database and business logic
 * 3. Next implement api-service - it exposes REST endpoints and calls resource-service
 * 4. Finally, build the frontend
 */

rootProject.name = "shortie"

// Include all subprojects
include(
    "proto",
    "api-service",
    "resource-service"
)

// Plugin management - centralized plugin versions
pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val protobufPluginVersion: String by settings
    
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version "1.1.4"
        id("com.google.protobuf") version protobufPluginVersion
    }
    
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

// Dependency resolution management
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

