package io.camassia.cloudstreampoc

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver {

    private val latch = CountDownLatch(1)
    private val messages = mutableListOf<String>()

    fun awaitMessages(): List<String> {
        latch.await()
        return messages
    }

    @KafkaListener(topics = ["test.t"])
    fun receive(payload: String) {
        println("Received [$payload]")
        messages.add(payload)
        latch.countDown()
    }

}