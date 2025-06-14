package com.mobydick.hms.employee.controller;

import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public String employeeList(Model model) throws Exception {
        List<EmployeeVO> employeeList = employeeService.selectAllEmployees();
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("bodyPage", "employee/employee.jsp");
        return "index";
    }
}
