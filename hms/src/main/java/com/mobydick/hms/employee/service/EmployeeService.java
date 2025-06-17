package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.vo.EmployeeVO;
import java.util.List;

public interface EmployeeService {

    List<EmployeeVO> selectAllEmployees() throws Exception;

    // 직원 등록
    void insertEmployee(EmployeeVO employeeVO) throws Exception;



}
