package com.mobydick.hms.employee.dao;

import com.mobydick.hms.employee.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // @Param 어노테이션 임포트

import java.util.List;

@Mapper
public interface EmployeeDAO {

    // 직원 목록 조회 (정렬 기준 파라미터 추가)
    List<EmployeeVO> selectAllEmployees(@Param("sortOrder") String sortOrder); // <-- @Param 어노테이션 사용

    // 직원 ID 중복 확인
    int countEmployeeById(String emplId) throws Exception;

    // 직원 등록
    void insertEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 정보 수정
    void updateEmployee(EmployeeVO employeeVO) throws Exception;

    // 직원 삭제
    void deleteEmployee(String emplId) throws Exception;

    // 직원 ID로 특정 직원 정보 조회
    EmployeeVO selectEmployeeById(String emplId) throws Exception;

    // 직원 사진 정보만 업데이트하는 메서드 추가
    void updateEmployeePhoto(EmployeeVO employeeVO) throws Exception;
}