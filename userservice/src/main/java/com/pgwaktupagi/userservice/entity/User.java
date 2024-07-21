package com.pgwaktupagi.userservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;


    private String password;


    private String email;


    private String fullName;

    private String phoneNumber;

    private String address;

    private String profilePicturePath; // Path or URL to the profile picture


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
