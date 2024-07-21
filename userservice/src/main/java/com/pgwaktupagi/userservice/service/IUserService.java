package com.pgwaktupagi.userservice.service;

import com.pgwaktupagi.userservice.dto.UserDTO;

import java.util.List;

public interface IUserService {

     List<UserDTO> getAllUsers();
    public UserDTO save(UserDTO userDTO);


}
