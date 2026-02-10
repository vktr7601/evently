package com.evently.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class EventsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }
}