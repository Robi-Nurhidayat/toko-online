package com.pgwaktupagi.orderservice.controller;

import com.pgwaktupagi.orderservice.dto.OrderDTO;
import com.pgwaktupagi.orderservice.dto.OrderInfo;
import com.pgwaktupagi.orderservice.dto.Response;
import com.pgwaktupagi.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final IOrderService orderService;
    private final OrderInfo orderInfo;

    @GetMapping
    public ResponseEntity<Response> getAllOrder() {
        List<OrderDTO> allOrder = orderService.getAllOrder();

        return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Success get all orders",allOrder));

    }

    @GetMapping("/order-info")
    public ResponseEntity<OrderInfo> getInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(orderInfo);
    }
}
