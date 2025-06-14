package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.vo.EmployeeVO;

import java.util.List;

// 직원관리 서비스 인터페이스
public interface EmployeeService {

    // 직원 전체 목록 조회
    List<EmployeeVO> getAllEmployees();

    // 직원 등록
    void insertEmployee(EmployeeVO employee);

    // 직원 상세 조회
    EmployeeVO getEmployeeById(String emplId);

    // 직원 정보 수정
    void updateEmployee(EmployeeVO employee);

}