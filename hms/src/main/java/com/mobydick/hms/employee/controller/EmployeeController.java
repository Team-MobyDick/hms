package com.mobydick.hms.employee.controller;

import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.login.vo.LoginVO; // Assuming LoginVO exists
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 직원 목록 조회
    @GetMapping("/list")
    public String employeeList(Model model, HttpSession session) throws Exception {
        List<EmployeeVO> employeeList = employeeService.selectAllEmployees();
        model.addAttribute("employeeList", employeeList);

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        if (loginUser != null) {
            model.addAttribute("userRole", loginUser.getEmplGrade());
        } else {
            model.addAttribute("userRole", ""); // Default or anonymous role
        }
        model.addAttribute("bodyPage", "employee/employee.jsp");
        return "index";
    }

    // 직원 등록 처리
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerEmployee(@ModelAttribute EmployeeVO employeeVO, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }
            employeeVO.setCreatedId(loginUser.getEmplId());

            if (employeeVO.getEmplId() == null || employeeVO.getEmplId().trim().isEmpty() ||
                    employeeVO.getEmplName() == null || employeeVO.getEmplName().trim().isEmpty() ||
                    employeeVO.getEmplDept() == null || employeeVO.getEmplDept().trim().isEmpty() ||
                    employeeVO.getEmplGrade() == null || employeeVO.getEmplGrade().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("필수 입력 항목(ID, 이름, 부서, 직책)을 모두 채워주세요.");
            }

            if (employeeVO.getPhotoName() == null || employeeVO.getPhotoName().isEmpty()) {
                employeeVO.setPhotoName(null);
            }
            if (employeeVO.getPhotoPath() == null || employeeVO.getPhotoPath().isEmpty()) {
                employeeVO.setPhotoPath(null);
            }

            employeeService.insertEmployee(employeeVO);
            return ResponseEntity.ok("직원이 성공적으로 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 직원 정보 수정 처리
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateEmployee(@ModelAttribute EmployeeVO employeeVO, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            String userRole = loginUser.getEmplGrade();
            String userDept = loginUser.getEmplDept(); // 로그인한 사용자의 부서 정보

            // GR_01 (총지배인) 또는 GR_02 (팀장)만 수정 가능
            if (!"GR_01".equals(userRole) && !"GR_02".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("직원 정보를 수정할 권한이 없습니다.");
            }

            // 수정 대상 직원의 현재 정보 조회
            EmployeeVO targetEmployee = employeeService.selectEmployeeById(employeeVO.getEmplId());
            if (targetEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정하려는 직원을 찾을 수 없습니다.");
            }

            // 팀장(GR_02)인 경우, 같은 부서의 직원만 수정 가능하도록 제한
            if ("GR_02".equals(userRole)) {
                if (!userDept.equals(targetEmployee.getEmplDept())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀장은 같은 부서의 직원 정보만 수정할 수 있습니다.");
                }
            }


            // 필수 입력 항목 검증 (ID는 수정 불가하므로 제외)
            if (employeeVO.getEmplName() == null || employeeVO.getEmplName().trim().isEmpty() ||
                    employeeVO.getEmplDept() == null || employeeVO.getEmplDept().trim().isEmpty() ||
                    employeeVO.getEmplGrade() == null || employeeVO.getEmplGrade().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("필수 입력 항목(이름, 부서, 직책)을 모두 채워주세요.");
            }

            employeeVO.setUpdatedId(loginUser.getEmplId()); // 수정자 ID 설정
            employeeService.updateEmployee(employeeVO); // 서비스 호출
            return ResponseEntity.ok("직원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 직원 삭제 처리
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteEmployee(@RequestParam("emplId") String emplId, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            // GR_01 (총지배인)만 삭제 가능
            String userRole = loginUser.getEmplGrade();
            if (!"GR_01".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("직원 정보를 삭제할 권한이 없습니다.");
            }

            if (emplId == null || emplId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("삭제할 직원 ID가 필요합니다.");
            }

            // 서비스 호출
            employeeService.deleteEmployee(emplId);
            return ResponseEntity.ok("직원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}