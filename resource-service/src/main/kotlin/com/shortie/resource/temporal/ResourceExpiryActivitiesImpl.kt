package com.shortie.resource.temporal

import com.shortie.resource.service.ResourceService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

/**
 * Resource Expiry Activities Implementation
 * 
 * Implements the activities that handle resource expiration.
 * 
 * IMPLEMENTATION NOTES:
 * - This is a Spring bean, so it can inject other services
 * - Activities should be idempotent (safe to retry)
 * - Keep activities focused and short-running
 * - @Lazy breaks circular dependency: ResourceService → TemporalWorkflowStarter → TemporalConfig → this
 */
@Component
class ResourceExpiryActivitiesImpl(
    @Lazy private val resourceService: ResourceService
) : ResourceExpiryActivities {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun expireResource(resourceId: String) {
        // TODO: Implement
        // logger.info("Expiring resource: $resourceId")
        // resourceService.expireResource(resourceId)
        // logger.info("Resource expired: $resourceId")
        
        logger.info("TODO: Expire resource $resourceId")
    }
}

