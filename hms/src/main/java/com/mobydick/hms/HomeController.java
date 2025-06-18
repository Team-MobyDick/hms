package com.mobydick.hms;

import com.mobydick.hms.common.service.CommonService; // CommonService import 추가
import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.login.vo.LoginVO; // LoginVO import 추가 (세션 체크용)
import jakarta.servlet.http.HttpSession; // HttpSession import 추가
import org.springframework.beans.factory.annotation.Autowired; // Autowired import 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List; // List import 추가

// 메인화면(대시보드) 이동용 컨트롤러
@Controller
public class HomeController {

    @Autowired
    private CommonService commonService; // CommonService 주입

    @GetMapping("/")
    public String main(Model model, HttpSession session) { // HttpSession 매개변수 추가

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            // 로그인되어 있지 않다면 로그인 페이지로 리다이렉트
            return "redirect:/login"; // 로그인 페이지 URL에 맞게 수정
        }

        try {
            // 1. 오늘의 사원 (직책 GR_03인 직원 중 3명 랜덤)
            List<EmployeeVO> employeesOfTheDay = commonService.getEmployeesOfTheDay();
            model.addAttribute("employeesOfTheDay", employeesOfTheDay);

            // 2. 주간 스케줄
            List<ScheduleVO> weeklySchedules = commonService.getWeeklySchedules();
            model.addAttribute("weeklySchedules", weeklySchedules);

            // 3. 최신 공지사항 3개
            List<NoticeVO> latestNotices = commonService.getLatestNotices();
            model.addAttribute("latestNotices", latestNotices);

            model.addAttribute("screenTitle", "대시보드");
            model.addAttribute("bodyPage", "/WEB-INF/views/common/common.jsp"); // 경로를 절대 경로로 수정
            return "index";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "대시보드 데이터를 불러오는 중 오류가 발생했습니다.");
            return "errorPage"; // 에러 페이지 뷰 이름
        }
    }
} // class 끝