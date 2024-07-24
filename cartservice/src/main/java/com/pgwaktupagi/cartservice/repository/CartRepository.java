package com.pgwaktupagi.cartservice.repository;

import com.pgwaktupagi.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
