package com.mobydick.hms;

import com.mobydick.hms.common.service.CommonService;
import com.mobydick.hms.common.vo.CommonVO; // CommonVO import
import com.mobydick.hms.login.vo.LoginVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/")
    public String main(Model model, HttpSession session) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        try {
            // 서비스에서 대시보드 데이터를 한번에 가져옴
            CommonVO dashboardData = commonService.getDashboardData();
            model.addAttribute("dashboardData", dashboardData); // CommonVO 객체를 모델에 추가

            model.addAttribute("screenTitle", "대시보드");
            model.addAttribute("bodyPage", "/WEB-INF/views/common/common.jsp");
            return "index";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "대시보드 데이터를 불러오는 중 오류가 발생했습니다.");
            return "errorPage";
        }
    }
}