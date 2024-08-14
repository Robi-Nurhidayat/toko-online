package com.pgwaktupagi.userservice.functions;

import com.pgwaktupagi.userservice.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UsersFunctions {

    private static final Logger log = LoggerFactory.getLogger(UsersFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IUserService userService) {
        // random number, karena ga tau mau kasih nama apa, ya karena di message service cuma ngembaliin nilai random aja
        return userId -> {
            log.info("Updating communication status for the account number : " + userId.toString());
            userService.updateCommunication(userId);
        };
    }
}
