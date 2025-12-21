package org.example.stablematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <--- Import nou

@SpringBootApplication
@EnableDiscoveryClient
public class StableMatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(StableMatchApplication.class, args);
    }
}