package com.mobydick.hms.employee.dao;

import com.mobydick.hms.employee.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeDAO {
    List<EmployeeVO> selectAllEmployees();
}
