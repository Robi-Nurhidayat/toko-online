// Di dalam cartservice, buat package yang sesuai, misalnya: com.pgwaktupagi.cartservice.dto

package com.pgwaktupagi.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
