package com.mobydick.hms.employee.dao;

import com.mobydick.hms.employee.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeDAO {

    // 전체 직원 목록 조회
    List<EmployeeVO> selectAllEmployees();

    // 직원 등록
    void insertEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 ID 중복 확인
    int countEmployeeById(String emplId) throws Exception;
}
