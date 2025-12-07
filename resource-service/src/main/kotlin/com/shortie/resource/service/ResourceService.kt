package com.shortie.resource.service

import com.shortie.resource.entity.ResourceEntity
import com.shortie.resource.entity.ResourceState
import com.shortie.resource.entity.ResourceType
import com.shortie.resource.kafka.KafkaEventPublisher
import com.shortie.resource.repository.ResourceRepository
import com.shortie.resource.temporal.TemporalWorkflowStarter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

/**
 * Resource Service
 * 
 * Core business logic for managing resources.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Create resources with validation
 * 2. Generate short codes
 * 3. Start Temporal workflows for expiration
 * 4. Emit Kafka events
 * 5. Handle visit registration with atomic updates
 * 
 * TODO:
 * - Add input validation
 * - Add error handling
 * - Add logging
 * - Add metrics
 */
@Service
class ResourceService(
    private val resourceRepository: ResourceRepository,
    private val shortCodeGenerator: ShortCodeGenerator,
    private val kafkaEventPublisher: KafkaEventPublisher,
    private val temporalWorkflowStarter: TemporalWorkflowStarter
) {

    /**
     * Create a new URL resource
     * 
     * TODO: Implement
     * 1. Validate URL format
     * 2. Generate short code
     * 3. Create and save entity
     * 4. Start expiry workflow (if expiresAt is set)
     * 5. Publish resource.created event
     * 6. Return created entity
     */
    @Transactional
    fun createUrl(
        originalUrl: String,
        maxVisits: Int?,
        expiresAt: Instant?
    ): ResourceEntity {
        // TODO: Implement
        // val shortCode = shortCodeGenerator.generate()
        // 
        // val resource = ResourceEntity(
        //     shortCode = shortCode,
        //     type = ResourceType.URL,
        //     originalUrl = originalUrl,
        //     maxVisits = maxVisits,
        //     expiresAt = expiresAt
        // )
        // 
        // val saved = resourceRepository.save(resource)
        // 
        // if (expiresAt != null) {
        //     temporalWorkflowStarter.startExpiryWorkflow(saved.id.toString(), expiresAt.toEpochMilli())
        // }
        // 
        // kafkaEventPublisher.publishResourceCreated(saved)
        // 
        // return saved
        
        throw NotImplementedError("createUrl not implemented")
    }

    /**
     * Create a new file resource
     * 
     * TODO: Implement similar to createUrl
     */
    @Transactional
    fun createFile(
        storagePath: String,
        originalFilename: String?,
        maxVisits: Int?,
        expiresAt: Instant?
    ): ResourceEntity {
        // TODO: Implement
        throw NotImplementedError("createFile not implemented")
    }

    /**
     * Resolve a resource by short code (without incrementing visits)
     * 
     * TODO: Implement
     * 1. Look up by short code
     * 2. Return entity or null
     */
    fun resolve(shortCode: String): ResourceEntity? {
        // TODO: Implement
        // return resourceRepository.findByShortCode(shortCode)
        return null
    }

    /**
     * Register a visit and validate the resource
     * 
     * TODO: Implement
     * 1. Look up resource
     * 2. Check if valid (active, not expired, under limit)
     * 3. Atomically increment visit count
     * 4. If limit reached, set state = EXPIRED
     * 5. Publish resource.downloaded event
     * 6. Return result with redirect URL or file path
     */
    @Transactional
    fun registerVisit(shortCode: String): VisitResult {
        // TODO: Implement
        // val resource = resourceRepository.findByShortCode(shortCode)
        //     ?: return VisitResult.NotFound
        // 
        // if (!resource.isValid()) {
        //     return VisitResult.Invalid("Resource has expired or reached visit limit")
        // }
        // 
        // resource.visitCount++
        // 
        // if (resource.maxVisits != null && resource.visitCount >= resource.maxVisits!!) {
        //     resource.state = ResourceState.EXPIRED
        //     kafkaEventPublisher.publishResourceExpired(resource)
        // }
        // 
        // resourceRepository.save(resource)
        // kafkaEventPublisher.publishResourceDownloaded(resource)
        // 
        // return VisitResult.Valid(resource)
        
        return VisitResult.NotFound
    }

    /**
     * Expire a resource by ID (called by Temporal workflow)
     * 
     * TODO: Implement
     * 1. Update state to EXPIRED
     * 2. Publish resource.expired event
     */
    @Transactional
    fun expireResource(resourceId: String) {
        // TODO: Implement
        // val id = UUID.fromString(resourceId)
        // val resource = resourceRepository.findById(id).orElse(null) ?: return
        // 
        // if (resource.state == ResourceState.ACTIVE) {
        //     resource.state = ResourceState.EXPIRED
        //     resourceRepository.save(resource)
        //     kafkaEventPublisher.publishResourceExpired(resource)
        // }
    }
}

/**
 * Result of a visit registration
 */
sealed class VisitResult {
    object NotFound : VisitResult()
    data class Invalid(val message: String) : VisitResult()
    data class Valid(val resource: ResourceEntity) : VisitResult()
}

