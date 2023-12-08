package com.example.springroadproject.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @Pattern(regexp ="^[a-z0-9]{4,10}$")
    private String username;

    @Pattern(regexp ="^[a-zA-Z0-9!@#$%^&*+=]{8,15}$")
    private String password;

    private String introduction;

    private String address;

    private String phone;

    //권한 체크 여부-> 체크하면 ture
    private boolean admin=false;

    //권한 키
    private String adminToken="";

    @Pattern(regexp ="^[a-zA-Z0-9!@#$%^&*+=]{8,15}$")
    private String newPassword;
}
