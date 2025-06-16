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
}