package com.ziorye.proofread.config;

import com.ziorye.proofread.thymeleaf.CustomLmsDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {
    @Bean
    public CustomLmsDialect customDialect() {
        return new CustomLmsDialect();
    }
}
