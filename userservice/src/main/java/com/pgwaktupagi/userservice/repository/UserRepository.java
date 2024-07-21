package com.pgwaktupagi.userservice.repository;

import com.pgwaktupagi.userservice.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
