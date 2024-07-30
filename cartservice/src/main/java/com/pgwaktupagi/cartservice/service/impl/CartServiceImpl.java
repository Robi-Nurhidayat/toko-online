package com.pgwaktupagi.cartservice.service.impl;

import com.pgwaktupagi.cartservice.dto.CartDTO;
import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public CartDTO findCartById(Long cartId) {
        Optional<Cart> byId = repository.findById(cartId);
        int quantity = 0;
        double price =  0.0;
        Optional<Cart> cart = Optional.empty();
        if (byId.isPresent()) {
            cart = byId;
        }else {
            throw new ResourceNotFoundException("Cart","cart_id", Long.toString(cartId));
        }

        List<CartItem> listCartItems = cartItemRepository.findByCartId(cart.get().getId());
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for (var items: listCartItems) {



            cartItemDTOList.add(new CartItemDTO(items.getId(),
                    items.getCart().getUserId(),
                    items.getCart().getId(),
                    items.getProductId(),
                    items.getQuantity(),
                    items.getPrice()

            ));

            double tempPrice =  items.getQuantity() * items.getPrice();
            quantity += items.getQuantity();
            price += tempPrice;



        }


        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.get().getId());
        cartDTO.setUserId(cart.get().getUserId());
        cartDTO.setCreatedAt(cart.get().getCreatedAt());
        cartDTO.setUpdatedAt(cart.get().getUpdatedAt());
        cartDTO.setCartItems(cartItemDTOList);
        cartDTO.setTotalQuantity(quantity);
        cartDTO.setTotalPrice(price);

        return cartDTO;
    }
}
