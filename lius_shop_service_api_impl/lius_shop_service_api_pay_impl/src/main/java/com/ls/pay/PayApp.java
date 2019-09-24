package com.ls.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class) ;
    }
}

