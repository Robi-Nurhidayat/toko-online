package com.pgwaktupagi.userservice.service;

import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUsers();
    UserDTO save(String userJson, MultipartFile image);
    UserDTO findUser(String email);
    UserDTO update(String userJson, MultipartFile image);
    boolean deleteUser(Long userId);
    User findById(Long userId);


}
