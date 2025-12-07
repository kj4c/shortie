/*
 * PROTO Module - gRPC/Protobuf Definitions
 * 
 * This module contains the shared .proto files and generates:
 * - Java/Kotlin message classes
 * - gRPC service stubs (both blocking and coroutine-based)
 * 
 * IMPLEMENTATION STEPS:
 * 1. Define message types in src/main/proto/resource.proto
 * 2. Run `./gradlew :proto:generateProto` to generate code
 * 3. Generated code appears in build/generated/source/proto/
 * 4. Both api-service and resource-service depend on this module
 */

import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm")
    id("com.google.protobuf")
}

val grpcVersion: String by rootProject.extra
val grpcKotlinVersion: String by rootProject.extra
val protobufVersion: String by rootProject.extra
val kotlinCoroutinesVersion: String by rootProject.extra

dependencies {
    // Protobuf - BOTH are required for Kotlin DSL support
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
    
    // gRPC
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    
    // Kotlin Coroutines (for gRPC-Kotlin)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    
    // Required for generated code
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("kotlin")
            }
            task.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}


// Make sure generated sources are included in the source set
sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/grpckt",
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

