package com.mobydick.hms.room.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 객실관리 컨트롤러
@Controller
@RequestMapping("/room")
public class RoomController {

    @GetMapping("/list")
    public String main(Model model) {

        model.addAttribute("bodyPage", "room/room.jsp");
        return "index";

    }

}
