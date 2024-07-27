package com.pgwaktupagi.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Response {

    private String httpCode;
    private String message;
    private Object data;
}
