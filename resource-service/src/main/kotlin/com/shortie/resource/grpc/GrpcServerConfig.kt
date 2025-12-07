package com.shortie.resource.grpc

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * gRPC Server Configuration
 * 
 * Configures and starts the gRPC server.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Create ServerBuilder with configured port
 * 2. Register service implementations
 * 3. Add interceptors (logging, auth, etc.)
 * 4. Start server on application startup
 * 5. Graceful shutdown on application stop
 * 
 * TODO:
 * - Add TLS configuration for production
 * - Add health check service
 * - Add metrics interceptor
 * - Add authentication interceptor
 */
@Configuration
class GrpcServerConfig(
    private val resourceGrpcService: ResourceGrpcService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${grpc.server.port:9090}")
    private var grpcPort: Int = 9090

    private var server: Server? = null

    @PostConstruct
    fun startServer() {
        // TODO: Implement
        // server = ServerBuilder.forPort(grpcPort)
        //     .addService(resourceGrpcService)
        //     .addService(ProtoReflectionService.newInstance()) // For grpcurl/debugging
        //     .build()
        //     .start()
        // 
        // logger.info("gRPC server started on port $grpcPort")
        // 
        // // Shutdown hook
        // Runtime.getRuntime().addShutdownHook(Thread {
        //     logger.info("Shutting down gRPC server...")
        //     stopServer()
        // })
        
        logger.info("TODO: Start gRPC server on port $grpcPort")
    }

    @PreDestroy
    fun stopServer() {
        // TODO: Implement graceful shutdown
        // server?.let {
        //     it.shutdown()
        //     try {
        //         if (!it.awaitTermination(30, TimeUnit.SECONDS)) {
        //             it.shutdownNow()
        //         }
        //     } catch (e: InterruptedException) {
        //         it.shutdownNow()
        //     }
        // }
        
        logger.info("TODO: Stop gRPC server")
    }
}

