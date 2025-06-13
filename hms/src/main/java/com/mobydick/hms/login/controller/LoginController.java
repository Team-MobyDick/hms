package com.mobydick.hms.login.controller;

import com.mobydick.hms.login.service.LoginService;
import com.mobydick.hms.login.vo.LoginVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 로그인 컨트롤러
@Controller
public class LoginController {

    // LoginService 주입
    @Autowired
    LoginService loginService;

    // 로그인 요청이 get일 경우
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
        }
        return "login/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("emplId") String emplId,
                        Model model,
                        HttpSession session) throws Exception {

        LoginVO loginUser = loginService.selectEmployeeById(emplId);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            return "redirect:/";
        }

        // 로그인 실패 시 GET 요청으로 리디렉트
        return "redirect:/login?error=true";
    }

}
