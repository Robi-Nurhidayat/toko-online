package com.pgwaktupagi.userservice.service.impl;

import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.entity.User;
import com.pgwaktupagi.userservice.repository.UserRepository;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();


        // pindahkan user ke user dto
        for (var user: userList) {

            UserDTO userDTO = UserDTO
                    .builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .phoneNumber(user.getPhoneNumber())
                    .address(user.getAddress())
                    .profilePicturePath("avatar.jpg")
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();

            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return null;
    }
}
