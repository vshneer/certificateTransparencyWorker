package com.annalabs.certificateTransparencyWorker.worker;

import org.springframework.stereotype.Component;

@Component
public class CertificateTransparencyLogWorker {
    public void work(String message) {
        System.out.println("Received message: " + message);
    }
}
