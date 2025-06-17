package com.mobydick.hms.notice.controller;

import com.mobydick.hms.login.vo.LoginVO;   // 로그인 VO 임포트 (로그인 기능 연동 시 사용)
import com.mobydick.hms.notice.service.NoticeService;
import com.mobydick.hms.notice.vo.NoticeVO;
import jakarta.servlet.http.HttpSession;    // 세션에서 로그인 정보 가져올 때 사용
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    // jsp로 데이터 전달
import org.springframework.web.bind.annotation.*; // // @GetMapping, @PostMapping, @RequestParam, @ModelAttribute 등 사용


import java.util.List;

// 공지사항 컨트롤러
@Controller
@RequestMapping("/anno")    // 이 컨트롤러의 모든 경로는 /anno로 시작
public class NoticeController {

    @Autowired  // 스프링이 NoticeService 구현체를 자동으로 연결해줌
    private NoticeService noticeService;

    // 공지사항 목록 (최초 진입 시 jsp 렌더링)
    // URL: /anno/list
    @GetMapping("/list")
    public String notice(Model model) throws Exception {

        List<NoticeVO> noticeList = noticeService.selectAllNotices(); // 모든 공지사항 데이터 가져오기
        model.addAttribute("noticeList", noticeList);   // "noticeList"라는 이름으로 데이터를 jsp에 전달

        //  jsp 템플릿 구조를 사용할 때, 메인 레이아웃(index.jsp)에 특정 컨텐츠 페이지를 삽입
        model.addAttribute("screenTitle", "공지사항 목록");    // 화면 상단 타이틀
        model.addAttribute("mode", "list"); // 모드 설정: 목록 화면
        model.addAttribute("bodyPage", "notice/notice.jsp");    // index.jsp가 포함할 실제 내용 페이지

        return "index"; // "index.jsp"가 bodyPage를 include하도록 설정
    }

    // 모든 공지사항 데이터 조회 (ajax 호출용)
    // URL: /anno/notices
    @GetMapping("notices")
    @ResponseBody
    public ResponseEntity<List<NoticeVO>> getAllNoticesApi() {
        try {
            List<NoticeVO> noticeList = noticeService.selectAllNotices();
            return ResponseEntity.ok(noticeList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 공지사항 상세 (ajax 호출용)
    // URL: /ann/detail/{noticeId}
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

    // 공지사항 등록 (ajax POST 요청)
    // URL: /anno/add
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addNoticeApi(NoticeVO noticeVO, HttpSession session) {
        try {
            String loginUserId = "test";
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser != null) {
                loginUserId = loginUser.getEmplId();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            noticeVO.setEmplId(loginUserId);
            noticeVO.setCreatedId(loginUserId);

            noticeService.insertNotice(noticeVO);
            return ResponseEntity.ok("공지사항 등록 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("공지사항 등록 실패:" + e.getMessage());
        }
    }

    // 공지사항 수정 (ajax PUT 요청)
    // URL: /anno/update/{noticeId} (PUT)
    @PutMapping("/update/{noticeId}")
    @ResponseBody
    public ResponseEntity<String> updateNoticeApi(@PathVariable int noticeId, NoticeVO updateNoticeVO, HttpSession session) {
        try {
            String loginUserId = "test";
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser != null) {
                loginUserId = loginUser.getEmplId();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            updateNoticeVO.setNoticeId(noticeId);
            updateNoticeVO.setUpdatedId(loginUserId);

            noticeService.updateNotice(updateNoticeVO);
            return ResponseEntity.ok("공지사항 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("공지사항 수정 실패:" + e.getMessage());
        }
    }


    // 공지사항 삭제 (ajax DELETE 요청)
    // URL: /anno/delete/{noticeId} (DELETE)
    @DeleteMapping("/delete/{noticeId}")
    @ResponseBody
    public ResponseEntity<String> deleteNoticeApi(@PathVariable("noticeId") int noticeId) {
        try {
            noticeService.deleteNotice(noticeId);
            return ResponseEntity.ok("공지사항 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 삭제 실패:" + e.getMessage());
        }
    }

}
