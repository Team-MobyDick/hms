package com.mobydick.hms.schedule.controller;

import com.mobydick.hms.schedule.service.ScheduleService;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


// 스케줄 관리 컨트롤러
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 스케줄 페이지
    @GetMapping("/list")
    public String room(Model model, @RequestParam(defaultValue = "1") int page) throws Exception {

        List<ScheduleVO> calendar = null;

        try {
            
            calendar = scheduleService.selectAllschedule();

            model.addAttribute("screenTitle", "스케줄");
            model.addAttribute("calendarList", calendar);
            model.addAttribute("bodyPage", "schedule/schedule.jsp");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "index";
    }
    

}
