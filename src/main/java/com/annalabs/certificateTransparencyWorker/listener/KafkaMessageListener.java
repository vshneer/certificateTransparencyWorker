package com.annalabs.certificateTransparencyWorker.listener;

import com.annalabs.certificateTransparencyWorker.worker.CertificateTransparencyLogWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {
    @Autowired
    CertificateTransparencyLogWorker worker;

    @KafkaListener(topics = {"${kafka.topics.domain}"}, groupId = "${kafka.groups.certsh}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        worker.work(message);
    }
}
