package com.mobydick.hms.notice.controller;

import com.mobydick.hms.notice.service.NoticeService;
import com.mobydick.hms.notice.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 공지사항 컨트롤러
@Controller
@RequestMapping("/anno")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String notice(Model model) throws Exception {

        List<NoticeVO> noticeList = noticeService.selectAllNotices(); // 공지사항 목록

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("bodyPage", "notice/notice.jsp");
        return "index";

    }

    @PostMapping("/notice")
    public ResponseEntity<String> insertNotice(@RequestBody NoticeVO noticeVO) throws Exception{
        noticeService.insertNotice(noticeVO);
        return ResponseEntity.ok("공지 등록 성공");
    }

}
