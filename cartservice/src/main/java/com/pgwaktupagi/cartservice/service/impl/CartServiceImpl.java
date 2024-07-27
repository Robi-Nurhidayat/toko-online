package com.pgwaktupagi.cartservice.service.impl;

import com.pgwaktupagi.cartservice.dto.CartDTO;
import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.mapper.ResourceNotFoundException;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                                    item.getCart().getId(),
                                    item.getProductId(),
                                    item.getQuantity(),
                                    item.getPrice()
                            ))
                            .collect(Collectors.toList());

                    return new CartDTO(
                            cart.getId(),
                            cart.getCustomerId(),
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
}
