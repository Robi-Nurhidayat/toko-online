package com.pgwaktupagi.userservice.service;

import com.pgwaktupagi.userservice.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

     List<UserDTO> getAllUsers();
    public UserDTO save(String userJson, MultipartFile image);


}
