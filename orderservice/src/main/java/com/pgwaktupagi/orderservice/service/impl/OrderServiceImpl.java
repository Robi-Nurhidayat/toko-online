package com.pgwaktupagi.orderservice.service.impl;

import com.pgwaktupagi.orderservice.dto.OrderDTO;
import com.pgwaktupagi.orderservice.dto.UserDTO;
import com.pgwaktupagi.orderservice.entity.Order;
import com.pgwaktupagi.orderservice.entity.OrderItem;
import com.pgwaktupagi.orderservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.orderservice.repository.OrderItemRepository;
import com.pgwaktupagi.orderservice.repository.OrderRepository;
import com.pgwaktupagi.orderservice.service.IOrderService;
import com.pgwaktupagi.orderservice.service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserClient userClient;

    @Override
    public List<OrderDTO> getAllOrder() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOS = new ArrayList<>();

        OrderDTO orderDTO = null;

        for (var order: orders) {

            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setUserId(order.getUserId());
            orderDTO.setFirstName(order.getFirstName());
            orderDTO.setLastName(order.getLastName());
            orderDTO.setEmail(order.getEmail());
            orderDTO.setPhoneNumber(order.getPhoneNumber());
            orderDTO.setPickup(order.isPickup());
            orderDTO.setShippingAddress(order.getShippingAddress());
            orderDTO.setPaymentMethod(order.getPaymentMethod());
            orderDTO.setBillingAddress(order.getBillingAddress());
            orderDTO.setMessage(order.getMessage());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setCreatedTimestamp(order.getCreatedTimestamp());


        }
       return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        // find user for set user id
        ResponseEntity<UserDTO> user = userClient.findByid(orderDTO.getUserId());
        if (user.getBody() == null) {
            throw new ResourceNotFoundException("User","id",Long.toString(user.getBody().getId()));
        }

        // find cart for set cartId

        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setUserId(orderDTO.getUserId());
        order.setFirstName(orderDTO.getFirstName());
        order.setLastName(orderDTO.getLastName());
        order.setEmail(orderDTO.getEmail());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setPickup(orderDTO.isPickup());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setBillingAddress(orderDTO.getBillingAddress());
        order.setMessage(orderDTO.getMessage());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setCreatedTimestamp(orderDTO.getCreatedTimestamp());

        Order createOrder = orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(createOrder);
        

        return null;
    }
}
