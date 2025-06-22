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

    // 직원 조회 (정렬 기준 파라미터 처리)
    @Override
    public List<EmployeeVO> selectAllEmployees(String sortOrder) throws Exception { // <-- sortOrder 파라미터 받음
        return employeeDAO.selectAllEmployees(sortOrder); // <-- DAO로 sortOrder 전달
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
        employeeDAO.updateNoticeEmplIdToNull(emplId);
        employeeDAO.deleteSchedulesByEmplId(emplId);
        employeeDAO.deleteWorkDetailsByEmplId(emplId);
        employeeDAO.deleteEmployee(emplId);
    }

    // 직원 ID로 특정 직원 정보 조회
    @Override
    public EmployeeVO selectEmployeeById(String emplId) throws Exception {
        return employeeDAO.selectEmployeeById(emplId);
    }

    // 직원 사진 정보 업데이트 메서드 구현
    @Override
    public void updateEmployeePhoto(EmployeeVO employeeVO) throws Exception {
        employeeDAO.updateEmployeePhoto(employeeVO);
    }

    // 직원 퇴사 처리
    @Override
    public void retireEmployee(String emplId, String updatedId) throws Exception {
        employeeDAO.retireEmployee(emplId, updatedId);
    }
}