package com.example.LoginDemo;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public User authenticateLogin(LoginDto loginDto,HttpServletRequest request){
        User user = findUserByEmail(loginDto.getEmail());
        if(user!=null){
            if(bCryptPasswordEncoder.matches(loginDto.getPassword(),user.getPassword())){
                createSession(user,request);
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

    private void createSession(User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user",new SessionUser(user));
        session.setMaxInactiveInterval(2*60*60); //2시간 제한 시간
    }
}
