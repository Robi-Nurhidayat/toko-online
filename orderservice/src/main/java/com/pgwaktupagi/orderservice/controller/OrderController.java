package com.pgwaktupagi.orderservice.controller;

import com.pgwaktupagi.orderservice.dto.OrderDTO;
import com.pgwaktupagi.orderservice.dto.OrderInfo;
import com.pgwaktupagi.orderservice.dto.Response;
import com.pgwaktupagi.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Response> createOrder(@RequestBody OrderDTO orderDTO) {
        System.out.println("isi order dto" + orderDTO);
        OrderDTO order = orderService.createOrder(orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201","success created order",order));

    }
    @GetMapping("/order-info")
    public ResponseEntity<OrderInfo> getInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(orderInfo);
    }
}
