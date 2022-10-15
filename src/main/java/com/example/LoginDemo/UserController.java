package com.example.LoginDemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public int Login(@RequestBody LoginDto loginDto){
        User user = userService.authenticateLogin(loginDto);
        return user==null?304:200;
    }

    @PostMapping("/join")
    public int Join(@RequestBody JoinDto joinDto){
        if(userService.findUserByEmail(joinDto.getEmail())!=null){
            return 409;
        }
        if(userService.findUserByNickname(joinDto.getNickname())!=null){
            return 409;
        }
        userService.join(joinDto);
        return 201;
    }

}
