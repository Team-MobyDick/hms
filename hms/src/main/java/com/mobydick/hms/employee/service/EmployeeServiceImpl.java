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

    // 직원 조회
    @Override
    public List<EmployeeVO> selectAllEmployees() throws Exception {
        return employeeDAO.selectAllEmployees();
    }

    // 직원 등록
    @Override
    public void insertEmployee(EmployeeVO employeeVO) throws Exception {

        if (employeeDAO.countEmployeeById(employeeVO.getEmplId()) > 0) {
            throw new IllegalArgumentException("이미 존재하는 직원 ID입니다.");
        }

        employeeDAO.insertEmployee(employeeVO);
    }

    // 직원 정보 수정
    @Override
    public void updateEmployee(EmployeeVO employeeVO) throws Exception {
        employeeDAO.updateEmployee(employeeVO);
    }

    // 직원 삭제
    @Override
    public void deleteEmployee(String emplId) throws Exception {
        employeeDAO.deleteEmployee(emplId);
    }

}
