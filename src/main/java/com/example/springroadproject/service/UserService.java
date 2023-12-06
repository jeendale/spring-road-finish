package com.example.springroadproject.service;

import com.example.springroadproject.dto.UserRequestDto;
import com.example.springroadproject.entity.User;
import com.example.springroadproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signup(UserRequestDto userRequestDto) {
        if(userRepository.findByUsername(userRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }
        User user = new User(userRequestDto);
        userRepository.save(user);
    }
}
