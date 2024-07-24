package com.pgwaktupagi.cartservice.service.impl;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartItemService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements ICartItemService {


    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public CartItemDTO addToCart(CartItemDTO cartItemDTO) {
        // Assume customerId is derived from the security context or request
        Long customerId = getCurrentCustomerId();

        Optional<Cart> optionalCart = cartRepository.findByCustomerId(customerId);
        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setCustomerId(customerId);
            cart = cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());

        cartItem = cartItemRepository.save(cartItem);

        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }

    private Long getCurrentCustomerId() {
        // Implement logic to get the current customer ID, e.g., from security context
        return 1L; // Example placeholder
    }
}
