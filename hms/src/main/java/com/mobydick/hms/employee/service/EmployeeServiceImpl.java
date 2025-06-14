package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.dao.EmployeeDAO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 직원관리 서비스 구현 객체
@Service
@Transactional
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    EmployeeDAO employeeDAO;

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return employeeDAO.selectAllEmployees();
    }

    @Override
    public void insertEmployee(EmployeeVO employee) {
        employeeDAO.insertEmployee(employee);
    }

    @Override
    public EmployeeVO getEmployeeById(String emplId) {
        return employeeDAO.selectEmployeeById(emplId);
    }

    @Override
    public void updateEmployee(EmployeeVO employee) {
        employeeDAO.updateEmployee(employee);
    }

}