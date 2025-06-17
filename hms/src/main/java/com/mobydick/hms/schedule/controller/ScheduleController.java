package com.mobydick.hms.schedule.controller;

import com.mobydick.hms.schedule.service.ScheduleService;
import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public String schedulePage(HttpSession session, Map<String, Object> model) {
        model.put("screenTitle", "스케줄");
        model.put("bodyPage", "schedule/schedule.jsp");
        return "index";
    }

    @GetMapping("/display")
    @ResponseBody
    public List<Map<String, Object>> getScheduleList(HttpSession session) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        if (loginUser == null) return Collections.emptyList();

        String grade = loginUser.getEmplGrade();
        String emplId = loginUser.getEmplId();
        String deptId = loginUser.getEmplDept();

        List<ScheduleVO> scheduleList;
        switch (grade) {
            case "GR_01":
                scheduleList = scheduleService.getSchedulesForAdmin(deptId);
                break;
            case "GR_02":
                scheduleList = scheduleService.getSchedulesByTeamLeader(emplId);
                break;
            case "GR_03":
            default:
                scheduleList = scheduleService.getSchedulesByEmployee(emplId);
                break;
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (ScheduleVO s : scheduleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "GR_01".equals(grade)
                    ? s.getDeptName() + " - " + s.getScheShift()
                    : s.getScheShift());
            map.put("start", s.getScheDate().toString());
            result.add(map);
        }
        return result;
    }

    @PostMapping("/insert")
    @ResponseBody
    public String insertSchedules(@RequestBody List<ScheduleVO> list, HttpSession session) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        for (ScheduleVO vo : list) {
            vo.setScheId(UUID.randomUUID().toString());
            vo.setCreatedId(loginUser.getEmplId());
            vo.setUpdatedId(loginUser.getEmplId());
            scheduleService.insertSchedule(vo);
        }
        return "ok";
    }

    @GetMapping("/employees")
    @ResponseBody
    public List<Map<String, String>> getEmployees(HttpSession session, Model model) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String grade = loginUser.getEmplGrade();
        String deptId = loginUser.getEmplDept();

        //List<EmpVO> list = scheduleService.getEmployeesByGrade(grade, deptId);

        List<EmpVO> list = "GR_01".equals(grade)
                ? scheduleService.getAllEmployees()  // 관리자: 전체 직원
                : scheduleService.getEmployeesByDept(deptId);  // 팀장: 본인 부서 직원

        return list.stream()
                .map(emp -> Map.of(
                        "emplId", emp.getEmplId(),
                        "emplName", emp.getEmplName(),
                        "deptName", emp.getDeptName()
                ))
                .collect(Collectors.toList());
    }
}
