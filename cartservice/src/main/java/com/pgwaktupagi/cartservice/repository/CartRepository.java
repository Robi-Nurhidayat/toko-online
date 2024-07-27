package com.pgwaktupagi.cartservice.repository;

import com.pgwaktupagi.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByCustomerId(Long customerId);
}
