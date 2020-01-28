package io.camassia.cloudstreampoc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [KafkaApplication::class, Sender::class, Receiver::class, ReceiverConfig::class, SenderConfig::class])
@EmbeddedKafka(partitions = 1, topics = ["test.t"])
@TestPropertySource(properties = ["kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"])
class KafkaApplicationTests {

	@Autowired
	private lateinit var receiver: Receiver

	@Autowired
	private lateinit var  sender: Sender

	@Test
	fun testSendAndReceive() {
		sender.send("Hello World")

		val messages = receiver.awaitMessages()
		assertThat(messages).containsExactly("Hello World")
	}

}
