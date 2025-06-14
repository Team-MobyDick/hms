package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.vo.EmployeeVO;
import java.util.List;

public interface EmployeeService {
    List<EmployeeVO> selectAllEmployees() throws Exception;
}
