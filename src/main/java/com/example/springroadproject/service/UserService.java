package com.example.springroadproject.service;

import com.example.springroadproject.dto.UserRequestDto;
import com.example.springroadproject.dto.UserResponseDto;
import com.example.springroadproject.entity.User;
import com.example.springroadproject.repository.UserRepository;
import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserRequestDto userRequestDto) {
        if(userRepository.findByUsername(userRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저명입니다.");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto,encodedPassword);
        userRepository.save(user);
    }

    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();
        //db에 이름 여부 파악
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당이름이 없습니다."));
        //비밀번호 일치
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public UserResponseDto updateProfile(Long id, UserRequestDto userRequestDto, UserDetailsImpl userDetailsImpl) {
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        if(userRequestDto.getNewPassword()!=null) {
            if (userRequestDto.getPassword() == null) {
                throw new IllegalArgumentException("기존 비밀번호를 입력해야 새로운 비밀번호 변경가능");
            }
            if (!passwordEncoder.matches(userRequestDto.getPassword(), userDetailsImpl.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            String encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
            User updatedUser = user.updateWithNewPW(userRequestDto, encodedPassword);
            return new UserResponseDto(updatedUser);
        }else{
            user.update(userRequestDto);
            return new UserResponseDto(user);
        }
    }
}
