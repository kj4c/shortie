/*
 * API-SERVICE Module - REST API Gateway
 * 
 * This service is the public-facing gateway that:
 * - Exposes REST endpoints for creating resources and resolving short codes
 * - Handles file uploads and stores them locally
 * - Calls resource-service via gRPC for business logic
 * - Serves the production React frontend bundle
 * 
 * IMPLEMENTATION STEPS:
 * 1. Configure Spring Boot with WebMVC
 * 2. Set up gRPC client to connect to resource-service
 * 3. Implement REST controllers
 * 4. Configure file upload handling
 * 5. Set up static resource serving for frontend
 */

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val grpcVersion: String by rootProject.extra
val kotlinCoroutinesVersion: String by rootProject.extra

dependencies {
    // Project dependency - gRPC stubs
    implementation(project(":proto"))
    
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinCoroutinesVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    // gRPC Client
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.bootJar {
    archiveFileName.set("api-service.jar")
}

// Copy frontend build to resources for serving
// TODO: Uncomment when frontend is built
// tasks.processResources {
//     dependsOn(":frontend:build")
//     from("../frontend/dist") {
//         into("static")
//     }
// }

