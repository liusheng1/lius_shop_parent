package com.ls.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 登录请求
 */
@Controller
public class LoginContorller {
    private static final String MB_LOGION = "member/login";

    @GetMapping("/login")
    public String getLogin() {
        return MB_LOGION;
    }

    @PostMapping("/login")
    public String postLogin() {

        return MB_LOGION;
    }
}
