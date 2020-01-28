package io.camassia.cloudstreampoc

import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer


@Configuration
class ReceiverConfig(@Value("\${kafka.bootstrap-servers}") private val bootstrapServers: String) {

    private fun consumerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        // list of host:port pairs used for establishing the initial connections to the Kafka cluster
        props[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        // allows a pool of processes to divide the work of consuming and processing records
        props[GROUP_ID_CONFIG] = "helloworld"
        // automatically reset the offset to the earliest offset
        props[AUTO_OFFSET_RESET_CONFIG] = "earliest"
        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> = DefaultKafkaConsumerFactory(consumerConfigs())

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

}