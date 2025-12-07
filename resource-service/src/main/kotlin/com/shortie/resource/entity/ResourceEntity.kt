package com.shortie.resource.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

/**
 * Resource JPA Entity
 * 
 * Maps to the 'resources' table in Postgres.
 * 
 * IMPLEMENTATION NOTES:
 * - Use UUID for id (generated in application, not DB)
 * - short_code must be unique and indexed
 * - state uses enum: ACTIVE, EXPIRED
 * - type uses enum: URL, FILE
 * 
 * TODO:
 * - Add optimistic locking with @Version for concurrent visit updates
 * - Add index on created_at for cleanup queries
 * - Consider partitioning by created_at for large datasets
 */
@Entity
@Table(
    name = "resources",
    indexes = [
        Index(name = "idx_resources_short_code", columnList = "short_code", unique = true),
        Index(name = "idx_resources_state", columnList = "state"),
        Index(name = "idx_resources_expires_at", columnList = "expires_at")
    ]
)
class ResourceEntity(
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "short_code", length = 16, unique = true, nullable = false)
    val shortCode: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    val type: ResourceType,

    @Column(name = "original_url", columnDefinition = "TEXT")
    val originalUrl: String? = null,

    @Column(name = "storage_path", columnDefinition = "TEXT")
    val storagePath: String? = null,

    @Column(name = "original_filename")
    val originalFilename: String? = null,

    @Column(name = "max_visits")
    val maxVisits: Int? = null,

    @Column(name = "visit_count", nullable = false)
    var visitCount: Int = 0,

    @Column(name = "expires_at")
    val expiresAt: Instant? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 10, nullable = false)
    var state: ResourceState = ResourceState.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
) {
    /**
     * TODO: Implement visit registration logic
     * 
     * Steps:
     * 1. Check if state is ACTIVE
     * 2. Check if not expired (expiresAt)
     * 3. Check if visit limit not reached (maxVisits)
     * 4. Increment visitCount
     * 5. If limit reached, set state = EXPIRED
     * 6. Return whether visit was valid
     */
    fun registerVisit(): Boolean {
        // TODO: Implement
        return false
    }

    /**
     * Check if resource is still valid for access
     */
    fun isValid(): Boolean {
        if (state != ResourceState.ACTIVE) return false
        if (expiresAt != null && Instant.now().isAfter(expiresAt)) return false
        if (maxVisits != null && visitCount >= maxVisits) return false
        return true
    }
}

enum class ResourceType {
    URL,
    FILE
}

enum class ResourceState {
    ACTIVE,
    EXPIRED
}

