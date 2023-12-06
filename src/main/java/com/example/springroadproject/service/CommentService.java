package com.example.springroadproject.service;

import com.example.springroadproject.dto.CommentRequestDto;
import com.example.springroadproject.dto.CommentResponseDto;
import com.example.springroadproject.entity.Comment;
import com.example.springroadproject.entity.Post;
import com.example.springroadproject.repository.CommentRepository;
import com.example.springroadproject.repository.PostRepository;
import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<CommentResponseDto> getComments() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> commentsList = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            commentsList.add(new CommentResponseDto(commentList.get(i)));
        }
        return commentsList;
    }

    @Transactional
    public void modifyComment(Long id, Long commentId, CommentRequestDto req, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(("해당 댓글이 없습니다.")));
        if (!Objects.equals(comment.getUser().getId(), user.getUser().getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다");
        }
        comment.update(req);
    }
    public void deleteComment(Long id, Long commentId, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(("해당 댓글이 없습니다.")));
        if (!Objects.equals(comment.getUser().getId(), user.getUser().getId())) {
            throw new IllegalArgumentException("댓글 작성자만 삭제 가능합니다");
        }
        commentRepository.delete(comment);
    }
}
