package com.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(value = "com.domain")
public class FlywayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlywayServerApplication.class, args);
    }

}
