package com.shortie.api.config

import com.shortie.proto.ResourceServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PreDestroy

/**
 * gRPC Client Configuration
 * 
 * IMPLEMENTATION STEPS:
 * 1. Create ManagedChannel to resource-service
 * 2. Create blocking and async stubs
 * 3. Handle graceful shutdown
 * 
 * TODO:
 * - Add retry policies
 * - Add deadline/timeout configuration
 * - Add TLS for production
 * - Add health check / circuit breaker
 */
@Configuration
class GrpcClientConfig {

    @Value("\${grpc.resource-service.host:localhost}")
    private lateinit var resourceServiceHost: String

    @Value("\${grpc.resource-service.port:9090}")
    private var resourceServicePort: Int = 9090

    private var channel: ManagedChannel? = null

    @Bean
    fun resourceServiceChannel(): ManagedChannel {
        // TODO: Configure channel with:
        // - Keep-alive settings
        // - Max message size
        // - Interceptors for logging/tracing
        // - TLS credentials for production
        channel = ManagedChannelBuilder
            .forAddress(resourceServiceHost, resourceServicePort)
            .usePlaintext() // TODO: Use TLS in production
            .build()
        return channel!!
    }

    @Bean
    fun resourceServiceBlockingStub(channel: ManagedChannel): ResourceServiceGrpc.ResourceServiceBlockingStub {
        // TODO: Add deadline interceptor
        return ResourceServiceGrpc.newBlockingStub(channel)
    }

    @PreDestroy
    fun shutdownChannel() {
        // TODO: Graceful shutdown with timeout
        channel?.shutdown()
    }
}

