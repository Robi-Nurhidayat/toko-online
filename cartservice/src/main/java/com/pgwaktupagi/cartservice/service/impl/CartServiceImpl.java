package com.pgwaktupagi.cartservice.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pgwaktupagi.cartservice.dto.CartDTO;
import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.mapper.ResourceNotFoundException;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {


    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartDTO> getAllCart() {
        List<Cart> carts = repository.findAll();

        return carts.stream()
                .map(cart -> {
                    // Convert Cart to CartDTO
                    List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                            .map(item -> new CartItemDTO(
                                    item.getId(),
                                    null,
                                    item.getCart().getId(),
                                    item.getProductId(),
                                    item.getQuantity(),
                                    item.getPrice()
                            ))
                            .collect(Collectors.toList());

                    return new CartDTO(
                            cart.getId(),
                            cart.getUserId(),
                            cart.getCreatedAt(),
                            cart.getUpdatedAt(),
                            cartItemDTOs,
                            cartItemDTOs.stream().mapToInt(CartItemDTO::getQuantity).sum(),
                            cartItemDTOs.stream().mapToDouble(dto -> dto.getPrice() * dto.getQuantity()).sum()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteCart(Long id) {

        repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cart","id",Long.toString(id))
        );

        repository.deleteById(id);
        return true;
    }

    @Override
    public CartDTO findCartById(Long id) {
        Optional<Cart> byId = repository.findById(id);
        Cart cart = null;
        if (byId.isPresent()) {
            cart = byId;
        }

        List<CartItem> listCartItems = cartItemRepository.findByCartId(cart.getId());
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for (var items: listCartItems) {

            // cart item dto
            private Long id;
            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
            private Long userId;
            private Long cartId;
            private Long productId;
            private int quantity;
            private Double price;
            cartItemDTOList.add(new CartItemDTO(items.getId(),
                    items.getCart().getUserId(),

            ))

            // cart item
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @ManyToOne
            @JoinColumn(name = "cart_id", nullable = false)
            private Cart cart;
            private Long productId;

            private int quantity;

            private Double price;
        }


        private Long id;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<CartItemDTO> cartItems;
        private int totalQuantity;
        private double totalPrice;


        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUserId());
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());

        return null;
    }
}
