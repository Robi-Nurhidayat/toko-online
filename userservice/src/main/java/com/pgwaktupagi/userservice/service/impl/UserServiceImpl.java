package com.pgwaktupagi.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.dto.UsersMsgDto;
import com.pgwaktupagi.userservice.entity.User;
import com.pgwaktupagi.userservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.userservice.exception.UserAlreadyExistsException;
import com.pgwaktupagi.userservice.repository.UserRepository;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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


    private final StreamBridge streamBridge;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private final UserRepository userRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/userservice/uploads/";

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
                    .profile(user.getProfile().substring(user.getProfile().indexOf("_")+1))
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();

            // Generate image URL
            try {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/users/image/")
                        .path(user.getProfile())
                        .toUriString();
                userDTO.setProfileUrl(fileDownloadUri);
            } catch (Exception e) {
                log.error("Error generating image URL for product: " + user.getUsername(), e);
                userDTO.setProfileUrl(null); // Set to null or handle accordingly
            }
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    // untuk send ke rabbitmq
    private void sendCommunication(UserDTO userDTO) {
        var userMsgDto = new UsersMsgDto(userDTO.getId(), userDTO.getUsername(),userDTO.getEmail(),userDTO.getPhoneNumber());
        System.out.println("Sending communication request for the details: " + userMsgDto);
        var result = streamBridge.send("sendCommunication-out-0",userMsgDto);
        logger.info("Is the communication request successfully processed ? : {}",result);
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

        sendCommunication(userDTO);
        return userDTO;

    }

    @Override
    public UserDTO findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User","email", email)
        );

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .profile(user.getProfile().substring(user.getProfile().indexOf("_")+1))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        // Generate image URL
        try {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/users/image/")
                    .path(user.getProfile())
                    .toUriString();
            userDTO.setProfileUrl(fileDownloadUri);
        } catch (Exception e) {
            log.error("Error generating image URL for product: " + user.getUsername(), e);
            userDTO.setProfileUrl(null); // Set to null or handle accordingly
        }


        return userDTO;
    }

    @Override
    public UserDTO update(String userJson, MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = null;

        try {
            userDTO = objectMapper.readValue(userJson, UserDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        User user = userRepository.findById(userDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", "tidak ada")
        );

        log.info(userJson);

        if (!image.isEmpty()) {
            // sekarang ada 7 image
            Path getPathImageForDelete = Path.of(UPLOAD_DIR+user.getProfile());
            if (Files.exists(getPathImageForDelete)) {
                System.out.println("file ini ada " + getPathImageForDelete);
                try {
                    Files.deleteIfExists(getPathImageForDelete);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);

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

            // update nama image dengan image yang baru
            user.setProfile(fileName);

        }


        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());


        user = userRepository.save(user);


        userDTO.setId(user.getId());
        userDTO.setProfile(user.getProfile());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }

    @Override
    public boolean deleteUser(Long userId) {

        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id",Long.toString(userId))
        );

        userRepository.deleteById(userId);

        return true;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", Long.toString(userId))
        );
    }

    @Override
    public boolean updateCommunication(Long userId) {

        boolean isUpdate = false;
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId.toString())
            );

            user.setCommunicationSw(true);
            userRepository.save(user);
            isUpdate = true;
        }
        return isUpdate;
    }


}
