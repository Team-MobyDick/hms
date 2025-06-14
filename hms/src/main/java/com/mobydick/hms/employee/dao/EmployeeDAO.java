package com.mobydick.hms.employee.dao;

import com.mobydick.hms.employee.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 직원관리 매퍼
@Mapper
public interface EmployeeDAO {

    List<EmployeeVO> selectAllEmployees();

    EmployeeVO selectEmployeeById(String emplId);

    void insertEmployee(EmployeeVO employee);

    void updateEmployee(EmployeeVO employee);

}