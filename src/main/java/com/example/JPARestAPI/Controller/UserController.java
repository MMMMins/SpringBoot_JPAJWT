package com.example.JPARestAPI.Controller;

import com.example.JPARestAPI.DTO.ResultMessageDTO;
import com.example.JPARestAPI.DTO.UserRequestDTO;
import com.example.JPARestAPI.DTO.UserResponseDTO;
import com.example.JPARestAPI.Security.UserDetailsImpl;
import com.example.JPARestAPI.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<List<UserResponseDTO>> info(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(userService.info(userDetails.getUser()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultMessageDTO> login(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response){
        userService.login(userRequestDTO, response);
        return ResponseEntity.ok(new ResultMessageDTO("로그인 완료", HttpStatus.OK.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultMessageDTO> signup(@RequestBody UserRequestDTO userRequestDTO){
        userService.signup(userRequestDTO);
        return ResponseEntity.ok(new ResultMessageDTO("회원가입성공",HttpStatus.OK.value()));
    }

    @PutMapping("/info")
    public ResponseEntity<ResultMessageDTO> update(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserRequestDTO userRequestDTO){
        userService.update(userDetails.getUser(), userRequestDTO);
        return ResponseEntity.ok(new ResultMessageDTO("정보반영완료", HttpStatus.OK.value()));
    }
}
