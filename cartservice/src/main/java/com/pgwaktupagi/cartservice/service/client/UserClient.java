package com.pgwaktupagi.cartservice.service.client;

import com.pgwaktupagi.cartservice.dto.UserDTO;
import com.pgwaktupagi.cartservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users", url = "http://localhost:8001/api/users")
public interface UserClient {
    @GetMapping("/find")
    public ResponseEntity<UserDTO> findByid(@RequestParam Long userId);
}
