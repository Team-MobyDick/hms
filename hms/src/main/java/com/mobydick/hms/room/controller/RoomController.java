package com.mobydick.hms.room.controller;

import com.mobydick.hms.room.service.RoomService;
import com.mobydick.hms.room.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 객실관리 컨트롤러
@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public String room(Model model) throws Exception {

        // DB에서 객실 정보 조회
        List<RoomVO> roomList = roomService.selectAllRooms();

        model.addAttribute("roomList", roomList);
        model.addAttribute("bodyPage", "room/room.jsp");

        return "index";

    }

}
