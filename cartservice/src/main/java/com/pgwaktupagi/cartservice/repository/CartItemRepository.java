package com.pgwaktupagi.cartservice.repository;

import com.pgwaktupagi.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
