package com.example.JPARestAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    private boolean admin = false;      // 디폴트 값은 false. 관리자 권한일 경우 true 로 변한다
    private String adminToken = "";
}
