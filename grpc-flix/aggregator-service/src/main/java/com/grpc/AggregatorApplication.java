package com.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AggregatorApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(AggregatorApplication.class,args);
    }
}