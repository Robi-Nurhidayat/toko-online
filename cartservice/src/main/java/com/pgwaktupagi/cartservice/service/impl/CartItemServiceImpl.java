package com.pgwaktupagi.cartservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.cartservice.dto.CartItemDTO;
import com.pgwaktupagi.cartservice.dto.ProductDTO;
import com.pgwaktupagi.cartservice.dto.ResponseProduct;
import com.pgwaktupagi.cartservice.dto.UserDTO;
import com.pgwaktupagi.cartservice.entity.Cart;
import com.pgwaktupagi.cartservice.entity.CartItem;
import com.pgwaktupagi.cartservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.cartservice.repository.CartItemRepository;
import com.pgwaktupagi.cartservice.repository.CartRepository;
import com.pgwaktupagi.cartservice.service.ICartItemService;
import com.pgwaktupagi.cartservice.service.client.ProductClient;
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
    private final ProductClient productClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartItemDTO addToCartItem(CartItemDTO cartItemDTO) {

        ResponseEntity<UserDTO> user = userClient.findUser(cartItemDTO.getUserId());
        ResponseEntity<ResponseProduct> responseProductResponseEntity = productClient.findbyId(cartItemDTO.getProductId());
        System.out.println("Respons dari produk: " + responseProductResponseEntity.getBody());

        ResponseProduct responseProductId = objectMapper.convertValue(responseProductResponseEntity.getBody(), ResponseProduct.class);
        System.out.println("inii response  responseProductResponseEntity ISI NYA : " + responseProductId.getData());
        ProductDTO productDTO = objectMapper.convertValue(responseProductId.getData(),ProductDTO.class);
        System.out.println("INI BENERAN ISI PRODUCT DTO : " + productDTO );
        if (user.getBody() == null) {
                throw new ResourceNotFoundException("User","id", Long.toString(user.getBody().getId()));
        }

        Optional<CartItem> cartItemRepositoryByProductId = cartItemRepository.findByProductId(productDTO.getId());
        CartItem cartItem = new CartItem();
        if (cartItemRepositoryByProductId.isPresent()) {
            cartItem = cartItemRepositoryByProductId.get();
            System.out.println("CartItem ditemukan di repository: " + cartItem);
        } else {
            System.out.println("CartItem tidak ditemukan di repository untuk ID produk: " + productDTO.getId());
        }
        if (responseProductResponseEntity.getBody() == null) {
            throw new ResourceNotFoundException("Product","id", cartItemDTO.getProductId());
        }


        Long userId = user.getBody().getId();

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;




        if (optionalCart.isPresent()) {
            cart = optionalCart.get();

            System.out.println("isi dari cart item kedua : " + cartItem.getId());

           if (cartItem.getId() != null) {
               System.out.println("isi cart item di block if " + cartItem);
               CartItem finalCartItem = cartItem;
               cartItem = cart.getCartItems().stream()
                       .filter(items -> items.getId().equals(finalCartItem.getId()))
                       .findFirst()
                       .orElse(null);
               cartItem.setId(finalCartItem.getId());
               cartItem.setQuantity(cartItem.getQuantity() + finalCartItem.getQuantity());
               System.out.println("Cart item sebelum di save" + cartItem);
               cartItem = cartItemRepository.save(cartItem);
               return new CartItemDTO(
                       cartItem.getId(),
                       null,
                       cartItem.getCart().getId(),
                       cartItem.getProductId(),
                       cartItem.getQuantity(),
                       cartItem.getPrice()
               );
           } else {
               System.out.println("buat item baru dengan product berbeda");
               cartItem = new CartItem();
               cartItem.setCart(cart);
               cartItem.setProductId(cartItemDTO.getProductId());
               cartItem.setQuantity(cartItemDTO.getQuantity());
               cartItem.setPrice(cartItemDTO.getPrice());

               cartItem = cartItemRepository.save(cartItem);

               System.out.println("block jika belum ada product id tapi cart sudah ada");
               return new CartItemDTO(
                       cartItem.getId(),
                       null,
                       cartItem.getCart().getId(),
                       cartItem.getProductId(),
                       cartItem.getQuantity(),
                       cartItem.getPrice()
               );
           }

        } else {
            System.out.println("buat item baru");
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart);
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(cartItemDTO.getProductId());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setPrice(cartItemDTO.getPrice());

            cartItem = cartItemRepository.save(cartItem);

            System.out.println("block jika belum ada product id");
            return new CartItemDTO(
                    cartItem.getId(),
                    null,
                    cartItem.getCart().getId(),
                    cartItem.getProductId(),
                    cartItem.getQuantity(),
                    cartItem.getPrice()
            );
        }

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
