package com.example.LoginDemo;

import lombok.Data;

import java.io.Serializable;

@Data

//HTTP 세션에 저장할 사용자정보
public class SessionUser implements Serializable {
    private String name, email, nickname;
    private Long id;

    public SessionUser(User user){
        this.name=user.getName();
        this.email=user.getEmail();
        this.nickname=user.getNickname();
        this.id=user.getId();
    }
}
