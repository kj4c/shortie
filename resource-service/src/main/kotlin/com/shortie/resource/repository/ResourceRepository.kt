package com.shortie.resource.repository

import com.shortie.resource.entity.ResourceEntity
import com.shortie.resource.entity.ResourceState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

/**
 * Resource Repository
 * 
 * JPA repository for resource CRUD operations.
 * 
 * IMPLEMENTATION NOTES:
 * - findByShortCode is the primary lookup method
 * - Use @Modifying queries for atomic updates
 * - Consider using pessimistic locking for high-concurrency scenarios
 * 
 * TODO:
 * - Add method to find expired resources for cleanup
 * - Add method to find resources by state
 * - Add batch update methods for efficiency
 */
@Repository
interface ResourceRepository : JpaRepository<ResourceEntity, UUID> {

    /**
     * Find resource by short code
     * 
     * This is the primary lookup method used when resolving short URLs.
     */
    fun findByShortCode(shortCode: String): ResourceEntity?

    /**
     * Check if short code exists
     * 
     * Used to ensure uniqueness when generating new short codes.
     */
    fun existsByShortCode(shortCode: String): Boolean

    /**
     * Find all active resources that have expired
     * 
     * TODO: Implement for batch expiration processing
     * Used by scheduled job to expire resources that passed their expiresAt
     */
    fun findByStateAndExpiresAtBefore(
        state: ResourceState,
        expiresAt: Instant
    ): List<ResourceEntity>

    /**
     * Atomic visit count increment
     * 
     * TODO: Implement atomic update to prevent race conditions
     * Returns the number of rows updated (1 if successful)
     */
    @Modifying
    @Query("""
        UPDATE ResourceEntity r 
        SET r.visitCount = r.visitCount + 1 
        WHERE r.shortCode = :shortCode 
        AND r.state = 'ACTIVE'
    """)
    fun incrementVisitCount(shortCode: String): Int

    /**
     * Expire a resource by ID
     * 
     * TODO: Used by Temporal workflow to expire resources
     */
    @Modifying
    @Query("""
        UPDATE ResourceEntity r 
        SET r.state = 'EXPIRED' 
        WHERE r.id = :id
    """)
    fun expireResource(id: UUID): Int
}

