package com.shortie.api.service

import com.shortie.api.dto.CreateFileRequest
import com.shortie.api.dto.CreateUrlRequest
import com.shortie.api.dto.ResourceResponse
import com.shortie.api.dto.VisitResult
import com.shortie.proto.ResourceServiceGrpc
import org.springframework.stereotype.Service

/**
 * Resource API Service
 * 
 * Service layer that communicates with resource-service via gRPC.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Inject gRPC blocking stub
 * 2. Convert DTOs to Protobuf messages
 * 3. Call gRPC methods
 * 4. Convert responses back to DTOs
 * 5. Handle gRPC errors appropriately
 * 
 * TODO:
 * - Add error handling for gRPC failures
 * - Add retry logic for transient failures
 * - Add circuit breaker pattern
 * - Add logging and metrics
 */
@Service
class ResourceApiService(
    private val resourceServiceStub: ResourceServiceGrpc.ResourceServiceBlockingStub
) {

    /**
     * Create a shortened URL
     * 
     * TODO:
     * 1. Build CreateResourceRequest protobuf message
     * 2. Call resourceServiceStub.createResource()
     * 3. Convert response to ResourceResponse DTO
     */
    fun createUrl(request: CreateUrlRequest): ResourceResponse {
        val grpcRequest = CreateResourceRequest.newBuilder()
            .setType(ResourceType.RESOURCE_TYPE_URL)
            .setOriginalUrl(request.url)
            .apply {
                request.maxVisits?.let { setMaxVisits(it) }
                request.expiresInHours?.let { 
                    setExpiresAtMillis(System.currentTimeMillis() + it * 3600 * 1000)
                }
            }
            .build()
        val response = resourceServiceStub.createResource(grpcRequest)
        return ResourceResponse.fromProto(response.resource)
    }

    /**
     * Create a file resource
     * 
     * TODO:
     * 1. Build CreateResourceRequest with FILE type
     * 2. Include storage path and original filename
     * 3. Call gRPC and return response
     */
    fun createFile(request: CreateFileRequest): ResourceResponse {
        // TODO: Implement similar to createUrl
        return ResourceResponse.placeholder()
    }

    /**
     * Register a visit and get redirect/download info
     * 
     * TODO:
     * 1. Build RegisterVisitRequest with short code
     * 2. Call resourceServiceStub.registerVisit()
     * 3. Convert to VisitResult DTO
     */
    fun registerVisit(shortCode: String): VisitResult {
        // TODO: Implement
        // val request = RegisterVisitRequest.newBuilder()
        //     .setShortCode(shortCode)
        //     .build()
        // 
        // val response = resourceServiceStub.registerVisit(request)
        // return VisitResult.fromProto(response)
        
        return VisitResult.invalid("Not implemented")
    }
}

