package com.example.LoginDemo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
