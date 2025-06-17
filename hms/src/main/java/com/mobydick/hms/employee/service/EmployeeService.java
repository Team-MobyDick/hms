package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.vo.EmployeeVO;
import java.util.List;

public interface EmployeeService {

    // 직원 조회
    List<EmployeeVO> selectAllEmployees() throws Exception;

    // 직원 등록
    void insertEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 정보 수정
    void updateEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 삭제
    void deleteEmployee(String emplId) throws Exception;

}
