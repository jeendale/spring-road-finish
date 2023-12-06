package com.example.springroadproject.entity;

import com.example.springroadproject.dto.CommentRequestDto;
import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, CommentRequestDto req, UserDetailsImpl userDetails) {
        this.post = post;
        this.user = userDetails.getUser();
        this.text = req.getText();
    }

    public void update(CommentRequestDto req) {
        this.text = req.getText();
    }
}