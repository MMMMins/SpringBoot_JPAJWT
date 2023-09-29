package com.example.JPARestAPI.DTO;


import com.example.JPARestAPI.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String username;

    public UserResponseDTO(User user){
        username = user.getUsername();
    }
}
