package com.shortie.resource.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.shortie.resource.entity.ResourceEntity
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

/**
 * Kafka Event Publisher
 * 
 * Publishes domain events to Kafka topics.
 * 
 * EVENTS:
 * - resource.created: When a new resource is created
 * - resource.downloaded: When a resource is accessed/downloaded
 * - resource.expired: When a resource expires (time or limit)
 * 
 * IMPLEMENTATION STEPS:
 * 1. Inject KafkaTemplate
 * 2. Create event data classes
 * 3. Serialize events to JSON
 * 4. Publish to appropriate topics
 * 
 * TODO:
 * - Add event schema versioning
 * - Add dead letter queue handling
 * - Add retry configuration
 * - Consider using Avro/Protobuf for schema
 */
@Service
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        const val TOPIC_RESOURCE_CREATED = "resource.created"
        const val TOPIC_RESOURCE_DOWNLOADED = "resource.downloaded"
        const val TOPIC_RESOURCE_EXPIRED = "resource.expired"
    }

    /**
     * Publish resource.created event
     * 
     * TODO: Implement
     * 1. Create ResourceCreatedEvent
     * 2. Serialize to JSON
     * 3. Send to Kafka with resource ID as key
     */
    fun publishResourceCreated(resource: ResourceEntity) {
        // TODO: Implement
        // val event = ResourceCreatedEvent(
        //     resourceId = resource.id.toString(),
        //     shortCode = resource.shortCode,
        //     type = resource.type.name,
        //     createdAt = resource.createdAt.toString()
        // )
        // 
        // val json = objectMapper.writeValueAsString(event)
        // kafkaTemplate.send(TOPIC_RESOURCE_CREATED, resource.id.toString(), json)
        //     .addCallback(
        //         { logger.debug("Published resource.created event for ${resource.shortCode}") },
        //         { logger.error("Failed to publish resource.created event", it) }
        //     )
        
        logger.info("TODO: Publish resource.created event for ${resource.shortCode}")
    }

    /**
     * Publish resource.downloaded event
     * 
     * TODO: Implement similar to publishResourceCreated
     */
    fun publishResourceDownloaded(resource: ResourceEntity) {
        // TODO: Implement
        logger.info("TODO: Publish resource.downloaded event for ${resource.shortCode}")
    }

    /**
     * Publish resource.expired event
     * 
     * TODO: Implement similar to publishResourceCreated
     */
    fun publishResourceExpired(resource: ResourceEntity) {
        // TODO: Implement
        logger.info("TODO: Publish resource.expired event for ${resource.shortCode}")
    }
}

/**
 * Event data classes
 * 
 * TODO: Define proper event schemas
 */
data class ResourceCreatedEvent(
    val resourceId: String,
    val shortCode: String,
    val type: String,
    val createdAt: String
)

data class ResourceDownloadedEvent(
    val resourceId: String,
    val shortCode: String,
    val visitCount: Int,
    val downloadedAt: String
)

data class ResourceExpiredEvent(
    val resourceId: String,
    val shortCode: String,
    val reason: String, // "TIME_EXPIRED" or "LIMIT_REACHED"
    val expiredAt: String
)

