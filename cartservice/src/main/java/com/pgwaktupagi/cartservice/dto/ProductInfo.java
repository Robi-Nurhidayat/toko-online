package com.pgwaktupagi.cartservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "products")
@Getter @Setter
public class ProductInfo {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
