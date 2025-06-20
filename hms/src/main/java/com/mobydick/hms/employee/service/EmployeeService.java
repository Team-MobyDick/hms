package com.mobydick.hms.employee.service;

import com.mobydick.hms.employee.vo.EmployeeVO;
import java.util.List;

public interface EmployeeService {

    // 직원 조회 (정렬 기준 파라미터 추가)
    List<EmployeeVO> selectAllEmployees(String sortOrder) throws Exception; // <-- sortOrder 추가

    // 직원 등록
    void insertEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 정보 수정
    void updateEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 삭제
    void deleteEmployee(String emplId) throws Exception;

    // 직원 ID로 특정 직원 정보 조회
    EmployeeVO selectEmployeeById(String emplId) throws Exception;

    // 직원 사진 정보 업데이트 메서드 추가
    void updateEmployeePhoto(EmployeeVO employeeVO) throws Exception;
}