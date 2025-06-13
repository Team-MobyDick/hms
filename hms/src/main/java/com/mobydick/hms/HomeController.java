package com.mobydick.hms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 메인화면 이동용 컨트롤러
@Controller
public class HomeController {

    @GetMapping("/")
    public String main(Model model) {

        model.addAttribute("screenTitle", "대시보드");
        model.addAttribute("bodyPage", "common/common.jsp");
        return "index";

    }

} // class 끝