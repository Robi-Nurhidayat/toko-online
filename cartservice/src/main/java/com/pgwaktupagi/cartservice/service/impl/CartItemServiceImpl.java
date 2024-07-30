package com.pgwaktupagi.cartservice.service.impl;

import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.dto.UserDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartItemService;
import com.pgwaktupagi.cartservice.service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements ICartItemService {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserClient userClient;

    public CartItemDTO addToCartItem(CartItemDTO cartItemDTO) {

        ResponseEntity<UserDTO> user = userClient.findUser(cartItemDTO.getUserId());

        System.out.println("isi user apaan ya: " + user);
        if (user.getBody() == null) {
            throw new ResourceNotFoundException("User","id", Long.toString(user.getBody().getId()));
        }
        Long userId = user.getBody().getId();

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
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
                null,
                cartItem.getCart().getId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }

    @Override
    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        // Assume customerId is derived from the security context or request

        ResponseEntity<UserDTO> user = userClient.findUser(cartItemDTO.getUserId());
        Long userId = user.getBody().getId();

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
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
                cartItemDTO.getUserId(),
                updatedCartItem.getCart().getId(),
                updatedCartItem.getProductId(),
                updatedCartItem.getQuantity(),
                updatedCartItem.getPrice()
        );



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
