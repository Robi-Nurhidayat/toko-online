package com.pgwaktupagi.cartservice.service.client;

import com.pgwaktupagi.cartservice.dto.ResponseProduct;
import com.pgwaktupagi.cartservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserClient{

    @Override
    public ResponseEntity<UserDTO> findUser(Long userId) {
        return null;
    }
}
