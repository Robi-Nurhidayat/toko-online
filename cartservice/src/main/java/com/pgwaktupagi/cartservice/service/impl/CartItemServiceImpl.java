package com.pgwaktupagi.cartservice.service.impl;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.mapper.ResourceNotFoundException;
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

    public CartItemDTO addToCartItem(CartItemDTO cartItemDTO) {
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
        cartItem.setPrice(cartItemDTO.getPrice() * cartItemDTO.getQuantity());

        cartItem = cartItemRepository.save(cartItem);

        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }

    @Override
    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        // Assume customerId is derived from the security context or request

        Long customerId = getCurrentCustomerId();

        Optional<Cart> optionalCart = cartRepository.findByCustomerId(customerId);
        Cart cart = null;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
       }

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setCart(cart);
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice() * cartItemDTO.getQuantity());

        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        return new CartItemDTO(
                updatedCartItem.getId(),
                updatedCartItem.getCart().getId(),
                updatedCartItem.getProductId(),
                updatedCartItem.getQuantity(),
                updatedCartItem.getPrice()
        );



    }

    private Long getCurrentCustomerId() {
        // Implement logic to get the current customer ID, e.g., from security context
        return 1L; // Example placeholder
    }

    @Override
    public boolean deleteCart(Long id) {

        cartItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cart item","id",Long.toString(id))
        );
        cartItemRepository.deleteById(id);

        return true;
    }
}
