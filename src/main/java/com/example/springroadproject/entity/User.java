package com.example.springroadproject.entity;

import com.example.springroadproject.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;


    public User(UserRequestDto userRequestDto) {
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        this.introduction = userRequestDto.getIntroduction();
        this.address = userRequestDto.getAddress();
        this.phone = userRequestDto.getPhone();
    }
}
