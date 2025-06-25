package com.mobydick.hms.notice.controller;

import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.notice.service.NoticeService;
import com.mobydick.hms.notice.vo.NoticeVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 공지사항 목록
    @GetMapping("/list")
    public String notice(
            Model model,
            @RequestParam(defaultValue = "1") int page, // 현재 페이지 번호
            @RequestParam(defaultValue = "10") int pageSize // 페이지당 항목 수
    ) {
        try {
            int totalCount = noticeService.selectAllNotices().size();
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            int startIndex = (page - 1) * pageSize;

            List<NoticeVO> allNotices = noticeService.selectAllNotices();
            List<NoticeVO> noticeList;

            if (startIndex < totalCount) {
                noticeList = allNotices.subList(startIndex, Math.min(startIndex + pageSize, totalCount));
            } else {
                noticeList = new ArrayList<>();
            }

            model.addAttribute("noticeList", noticeList);
            model.addAttribute("screenTitle", "공지사항");
            model.addAttribute("mode", "list");
            model.addAttribute("bodyPage", "notice/notice.jsp");

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "목록 조회 중 오류 발생");
            return "error";
        }
    }

    // 공지사항 등록 폼
    @GetMapping("/noticeForm")
    public String showForm(Model model) {
        model.addAttribute("notice", new NoticeVO());  // 빈 객체
        model.addAttribute("mode", "add");
        model.addAttribute("screenTitle", "공지사항 등록");
        model.addAttribute("bodyPage", "notice/noticeForm.jsp");
        return "index";
    }

    // 공지사항 수정 폼
    @GetMapping("/update/{noticeId}")
    public String showUpdateForm(@PathVariable int noticeId, Model model) {
        try {
            NoticeVO notice = noticeService.selectNoticeById(noticeId);
            model.addAttribute("notice", notice);
            model.addAttribute("mode", "edit");
            model.addAttribute("screenTitle", "공지사항 수정");
            model.addAttribute("bodyPage", "notice/noticeForm.jsp");
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항 조회 오류");
            return "error";
        }
    }

    // 공지사항 등록 처리
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addNoticeApi(NoticeVO noticeVO, HttpSession session) {
        try {
            String loginUserId = "test";
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser != null) {
                loginUserId = loginUser.getEmplId();
            }
            noticeVO.setEmplId(loginUserId);
            noticeVO.setCreatedId(loginUserId);
            noticeVO.setUpdatedId(loginUserId);

            noticeService.insertNotice(noticeVO);
            return ResponseEntity.ok("등록 성공");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("등록 실패: " + e.getMessage());
        }
    }

    // 공지사항 수정 처리
    @PostMapping("/update/{noticeId}")
    @ResponseBody
    public ResponseEntity<String> updateNoticeApi(@PathVariable int noticeId,
                                                  NoticeVO updateVO,
                                                  HttpSession session) {
        try {
            String loginUserId = "test";
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser != null) {
                loginUserId = loginUser.getEmplId();
            }

            updateVO.setNoticeId(noticeId);
            updateVO.setUpdatedId(loginUserId);

            noticeService.updateNotice(updateVO);
            return ResponseEntity.ok("수정 성공");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("수정 실패: " + e.getMessage());
        }
    }

    // 전체 공지사항 AJAX 조회
    @GetMapping("/notices")
    @ResponseBody
    public ResponseEntity<List<NoticeVO>> getAllNoticesApi() {
        try {
            List<NoticeVO> list = noticeService.selectAllNotices();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 단건 공지사항 AJAX 조회
    @GetMapping("/detail/{noticeId}")
    @ResponseBody
    public ResponseEntity<NoticeVO> getNoticeDetailApi(@PathVariable int noticeId) {
        try {
            NoticeVO notice = noticeService.selectNoticeById(noticeId);
            if (notice == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 공지사항 삭제 처리
    @DeleteMapping("/delete/{noticeId}")
    @ResponseBody
    public ResponseEntity<String> deleteNoticeApi(@PathVariable("noticeId") int noticeId) {
        try {
            noticeService.deleteNotice(noticeId);
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("삭제 실패: " + e.getMessage());
        }
    }
}
