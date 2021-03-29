package com.playmuscial.api.config;

import com.toast.java.logncrash.logback.LogbackShutdownListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${web.server.host}")
    String url;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(url).allowedOrigins("*").allowedMethods("*").maxAge(1800);
    }

    @Bean
    public LogbackShutdownListener logbackShutdownListener() {
        return new LogbackShutdownListener();
    }
}
