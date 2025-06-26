package com.mobydick.hms.work.controller;

import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.service.WorkService;
import com.mobydick.hms.work.vo.WorkVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// 할일 관리 컨트롤러
@Slf4j
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

    // 목록 재로딩
    @GetMapping("/listData")
    @ResponseBody
    public Map<String, Object> listData(
            @RequestParam String date,
            @RequestParam(required = false) String mode,
            HttpSession session) throws Exception {

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        List<WorkVO> workMList = null;
        List<WorkVO> workDList = null;

        if ("ALL".equals(mode)) {
            workMList = workService.selectAllWorkM();
        } else {
            workDList = workService.selectWorkDByEmplByDate(loginUser.getEmplId(), date);
        }

        List<WorkVO> codeList = workService.getCodeIdAndName(); // 전체 코드 목록

        // 맵 (코드ID → 코드명) 생성
        Map<String, String> codeMap = codeList.stream()
                .collect(Collectors.toMap(WorkVO::getCodeId, WorkVO::getCodeName, (v1, v2) -> v1, LinkedHashMap::new));


        Map<String, Object> result = new HashMap<>();
        result.put("workMList", workMList);
        result.put("workDList", workDList);
        result.put("codeMap", codeMap);
        return result;
    }

    // 주 업무 등록
    @PostMapping("/addWorkM")
    public ResponseEntity<String> addWorkM(@RequestBody WorkVO vo, HttpSession session) {
        try {
            // 주 업무 ID 생성
            String workMId = "007" + String.format("%015d", new Random().nextInt(100000));
            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

            vo.setWorkMId(workMId);
            vo.setCreatedId(loginUserId);

            workService.insertWorkM(vo);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    // 주 업무 수정
    @PostMapping("/modifyWorkM")
    public ResponseEntity<String> modifyWorkM(@RequestBody WorkVO vo, HttpSession session) {
        try {

            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

            vo.setUpdatedId(loginUserId);

            workService.updateWorkM(vo);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    // 주 업무 삭제
    @PostMapping("/deleteWorkM")
    @ResponseBody
    public String deleteWorkM(@RequestParam String workMId, HttpSession session) throws Exception {

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        workService.deleteWorkM(workMId);
        return "success";
    }

    // 업무 배분 등록
    @PostMapping("/addWorkD")
    public ResponseEntity<String> addWorkD(@RequestBody WorkVO vo, HttpSession session) {
        try {
            // 상세 업무 ID 생성
            String workDId = "008" + String.format("%015d", new Random().nextInt(10000000));
            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

            vo.setWorkDId(workDId);
            vo.setCreatedId(loginUserId);

            workService.insertWorkD(vo);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    // 주 업무 클릭시 해당 일자 상세업무 리스트
    @GetMapping("/detailWorkList")
    @ResponseBody
    public List<WorkVO> getDetailWorkList(@RequestParam("workMId") String workMId, @RequestParam("date") String date) {
        return workService.getDetailWorkList(workMId, date);
    };

    // 상세업무 개별 버튼 클릭시 상세페이지 이동
    @GetMapping("/detailWorkD")
    public String getDetailWorkD(Model model, HttpSession session, @RequestParam("workDId") String workDId, @RequestParam("date") String date) throws Exception {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String grade = loginUser.getEmplGrade();
        List<WorkVO> emplList;
        List<WorkVO> allEmplList = workService.getEmpl();
        List<WorkVO> roomList = workService.getRoom();
        List<WorkVO> impoList = workService.getImpo();
        if ("GR_01".equals(grade)) {
            emplList = workService.getEmplbySche(date);
        } else if ("GR_02".equals(grade)) {
            emplList = workService.selectEmployeesByDept(loginUser.getEmplDept(),date);
        } else {
            emplList = Collections.emptyList();
        }

        WorkVO detailWorkD = workService.selectDetailWorkD(workDId);

        model.addAttribute("emplList", emplList);
        model.addAttribute("allEmplList", allEmplList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("impoList", impoList);
        model.addAttribute("detailWorkD", detailWorkD);
        model.addAttribute("bodyPage", "work/workDDetail.jsp");

        return "index";
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

    // 방 목록 (AJAX)
    @GetMapping("/roomTypes")
    @ResponseBody
    public List<WorkVO> getroom() {
        try {
            return workService.getRoom();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 직원 목록 (AJAX)
    @GetMapping("/emplTypes")
    @ResponseBody
    public List<WorkVO> getempl() {
        try {
            return workService.getEmpl();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 부서별 출근 직원 목록 (AJAX)
    @GetMapping("/emplListByDate")
    @ResponseBody
    public List<WorkVO> getemplByDate(@RequestParam("date") String date, HttpSession session) throws Exception {
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String grade = loginUser.getEmplGrade();
        List<WorkVO> emplList;
        if ("GR_01".equals(grade)) {
            emplList = workService.getEmplbySche(date);
        } else if ("GR_02".equals(grade)) {
            emplList = workService.selectEmployeesByDept(loginUser.getEmplDept(),date);
        } else {
            emplList = Collections.emptyList();
        }
        return emplList;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("C:/hms_uploads/work_photos");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path target = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return Map.of(
                "filePath", fileName,
                "uploadTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    // 상세 업무 수정
    @PostMapping("/modifyWorkD")
    @ResponseBody
    public Map<String, Object> modifyWorkD(@RequestBody WorkVO workVO, HttpSession session) throws Exception {

        String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

        workVO.setUpdatedId(loginUserId);

        // Service 호출해서 DB update
        workService.updateWorkD(workVO);

        return Map.of("success", true);
    }

    // 상세 업무 삭제
    @PostMapping("/deleteWorkD")
    @ResponseBody
    public String deleteWorkD(@RequestParam String workDId, HttpSession session) throws Exception {

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        workService.deleteWorkD(workDId);
        return "success";
    }
}
