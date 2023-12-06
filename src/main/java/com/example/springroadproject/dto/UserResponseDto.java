package com.example.springroadproject.dto;

import com.example.springroadproject.entity.User;

public class UserResponseDto extends CommonResponseDto{
    private String username;
    private String introduction;
    private String address;
    private String phone;

    public UserResponseDto(User updatedUser) {
        this.username = updatedUser.getUsername();
        this.introduction = updatedUser.getIntroduction();
        this.address = updatedUser.getAddress();
        this.phone = updatedUser.getPhone();
    }
}
