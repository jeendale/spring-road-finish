package com.example.springroadproject.controller;

import com.example.springroadproject.dto.CommonResponseDto;
import com.example.springroadproject.dto.PostRequestDto;
import com.example.springroadproject.entity.UserRoleEnum;
import com.example.springroadproject.security.UserDetailsImpl;
import com.example.springroadproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class AdminController {
    private final PostService postService;
    //관리자 권한 게시글 전체 수정
/*    @Secured(UserRoleEnum.Authority.ADMIN)
    @PostMapping("/admin}")
    public ResponseEntity<CommonResponseDto> admidUpdatePost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl user){
        try{
            return ResponseEntity.ok().body(new CommonResponseDto("관리자 수정", HttpStatus.OK.value()));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }*/

    @Secured(UserRoleEnum.Authority.ADMIN) // 관리자용 테스트
    @GetMapping("/products/secured")
    public String getProductsByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            System.out.println("authority.getAuthority() = " + authority.getAuthority());
        }

        return "완료";
    }
}
