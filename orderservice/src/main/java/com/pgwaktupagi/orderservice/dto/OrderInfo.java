package com.pgwaktupagi.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "orders")
@Getter @Setter
public class OrderInfo {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
