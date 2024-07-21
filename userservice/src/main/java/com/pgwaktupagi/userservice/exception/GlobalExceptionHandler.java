package com.pgwaktupagi.userservice.exception;


import com.pgwaktupagi.userservice.dto.ErrorResponseDto;
import com.pgwaktupagi.userservice.dto.ResponseProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleInvalidArgument(MethodArgumentNotValidException exception)
    {
        Map<String,String> errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
        {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    // metode untuk handle validasi


    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleProductAlreadyExistsException(ProductAlreadyExistsException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseProduct> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setStatusCode("400");
        responseProduct.setMessage("Failed upload image");
        responseProduct.setData("Image must be less than 1mb");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseProduct);
    }
}
