package com.mobydick.hms.schedule.dao;

import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 스케줄 관리 DAO
@Mapper
public interface ScheduleDAO {

    // 전체 스케줄 조회
    List<ScheduleVO> getAllSchedules() throws DataAccessException;

    List<ScheduleVO> getSchedulesForAdmin(String deptId) throws DataAccessException;;
    List<ScheduleVO> getSchedulesByTeamLeader(String emplId) throws DataAccessException;;
    List<ScheduleVO> getSchedulesByEmployee(String emplId) throws DataAccessException;;
    void insertSchedule(ScheduleVO vo);
    List<EmpVO> getAllEmployees();
    List<EmpVO> getEmployeesByDept(String deptId);
}
