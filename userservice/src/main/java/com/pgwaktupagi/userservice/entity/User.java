package com.pgwaktupagi.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private String profilePicturePath;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
