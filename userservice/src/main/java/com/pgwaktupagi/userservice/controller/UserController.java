package com.pgwaktupagi.userservice.controller;

import com.pgwaktupagi.userservice.constant.UserConstants;
import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.dto.UserResponse;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getAllUser() {
        List<UserDTO> allUsers = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(UserConstants.STATUS_200,UserConstants.MESSAGE_200,allUsers));
    }


}
