package com.custommock.backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
