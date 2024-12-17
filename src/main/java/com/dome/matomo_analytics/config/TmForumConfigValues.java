package com.dome.matomo_analytics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "tmforum")
@Configuration
@Data
public class TmForumConfigValues {
    private String host;
    private String api_key;
}
