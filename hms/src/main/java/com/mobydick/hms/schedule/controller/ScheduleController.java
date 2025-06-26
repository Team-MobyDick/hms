package com.mobydick.hms.schedule.controller;

import com.mobydick.hms.schedule.service.ScheduleService;
import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public List<Map<String, Object>> getScheduleList(
            @RequestParam String start,
            @RequestParam String end,
            HttpSession session
    ) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        if (loginUser == null) return Collections.emptyList();

        String grade = loginUser.getEmplGrade();
        String emplId = loginUser.getEmplId();
        String deptId = loginUser.getEmplDept();

        // 날짜 파라미터 포맷 자르기 (ISO → yyyyMMdd)
        String startDate = start.substring(0, 10).replace("-", "");
        String endDate = end.substring(0, 10).replace("-", "");

        List<ScheduleVO> scheduleList = switch (grade) {
            case "GR_01" -> scheduleService.getSchedulesForAdminBetween(startDate, endDate);
            case "GR_02" -> scheduleService.getSchedulesByTeamLeaderBetween(startDate, endDate);
            default       -> scheduleService.getSchedulesByEmployeeBetween(emplId, startDate, endDate);
        };

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return scheduleList.stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "GR_01".equals(grade)
                    ? s.getDeptName() + " - " + convertShiftName(s.getScheShift())
                    : convertShiftName(s.getScheShift()));
            map.put("start", sdf.format(s.getScheDate()));
            return map;
        }).toList();
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

    @GetMapping("/detailByDate")
    @ResponseBody
    public List<ScheduleDetailVO> getScheduleByDate(@RequestParam String date, HttpSession session) {

        // 로그인 유저 정보
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        if (loginUser.getEmplGrade().equals("GR_01")) {
            loginUser.setEmplId("");
        }

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yy/MM/dd");

        LocalDate parsedDate = LocalDate.parse(date, inputFormat);
        String formatted = parsedDate.format(dbFormat) + "%";

        return scheduleService.getScheduleByDate(formatted, loginUser.getEmplId());
    }

    private String convertShiftName(String code) {
        return switch (code) {
            case "SH_01" -> "주간";
            case "SH_02" -> "야간";
            default -> code;
        };
    }

    @DeleteMapping("/delete/{scheId}")
    @ResponseBody
    public String deleteSchedule(@PathVariable String scheId, HttpSession session) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "error: 로그인 정보가 없습니다.";
        }

        String userGrade = loginUser.getEmplGrade();

        try {
            scheduleService.deleteSchedule(scheId, userGrade);
            return "ok";
        } catch (SecurityException e) {
            return "error: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "error: " + e.getMessage();
        } catch (Exception e) {
            return "error: 스케줄 삭제 중 오류가 발생했습니다. " + e.getMessage();
        }
    }

}
