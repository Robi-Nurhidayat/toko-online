package com.pgwaktupagi.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.entity.User;
import com.pgwaktupagi.userservice.exception.UserAlreadyExistsException;
import com.pgwaktupagi.userservice.repository.UserRepository;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

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
                    .profile("avatar.jpg")
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();

            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public UserDTO save(String userJson, MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = null;

        try {
            userDTO = objectMapper.readValue(userJson, UserDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Optional<User> isEmailExits = userRepository.findByEmail(userDTO.getEmail());

        if (isEmailExits.isPresent()) {
            throw new UserAlreadyExistsException("User already exist in database with email " + userDTO.getEmail());
        }

        log.info(userJson);

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        log.info("Saving file to path: {}", path.toString());
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User user = User
                .builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .profile(fileName)
                .build();

        user = userRepository.save(user);


        userDTO.setId(user.getId());
        userDTO.setProfile(user.getProfile());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;

    }
}
