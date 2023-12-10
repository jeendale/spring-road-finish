package com.example.springroadproject.controller;

import com.example.springroadproject.dto.*;
import com.example.springroadproject.entity.UserRoleEnum;
import com.example.springroadproject.security.UserDetailsImpl;
import com.example.springroadproject.service.PostService;
import com.example.springroadproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PostMapping("/notices")
    public ResponseEntity<CommonResponseDto> noticePost(@RequestBody PostRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        AdminPostResponseDto adminPostResponseDto=postService.createPostByAdmin(requestDto,userDetails);
        return ResponseEntity.ok().body(new CommonResponseDto("공지 작성완료",HttpStatus.OK.value()));
    }

    @GetMapping("/notices/{id}")
    public ResponseEntity<CommonResponseDto> getNoticePost(@PathVariable Long id){
        try{

            return ResponseEntity.ok().body(postService.getNoticePost(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }

    }

    @GetMapping("/notices")
    public List<AdminPostResponseDto> getNoticePostList(){
        return postService.getNoticePostList();
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PatchMapping("/notices/{id}")
    public ResponseEntity<CommonResponseDto> updataNoticePost(@PathVariable Long id,@RequestBody PostRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postService.updateNoticePost(id,requestDto,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("수정완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/notices/{id}")
    public ResponseEntity<CommonResponseDto>deleteNoticePost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postService.deleteNoticePost(id,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> updatePostByAdmin(@PathVariable Long postId,@RequestBody PostRequestDto requestDto){
        try {
            postService.updatePostByAdmin(requestDto,postId);
            return ResponseEntity.ok().body(new CommonResponseDto("수정완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> deletePostByAdmin(@PathVariable Long postId){
        try {
            postService.deletePostByAdmin(postId);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제되었습니다.",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
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

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PatchMapping("/users/{userId}/promote")
    public ResponseEntity<CommonResponseDto> promoteUser(@PathVariable Long userId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.promoteUser(userId,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("해당 회원을 승격시켰습니다.",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }


}
