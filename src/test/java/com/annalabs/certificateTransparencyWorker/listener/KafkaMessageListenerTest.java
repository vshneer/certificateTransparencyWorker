package com.annalabs.certificateTransparencyWorker.listener;

import com.annalabs.certificateTransparencyWorker.worker.CertificateTransparencyLogWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"domains"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaMessageListenerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaMessageListener listener;

    @Test
    public void listen() throws InterruptedException {

        CertificateTransparencyLogWorker worker = mock(CertificateTransparencyLogWorker.class);
        listener.worker = worker;

        // Send a test message to Kafka
        String testMessage = "Hello, Kafka!";
        kafkaTemplate.send("domains", testMessage);

        // Wait for the listener to process the message
        Thread.sleep(5000); // Replace with a better synchronization method in production

        // Verify that the MessageProcessor's processMessage method was called with the correct parameter
        verify(worker).work(testMessage);
    }
}