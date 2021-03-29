package com.playmuscial.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = {DataSourceTransactionManagerAutoConfiguration.class,
    DataSourceAutoConfiguration.class})
public class ApiApplication {

    public static void main(String[] args) {
        String profile = System.getProperty("spring.profiles.active");
        if (profile == null) {
            System.setProperty("spring.profiles.active", "default");
        }
        SpringApplication.run(ApiApplication.class, args);
    }

}
