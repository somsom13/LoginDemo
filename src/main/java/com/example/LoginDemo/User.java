package com.example.LoginDemo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data //getter, setter
@Entity //이 class를 JPA가 관리하고 이름을 '엔티티' 라 부른다 => JPA로 실제 테이블과 매핑해서 쓰려면 이 어노테이션이 필수!
@NoArgsConstructor //엔티티는 파라미터가 없는 기본생성자가 필수 -> 이 어노테이션으로 만들어준다
public class User {

    @Id //primary_key 로 사용할 값
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id는 보통 auto_increment -> MySQL의 경우에는 GenerationType.IDENTITY 사용
    @Column(name="user_id") //컬럼 매핑
    private Long id;

    @Column(nullable = false) //null 허용 X
    private String name;

    @Column(unique = true, length=20, nullable = false) //유니크 (중복 허용하지 않음), 최대 길이 제한
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; //길이 제한 안두면 varchar(255)로 생성 -> 암호화된 비밀번호 저장하기에는 충분한 값

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public User(String name, String nickname, String email, String pwd, LocalDateTime time){
        this.name=name;
        this.nickname=nickname;
        this.email=email;
        this.password=pwd;
        this.createdDate=time;
    }
}
