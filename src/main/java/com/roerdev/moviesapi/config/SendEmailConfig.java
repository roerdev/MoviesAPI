package com.roerdev.moviesapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "send-email", ignoreInvalidFields = true)
public class SendEmailConfig {

    private String subject;
    private String from;
    private String apikey;
}
