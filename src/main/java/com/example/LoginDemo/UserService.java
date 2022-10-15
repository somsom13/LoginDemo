package com.example.LoginDemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User authenticateLogin(LoginDto loginDto){
        User user = findUserByEmail(loginDto.getEmail());
        if(user!=null){
            if(bCryptPasswordEncoder.matches(loginDto.getPassword(),user.getPassword())){
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findUserByNickname(String nickname){
        return userRepository.findByNickname(nickname).orElse(null);
    }

    public void join(JoinDto joinDto){
        User user = User.builder()
                .name(joinDto.getName())
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .pwd(bCryptPasswordEncoder.encode(joinDto.getPwd()))
                .time(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

}
