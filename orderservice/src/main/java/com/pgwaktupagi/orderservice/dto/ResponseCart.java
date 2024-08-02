package com.pgwaktupagi.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResponseCart {

    private String statusCode;
    private String message;
    private Object data;

}
