package com.example.springroadproject.service;

import com.example.springroadproject.dto.PostRequestDto;
import com.example.springroadproject.dto.PostResponseDto;
import com.example.springroadproject.entity.Post;
import com.example.springroadproject.repository.PostRepository;
import com.example.springroadproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetailsImpl) {
        Post post = new Post(postRequestDto,userDetailsImpl);
        Post savePost = postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }
}
