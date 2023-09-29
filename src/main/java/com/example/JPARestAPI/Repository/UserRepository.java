package com.example.JPARestAPI.Repository;

import com.example.JPARestAPI.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Select * from User where username = ?
    Optional<User> findByUsername(String username);

}
