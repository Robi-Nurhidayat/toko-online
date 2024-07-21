package com.pgwaktupagi.userservice.mapper;

import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.entity.User;


public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
//        dto.setProfilePicturePath(user.getProfilePicturePath());
        return dto;

    }

    public static User mapToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
//        user.setProfilePicturePath(dto.getProfilePicturePath());
        return user;


    }
}
