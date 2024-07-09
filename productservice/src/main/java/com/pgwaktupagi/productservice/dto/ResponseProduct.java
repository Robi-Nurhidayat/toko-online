package com.pgwaktupagi.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {

    @JsonProperty(namespace = "status_code")
    private String statusCode;
    private String message;
    private Object data;
}
