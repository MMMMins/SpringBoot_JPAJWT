package com.example.JPARestAPI.Service;

import com.example.JPARestAPI.DTO.UserRequestDTO;
import com.example.JPARestAPI.DTO.UserResponseDTO;
import com.example.JPARestAPI.Entity.User;
import com.example.JPARestAPI.Entity.UserAuthEnum;
import com.example.JPARestAPI.Exception.AllException;
import com.example.JPARestAPI.Jwt.JwtUtil;
import com.example.JPARestAPI.Repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.JPARestAPI.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public List<UserResponseDTO> info(User requestUser){
        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        if(requestUser.getAuth().equals(UserAuthEnum.ADMIN)){
            List<User> users = userRepository.findAll();
            for(User user : users){
                responseDTOList.add(new UserResponseDTO(user));
            }
        }else{
            Optional<User> user= userRepository.findByUsername(requestUser.getUsername());
            responseDTOList.add(new UserResponseDTO(user.get()));
        }
        return responseDTOList;
    }

    // 회원 가입
    public void signup(UserRequestDTO requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new AllException(DUPLICATED_USERNAME);
        }

        // 사용자 ROLE 확인 (admin = true 일 경우 아래 코드 수행)
        UserAuthEnum role = UserAuthEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new AllException(NOT_MATCH_ADMIN_TOKEN);
            }

            role = UserAuthEnum.ADMIN;
        }

        // 사용자 등록 (admin = false 일 경우 아래 코드 수행)
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    // 로그인
    public void login(UserRequestDTO loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AllException(NOT_MATCH_INFORMATION)
        );

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AllException(NOT_MATCH_INFORMATION);
        }

        // Header 에 key 값과 Token 담기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(user.getUsername(), user.getAuth()));
    }

    @Transactional
    public void update(User userinfo, UserRequestDTO userRequestDTO) {
        String username = userinfo.getUsername();
        String password = passwordEncoder.encode(userRequestDTO.getPassword());

        Optional<User> user = userRepository.findByUsername(username);
        user.get().setPassword(password);
    }
}
