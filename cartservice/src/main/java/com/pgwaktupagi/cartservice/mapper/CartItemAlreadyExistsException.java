package com.pgwaktupagi.cartservice.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CartItemAlreadyExistsException extends RuntimeException{

    public CartItemAlreadyExistsException(String message) {super(message);}
}
