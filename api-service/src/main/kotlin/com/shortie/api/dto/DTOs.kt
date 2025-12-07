package com.shortie.api.dto

import java.time.Instant

/**
 * Data Transfer Objects for API Service
 * 
 * These DTOs are used for:
 * - REST API request/response bodies
 * - Converting to/from gRPC Protobuf messages
 */

// ============================================================================
// REQUEST DTOs
// ============================================================================

/**
 * Request to create a shortened URL
 * 
 * TODO: Add validation annotations
 * - url: @NotBlank, @URL
 * - maxVisits: @Min(1)
 * - expiresInHours: @Min(1)
 */
data class CreateUrlRequest(
    val url: String,
    val maxVisits: Int? = null,
    val expiresInHours: Int? = null
)

/**
 * Request to create a file resource
 * (Used internally after file is saved)
 */
data class CreateFileRequest(
    val storagePath: String,
    val originalFilename: String?,
    val maxVisits: Int? = null,
    val expiresInHours: Int? = null
)

// ============================================================================
// RESPONSE DTOs
// ============================================================================

/**
 * Response containing resource info and short URL
 */
data class ResourceResponse(
    val id: String,
    val shortCode: String,
    val shortUrl: String,
    val type: String,
    val originalUrl: String?,
    val maxVisits: Int?,
    val visitCount: Int,
    val expiresAt: Instant?,
    val state: String,
    val createdAt: Instant
) {
    companion object {
        fun fromProto(resource: Resource): ResourceResponse {
            return ResourceResponse(
                id = resource.id,
                shortCode = resource.shortCode,
                shortUrl = "/s/${resource.shortCode}",
                type = resource.type.name,
                originalUrl = resource.originalUrl.takeIf { it.isNotEmpty() },
                maxVisits = resource.maxVisits.takeIf { it > 0 },
                visitCount = resource.visitCount,
                expiresAt = resource.expiresAtMillis.takeIf { it > 0 }
                    ?.let { Instant.ofEpochMilli(it) },
                state = resource.state.name,
                createdAt = Instant.ofEpochMilli(resource.createdAtMillis)
            )
        }
    }
}

/**
 * Result of visiting/accessing a resource
 */
data class VisitResult(
    val valid: Boolean,
    val type: String?,
    val redirectUrl: String?,
    val filePath: String?,
    val filename: String?,
    val errorMessage: String?
) {
    companion object {
        fun invalid(message: String) = VisitResult(
            valid = false,
            type = null,
            redirectUrl = null,
            filePath = null,
            filename = null,
            errorMessage = message
        )

        /**
         * TODO: Implement conversion from Protobuf message
         */
        // fun fromProto(response: RegisterVisitResponse): VisitResult {
        //     return VisitResult(
        //         valid = response.valid,
        //         type = response.resource.type.name,
        //         redirectUrl = response.redirectUrl.takeIf { it.isNotEmpty() },
        //         filePath = response.filePath.takeIf { it.isNotEmpty() },
        //         filename = extractFilename(response.filePath),
        //         errorMessage = response.errorMessage.takeIf { it.isNotEmpty() }
        //     )
        // }
    }
}

