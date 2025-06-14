package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.dao.EmployeeDAO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public List<EmployeeVO> selectAllEmployees() throws Exception {
        return employeeDAO.selectAllEmployees();
    }
}
