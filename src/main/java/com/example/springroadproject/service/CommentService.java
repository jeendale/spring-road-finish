package com.example.springroadproject.service;

import com.example.springroadproject.dto.CommentRequestDto;
import com.example.springroadproject.dto.CommentResponseDto;
import com.example.springroadproject.entity.Comment;
import com.example.springroadproject.entity.Post;
import com.example.springroadproject.repository.CommentRepository;
import com.example.springroadproject.repository.PostRepository;
import com.example.springroadproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long id, CommentRequestDto req, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        if (req.getText() == null) {
            throw new IllegalArgumentException("내용을 입력하세요");
        }
        Comment comment = new Comment(post, req, userDetails);
        Comment saveComment = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }
}
