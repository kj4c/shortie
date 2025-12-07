package com.shortie.resource.grpc

import com.shortie.proto.*
import com.shortie.resource.entity.ResourceEntity
import com.shortie.resource.entity.ResourceType
import com.shortie.resource.service.ResourceService
import com.shortie.resource.service.VisitResult
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * gRPC Service Implementation
 * 
 * Implements the ResourceService gRPC interface defined in proto/resource.proto.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Extend the generated ResourceServiceGrpc.ResourceServiceImplBase
 * 2. Implement each RPC method
 * 3. Convert between Protobuf messages and domain objects
 * 4. Handle errors and return appropriate status codes
 * 
 * TODO:
 * - Add input validation
 * - Add proper gRPC error handling with Status codes
 * - Add interceptors for logging/tracing
 * - Add authentication interceptor
 */
@Service
class ResourceGrpcService(
    private val resourceService: ResourceService
) : ResourceServiceGrpc.ResourceServiceImplBase() {

    /**
     * Create a new resource (URL or FILE)
     * 
     * TODO: Implement
     * 1. Extract type and parameters from request
     * 2. Call resourceService.createUrl() or createFile()
     * 3. Convert entity to Protobuf response
     * 4. Send response via StreamObserver
     */
    override fun createResource(
        request: CreateResourceRequest,
        responseObserver: StreamObserver<CreateResourceResponse>
    ) {
        // TODO: Implement
        // try {
        //     val expiresAt = request.expiresAtMillis.takeIf { it > 0 }
        //         ?.let { Instant.ofEpochMilli(it) }
        //     val maxVisits = request.maxVisits.takeIf { it > 0 }
        //     
        //     val resource = when (request.type) {
        //         com.shortie.proto.ResourceType.RESOURCE_TYPE_URL ->
        //             resourceService.createUrl(request.originalUrl, maxVisits, expiresAt)
        //         com.shortie.proto.ResourceType.RESOURCE_TYPE_FILE ->
        //             resourceService.createFile(request.storagePath, request.originalFilename, maxVisits, expiresAt)
        //         else -> throw IllegalArgumentException("Invalid resource type")
        //     }
        //     
        //     val response = CreateResourceResponse.newBuilder()
        //         .setResource(resource.toProto())
        //         .build()
        //     
        //     responseObserver.onNext(response)
        //     responseObserver.onCompleted()
        // } catch (e: Exception) {
        //     responseObserver.onError(Status.INTERNAL.withDescription(e.message).asException())
        // }
        
        responseObserver.onError(
            io.grpc.Status.UNIMPLEMENTED.withDescription("Not implemented").asException()
        )
    }

    /**
     * Resolve a short code to its resource
     * 
     * TODO: Implement
     * 1. Call resourceService.resolve()
     * 2. Build response with found/error status
     */
    override fun resolveResource(
        request: ResolveResourceRequest,
        responseObserver: StreamObserver<ResolveResourceResponse>
    ) {
        // TODO: Implement
        // val resource = resourceService.resolve(request.shortCode)
        // 
        // val response = if (resource != null) {
        //     ResolveResourceResponse.newBuilder()
        //         .setResource(resource.toProto())
        //         .setFound(true)
        //         .build()
        // } else {
        //     ResolveResourceResponse.newBuilder()
        //         .setFound(false)
        //         .setErrorMessage("Resource not found")
        //         .build()
        // }
        // 
        // responseObserver.onNext(response)
        // responseObserver.onCompleted()
        
        responseObserver.onError(
            io.grpc.Status.UNIMPLEMENTED.withDescription("Not implemented").asException()
        )
    }

    /**
     * Register a visit and get redirect/download info
     * 
     * TODO: Implement
     * 1. Call resourceService.registerVisit()
     * 2. Build response based on result type
     * 3. Include redirect URL or file path
     */
    override fun registerVisit(
        request: RegisterVisitRequest,
        responseObserver: StreamObserver<RegisterVisitResponse>
    ) {
        // TODO: Implement
        // val result = resourceService.registerVisit(request.shortCode)
        // 
        // val response = when (result) {
        //     is VisitResult.NotFound -> RegisterVisitResponse.newBuilder()
        //         .setValid(false)
        //         .setErrorMessage("Resource not found")
        //         .build()
        //     is VisitResult.Invalid -> RegisterVisitResponse.newBuilder()
        //         .setValid(false)
        //         .setErrorMessage(result.message)
        //         .build()
        //     is VisitResult.Valid -> RegisterVisitResponse.newBuilder()
        //         .setResource(result.resource.toProto())
        //         .setValid(true)
        //         .apply {
        //             if (result.resource.type == ResourceType.URL) {
        //                 setRedirectUrl(result.resource.originalUrl)
        //             } else {
        //                 setFilePath(result.resource.storagePath)
        //             }
        //         }
        //         .build()
        // }
        // 
        // responseObserver.onNext(response)
        // responseObserver.onCompleted()
        
        responseObserver.onError(
            io.grpc.Status.UNIMPLEMENTED.withDescription("Not implemented").asException()
        )
    }

    /**
     * Convert domain entity to Protobuf message
     * 
     * TODO: Implement as extension function
     */
    // private fun ResourceEntity.toProto(): Resource {
    //     return Resource.newBuilder()
    //         .setId(id.toString())
    //         .setShortCode(shortCode)
    //         .setType(
    //             when (type) {
    //                 ResourceType.URL -> com.shortie.proto.ResourceType.RESOURCE_TYPE_URL
    //                 ResourceType.FILE -> com.shortie.proto.ResourceType.RESOURCE_TYPE_FILE
    //             }
    //         )
    //         .apply {
    //             originalUrl?.let { setOriginalUrl(it) }
    //             storagePath?.let { setStoragePath(it) }
    //             maxVisits?.let { setMaxVisits(it) }
    //             expiresAt?.let { setExpiresAtMillis(it.toEpochMilli()) }
    //         }
    //         .setVisitCount(visitCount)
    //         .setState(
    //             when (state) {
    //                 ResourceState.ACTIVE -> com.shortie.proto.ResourceState.RESOURCE_STATE_ACTIVE
    //                 ResourceState.EXPIRED -> com.shortie.proto.ResourceState.RESOURCE_STATE_EXPIRED
    //             }
    //         )
    //         .setCreatedAtMillis(createdAt.toEpochMilli())
    //         .build()
    // }
}

