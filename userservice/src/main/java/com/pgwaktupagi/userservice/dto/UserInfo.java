package com.pgwaktupagi.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "users")
@Getter @Setter
public class UserInfo {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
