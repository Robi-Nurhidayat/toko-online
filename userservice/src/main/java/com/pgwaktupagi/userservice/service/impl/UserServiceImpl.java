package com.pgwaktupagi.userservice.service.impl;

import com.pgwaktupagi.userservice.repository.UserRepository;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private final UserRepository userRepository;

}
