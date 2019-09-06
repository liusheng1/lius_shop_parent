package com.ls.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class RegisterContorller {
    private static final String MB_REGISTEWR = "member/register";

    @GetMapping("/register")
    public String getRegister() {
        return MB_REGISTEWR;
    }
    @PostMapping("/register")
    public String postRegister() {
        return MB_REGISTEWR;
    }
}
