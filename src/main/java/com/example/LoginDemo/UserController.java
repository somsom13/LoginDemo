package com.example.LoginDemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public int Login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        User user = userService.authenticateLogin(loginDto,request);
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

    @GetMapping("/member")
    public String Member(@SessionAttribute(name = "user",required = false) SessionUser sessionUser){
        if(sessionUser==null){
            return "login need";
        }
        return sessionUser.getNickname();
    }

    @PostMapping("/logout")
    public String Logout(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "logout";
    }

}
