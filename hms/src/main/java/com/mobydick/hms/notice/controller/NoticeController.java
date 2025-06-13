package com.mobydick.hms.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 공지사항 컨트롤러
@Controller
@RequestMapping("/anno")
public class NoticeController {

    @GetMapping("/list")
    public String main(Model model) {

        model.addAttribute("bodyPage", "notice/notice.jsp");
        return "index";

    }

}
