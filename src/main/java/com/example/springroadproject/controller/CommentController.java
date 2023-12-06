package com.example.springroadproject.controller;

import com.example.springroadproject.dto.CommentRequestDto;
import com.example.springroadproject.dto.CommentResponseDto;
import com.example.springroadproject.dto.CommonResponseDto;
import com.example.springroadproject.security.UserDetailsImpl;
import com.example.springroadproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommonResponseDto> postComment(@PathVariable Long id, @RequestBody CommentRequestDto req, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentRes = commentService.createComment(id,req,userDetails);
            return ResponseEntity.ok().body(commentRes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
