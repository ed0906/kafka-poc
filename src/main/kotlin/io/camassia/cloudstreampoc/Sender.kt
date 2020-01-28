package io.camassia.cloudstreampoc

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Sender(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun send(payload: String) {
        println("Sending [$payload]")
        kafkaTemplate.send("test.t", payload)
    }

}