package com.annalabs.certificateTransparencyWorker.worker;

import com.annalabs.crtshClient.client.CrtShClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Component
public class CertificateTransparencyLogWorker {

    @Autowired
    CrtShClient crtShClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topics.subdomain}")
    private String topic;

    public void work(String message) {
        Set<String> subdomains = null;
        try {
            subdomains = crtShClient.getSubdomains(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Objects.isNull(subdomains)) {
            subdomains.forEach(subdomain -> kafkaTemplate.send(topic, subdomain));
        }
    }
}
