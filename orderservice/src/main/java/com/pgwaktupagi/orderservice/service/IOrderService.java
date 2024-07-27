package com.pgwaktupagi.orderservice.service;

import com.pgwaktupagi.orderservice.dto.OrderDTO;

import java.util.List;

public interface IOrderService {

    List<OrderDTO> getAllOrder();
    OrderDTO createOrder(OrderDTO orderDTO);

}
