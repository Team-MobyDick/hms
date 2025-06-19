package com.mobydick.hms.work.controller;

import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.service.WorkService;
import com.mobydick.hms.work.vo.WorkVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 할일 관리 컨트롤러
@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/list")
    public String work(Model model, HttpSession session) throws Exception {

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = now.format(formatter);
        String grade = loginUser.getEmplGrade();
        String emplId = loginUser.getEmplId();

        List<WorkVO> workMList;
        List<WorkVO> workDList;

        if ("GR_01".equals(grade)||"GR_02".equals(grade)) {
            workMList = workService.selectAllWorkM();
            workDList = null;
        } else {
            workMList = null;
            workDList = workService.selectWorkDByEmplByDate(emplId, date);
        };

        List<WorkVO> codeList = workService.getCodeIdAndName(); // 전체 코드 목록

        // 맵 (코드ID → 코드명) 생성
        Map<String, String> codeMap = codeList.stream()
                .collect(Collectors.toMap(WorkVO::getCodeId, WorkVO::getCodeName, (v1, v2) -> v1, LinkedHashMap::new));

        model.addAttribute("codeMap", codeMap);
        model.addAttribute("workMList", workMList);
        model.addAttribute("workDList", workDList);
        model.addAttribute("bodyPage", "work/work.jsp");

        return "index";
    }

    // 주 업무 클릭시 해당 일자 상세업무 리스트
    @GetMapping("/detailWorkList")
    @ResponseBody
    public List<WorkVO> getDetailWorkList(@RequestParam("workMId") String workMId, @RequestParam("date") String date) {
        return workService.getDetailWorkList(workMId, date);
    };

    // 부서 목록 (AJAX)
    @GetMapping("/deptTypes")
    @ResponseBody
    public List<WorkVO> getDeptTypes() {
        try {
            return workService.getDept();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 중요도 목록 (AJAX)
    @GetMapping("/impoTypes")
    @ResponseBody
    public List<WorkVO> getImpoTypes() {
        try {
            return workService.getImpo();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
