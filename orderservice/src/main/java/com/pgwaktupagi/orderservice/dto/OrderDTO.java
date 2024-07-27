package com.pgwaktupagi.orderservice.dto;

import com.pgwaktupagi.orderservice.entity.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isPickup;
    private String shippingAddress;
    private String paymentMethod;
    private String billingAddress;
    private String message;
    private Double totalPrice;
    private String orderStatus;
    private Instant createdTimestamp;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
