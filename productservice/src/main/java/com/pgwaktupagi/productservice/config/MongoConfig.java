package com.pgwaktupagi.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

//    @Bean
//    public AuditorAware<AuditableUser> myAuditorProvider() {
//        return new AuditorAwareImpl();
//    }
}
