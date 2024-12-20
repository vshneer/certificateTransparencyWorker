package com.annalabs.certificateTransparencyWorker.worker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CertificateTransparencyLogWorkerTest {

    public static final String TEST_D = "fnx.co.il";
    private static final String TOPIC = "subdomains";

    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    static {
        kafkaContainer.start();
    }

    private final CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    CertificateTransparencyLogWorker certificateTransparencyLogWorker;
    private String receivedMessage;

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    void workerPublishesToKafka() throws InterruptedException {
        certificateTransparencyLogWorker.work(TEST_D);
        // Wait for the message to be consumed
        boolean messageConsumed = latch.await(2, TimeUnit.SECONDS);
        // Verify the received message
        assertTrue(messageConsumed, "Message was not consumed in time");
    }

    @KafkaListener(topics = TOPIC, groupId = "test-group")
    public void listen(String message) {
        this.receivedMessage = message;
        latch.countDown();
    }
}