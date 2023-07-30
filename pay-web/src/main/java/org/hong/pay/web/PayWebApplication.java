package org.hong.pay.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.hong"})
public class PayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayWebApplication.class, args);
    }
}
