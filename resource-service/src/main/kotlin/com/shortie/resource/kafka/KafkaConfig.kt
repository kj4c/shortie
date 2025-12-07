package com.shortie.resource.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * Kafka Configuration
 * 
 * Configures Kafka producer and topics.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Configure producer factory with serializers
 * 2. Create KafkaTemplate for sending messages
 * 3. Auto-create topics on startup
 * 
 * TODO:
 * - Add SSL/SASL configuration for production
 * - Configure acknowledgment modes
 * - Add idempotent producer settings
 * - Consider separate configs for different environments
 */
@Configuration
class KafkaConfig {

    @Value("\${spring.kafka.bootstrap-servers:localhost:9092}")
    private lateinit var bootstrapServers: String

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val configs = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            // TODO: Add production settings
            // ProducerConfig.ACKS_CONFIG to "all",
            // ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            // ProducerConfig.RETRIES_CONFIG to 3
        )
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory)
    }

    // Auto-create topics
    // TODO: Adjust partitions and replication for production
    
    @Bean
    fun resourceCreatedTopic(): NewTopic {
        return NewTopic(KafkaEventPublisher.TOPIC_RESOURCE_CREATED, 3, 1.toShort())
    }

    @Bean
    fun resourceDownloadedTopic(): NewTopic {
        return NewTopic(KafkaEventPublisher.TOPIC_RESOURCE_DOWNLOADED, 3, 1.toShort())
    }

    @Bean
    fun resourceExpiredTopic(): NewTopic {
        return NewTopic(KafkaEventPublisher.TOPIC_RESOURCE_EXPIRED, 3, 1.toShort())
    }
}

