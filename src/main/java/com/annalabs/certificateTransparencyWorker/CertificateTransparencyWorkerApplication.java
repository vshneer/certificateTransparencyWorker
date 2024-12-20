package com.annalabs.certificateTransparencyWorker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.annalabs.certificateTransparencyWorker", "com.annalabs.crtshClient"})
public class CertificateTransparencyWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificateTransparencyWorkerApplication.class, args);
	}

}
