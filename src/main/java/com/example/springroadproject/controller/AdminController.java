package com.example.springroadproject.controller;

import com.example.springroadproject.dto.*;
import com.example.springroadproject.entity.Post;
import com.example.springroadproject.entity.UserRoleEnum;
import com.example.springroadproject.security.UserDetailsImpl;
import com.example.springroadproject.service.PostService;
import com.example.springroadproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {
    private final UserService userService;
    private final PostService postService;
    //관리자 권한 게시글 전체 수정

    @Secured(UserRoleEnum.Authority.ADMIN) // 관리자용 테스트
    @GetMapping("/products/secured")
    public String getProductsByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            System.out.println("authority.getAuthority() = " + authority.getAuthority());
        }

        return "완료";
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/users")
    public List<UserResponseDto> getProfileByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails){
         return userService.getProfileByAdmin(userDetails);
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PatchMapping("/users/{userId}")
    public ResponseEntity<CommonResponseDto> updateProfileByAdmin(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        try {
            userService.updateProfileByAdmin(userId,userRequestDto,userDetailsImpl);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long userId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.deleteUser(userId,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("회원정보 삭제 완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }


}
