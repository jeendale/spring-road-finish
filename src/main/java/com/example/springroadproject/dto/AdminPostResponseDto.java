package com.example.springroadproject.dto;

import com.example.springroadproject.entity.AdminPost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminPostResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AdminPostResponseDto(AdminPost savePost) {
        this.id = savePost.getId();
        this.title = savePost.getTitle();
        this.content = savePost.getContent();
        this.createdAt = savePost.getCreatedAt();
        this.modifiedAt = savePost.getModifiedAt();
    }
}
