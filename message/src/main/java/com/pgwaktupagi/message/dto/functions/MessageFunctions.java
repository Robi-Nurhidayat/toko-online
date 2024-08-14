package com.pgwaktupagi.message.dto.functions;


import com.pgwaktupagi.message.dto.UsersMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<UsersMsgDto,UsersMsgDto> email() {
        return usersMsgDto -> {
            log.info("sending email with details: " + usersMsgDto.toString());

            return usersMsgDto;
        };
    }

    @Bean
    public Function<UsersMsgDto,Long> sms() {
        return usersMsgDto -> {
            log.info("sending sms with details: " + usersMsgDto.toString());

            return 23423423L;
        };
    }
}
