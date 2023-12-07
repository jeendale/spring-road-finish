package com.example.springroadproject.controller;

import com.example.springroadproject.dto.CommonResponseDto;
import com.example.springroadproject.dto.UserRequestDto;
import com.example.springroadproject.dto.UserResponseDto;
import com.example.springroadproject.jwt.JwtUtil;
import com.example.springroadproject.security.UserDetailsImpl;
import com.example.springroadproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto>signup(@Valid @RequestBody UserRequestDto userRequestDto){
        try {
            userService.signup(userRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        try {
            userService.login(userRequestDto);
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername()));
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto> updateProfile(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        try {
            userService.updateProfile(id,userRequestDto,userDetailsImpl);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료",HttpStatus.OK.value()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto> logout(HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userDetails){
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.logoutToken());
            return ResponseEntity.ok().body(new CommonResponseDto("로그아웃 완료",HttpStatus.OK.value()));
    }
}
