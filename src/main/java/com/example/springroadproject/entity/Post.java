package com.example.springroadproject.entity;

import com.example.springroadproject.dto.PostRequestDto;
import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetailsImpl) {
        this.title = postRequestDto.getTitle();
        this.content= postRequestDto.getContent();
        this.user = userDetailsImpl.getUser();
    }

}
