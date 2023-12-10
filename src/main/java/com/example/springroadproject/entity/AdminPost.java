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
public class AdminPost extends Timestamped{
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

    public AdminPost(PostRequestDto requestDto, UserDetailsImpl userDetails){
        this.title=requestDto.getTitle();
        this.content= requestDto.getContent();
        this.user=userDetails.getUser();
    }
    public void adminPostUpdate(PostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.content=requestDto.getContent();
    }
}

