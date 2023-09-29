package com.example.JPARestAPI.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "user_auth")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "auth")
    @Enumerated(value = EnumType.STRING)
    private UserAuthEnum auth;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.auth = UserAuthEnum.USER;
    }

    public User(String username, String password, UserAuthEnum auth){
        this.username = username;
        this.password = password;
        this.auth = auth;
    }
}
