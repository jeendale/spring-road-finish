package com.example.springroadproject.dto;

import com.example.springroadproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends CommonResponseDto{
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment saveComment) {
        this.text = saveComment.getText();
        this.createdAt = saveComment.getCreatedAt();
        this.modifiedAt = saveComment.getModifiedAt();
    }
}
