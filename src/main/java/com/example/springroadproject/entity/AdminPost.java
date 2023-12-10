package com.example.springroadproject.entity;

import com.example.springroadproject.dto.AdminPostRequestDto;
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

    public AdminPost(AdminPostRequestDto requestDto, UserDetailsImpl userDetails){
        this.title=requestDto.getTitle();
        this.content= requestDto.getContent();
        this.user=userDetails.getUser();
    }
    public void AdminPostUpdate(AdminPostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.content=requestDto.getContent();
    }
}

