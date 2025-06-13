package com.mobydick.hms.login.controller;

import com.mobydick.hms.login.service.LoginService;
import com.mobydick.hms.login.vo.LoginVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 로그인 컨트롤러
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    // 로그인 처리
    @PostMapping("/login")
    public String main(@RequestParam("empl_id") String empl_id,
                       Model model,
                       HttpSession session) throws Exception {
        System.out.println("emplId = " + empl_id);

        LoginVO loginUser = loginService.selectEmployeeById(empl_id);

        System.out.println("loginUser = " + loginUser);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            model.addAttribute("screenTitle", "대시보드");
            model.addAttribute("bodyPage", "common/common.jsp");
            return "redirect:/";
        } else {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            model.addAttribute("bodyPage", "login/login.jsp");
        }

        return "index";
    }
}
