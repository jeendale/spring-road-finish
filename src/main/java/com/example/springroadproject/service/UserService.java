package com.example.springroadproject.service;

import com.example.springroadproject.dto.LoginRequestDto;
import com.example.springroadproject.dto.PostResponseDto;
import com.example.springroadproject.dto.UserRequestDto;
import com.example.springroadproject.dto.UserResponseDto;
import com.example.springroadproject.entity.PwHistory;
import com.example.springroadproject.entity.User;
import com.example.springroadproject.entity.UserRoleEnum;
import com.example.springroadproject.jwt.JwtUtil;
import com.example.springroadproject.repository.PwRepository;
import com.example.springroadproject.repository.UserRepository;
import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PwRepository pwRepository;
    private final JwtUtil jwtUtil;
    //admin 토큰 부여
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(UserRequestDto userRequestDto) {
        if(userRepository.findByUsername(userRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저명입니다.");
        }

        UserRoleEnum role =UserRoleEnum.USER;
        if(userRequestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 인증 번호가 다릅니다.");
            }
            role=UserRoleEnum.ADMIN;
        }

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto,encodedPassword,role);
        userRepository.save(user);
        pwRepository.save(new PwHistory(user,encodedPassword));
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username =loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        //db에 이름 여부 파악
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당이름이 없습니다."));
        //비밀번호 일치
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //user.getRole() 사용위해 JWT생성 및 쿠키에 저장
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),user.getRole()));
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
            List<PwHistory>passwordList = pwRepository.findTop3ByUserIdOrderByCreatedAtDesc(id);
            for (PwHistory pwHistory : passwordList) {
                if(passwordEncoder.matches(userRequestDto.getNewPassword(), pwHistory.getPassword())){
                    throw new IllegalArgumentException("사용했던 비밀번호는 재사용할 수 없습니다.");
                }
            }
            PwHistory usedPW = new PwHistory(userDetailsImpl.getUser(),passwordEncoder.encode(userRequestDto.getNewPassword()));
            pwRepository.save(usedPW);
            String encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
            User updatedUser = user.updateWithNewPW(userRequestDto, encodedPassword);
            return new UserResponseDto(updatedUser);
        }else{
            user.update(userRequestDto);
            return new UserResponseDto(user);
        }
    }

    public List<UserResponseDto> getProfileByAdmin(UserDetailsImpl userDetails) {
        List<User>userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList= new ArrayList<>();
        for(int i=0; i<userList.size(); i++){
            userResponseDtoList.add(new UserResponseDto(userList.get(i)));
            log.info(userResponseDtoList.get(i).getUsername());
        }
        return userResponseDtoList;
    }

    @Transactional
    public UserResponseDto updateProfileByAdmin(Long userId, UserRequestDto userRequestDto, UserDetailsImpl userDetailsImpl) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        if(user.getRole()==UserRoleEnum.ADMIN){
            throw new IllegalArgumentException("다른 관리자의 정보는 수정할 수 없습니다.");
        }
        if(userRequestDto.getNewPassword()!=null) {
            PwHistory usedPW = new PwHistory(userDetailsImpl.getUser(),passwordEncoder.encode(userRequestDto.getNewPassword()));
            pwRepository.save(usedPW);
            String encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
            User updatedUser = user.updateWithNewPW(userRequestDto, encodedPassword);
            return new UserResponseDto(updatedUser);
        }else{
            user.update(userRequestDto);
            return new UserResponseDto(user);
        }

    }

    public void deleteUser(Long userId, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        if(user.getRole()==UserRoleEnum.ADMIN){
            throw new IllegalArgumentException("다른 관리자의 정보는 수정할 수 없습니다.");
        }
        userRepository.delete(user);
    }

    @Transactional
    public void promoteUser(Long userId, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        User promotedUser = user.promote(userId);
        userRepository.save(promotedUser);
    }
}
