package com.mobydick.hms.employee.controller;

import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 직원관리 컨트롤러
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // 직원 목록 조회
    @GetMapping("/employee/list")
    public String listEmployees(Model model) {
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee/employee";
    }

    // 직원 상세 조회
    @GetMapping("/employee/{emplId}")
    public String employeeDetail(@PathVariable String emplId, Model model) {
        EmployeeVO employee = employeeService.getEmployeeById(emplId);
        model.addAttribute("employee", employee);
        return "employee/employeeDetail";
    }

    // 직원 등록 폼
    @GetMapping("/employee/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeVO());
        return "employee/employeeForm";
    }

    // 신규 직원 저장
    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute EmployeeVO employee) {
        employee.setCreatedId("admin"); // 실제 로그인 사용자 ID로 변경 권장
        employee.setUpdatedId("admin");
        employeeService.insertEmployee(employee);
        return "redirect:/employee/list";
    }

    // 직원 정보 수정
    @PostMapping("/employee/update")
    public String updateEmployee(EmployeeVO employee) {

        if(employee == null) {
            return "redirect:/employee/list";
        }

        employee.setUpdatedId("admin");
        employeeService.updateEmployee(employee);
        return "redirect:/employee/list";
    }

    // 직원 정보 수정 (기존 직원 정보에서 수정)
    @GetMapping("/employee/edit/{emplId}")
    public String editEmployeeForm(@PathVariable String emplId, Model model) {
        EmployeeVO employee = employeeService.getEmployeeById(emplId);
        if (employee == null) {
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        return "employee/employeeForm";
    }

}