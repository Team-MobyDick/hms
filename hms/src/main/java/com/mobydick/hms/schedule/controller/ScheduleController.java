package com.mobydick.hms.schedule.controller;

import com.mobydick.hms.schedule.service.ScheduleService;
import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.employee.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 스케줄 화면 이동
     */
    @GetMapping("/list")
    public String schedulePage(HttpSession session, Map<String, Object> model) {
        model.put("screenTitle", "스케줄");
        model.put("bodyPage", "schedule/schedule.jsp");
        return "index";
    }

    /**
     * fullCalendar에 표시할 스케줄 데이터 조회
     * - 관리자: 부서별 스케줄
     * - 팀장: 부서 팀원 스케줄
     * - 일반: 본인 스케줄
     */
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

        // ISO 8601 → yyyyMMdd 포맷
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

    /**
     * 스케줄 등록
     * - JSON 배열로 넘어온 ScheduleVO 리스트 처리
     */
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

    /**
     * 등록 모달용 직원 목록 조회
     * - GR_01: 전체 직원
     * - GR_02: 본인 부서 직원
     */
    @GetMapping("/employees")
    @ResponseBody
    public List<Map<String, String>> getEmployees(HttpSession session, Model model) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String grade = loginUser.getEmplGrade();
        String deptId = loginUser.getEmplDept();

        List<EmpVO> list = "GR_01".equals(grade)
                ? scheduleService.getAllEmployees()
                : scheduleService.getEmployeesByDept(deptId);

        return list.stream()
                .map(emp -> Map.of(
                        "emplId", emp.getEmplId(),
                        "emplName", emp.getEmplName(),
                        "deptName", emp.getDeptName()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 날짜 클릭 시 하단 상세 스케줄 조회
     */
    @GetMapping("/detailByDate")
    @ResponseBody
    public List<ScheduleDetailVO> getScheduleByDate(@RequestParam String date, HttpSession session) {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yy/MM/dd");

        LocalDate parsedDate = LocalDate.parse(date, inputFormat);
        String formatted = parsedDate.format(dbFormat) + "%";

        return scheduleService.getScheduleByDate(
                formatted,
                loginUser.getEmplId(),
                loginUser.getEmplGrade()
        );
    }

    /**
     * 교대조 코드 → 이름 변환 (주간/야간)
     */
    private String convertShiftName(String code) {
        return switch (code) {
            case "SH_01" -> "주간";
            case "SH_02" -> "야간";
            default -> code;
        };
    }

    /**
     * 스케줄 삭제
     * - 관리자, 팀장만 가능
     */
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
