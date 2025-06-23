package com.mobydick.hms.schedule.dao;

import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

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

    List<ScheduleDetailVO> getScheduleByDate(String date);

    List<ScheduleVO> getSchedulesForAdminBetween(Map<String, Object> map);

    List<ScheduleVO> getSchedulesByTeamLeaderBetween(Map<String, Object> map);

    List<ScheduleVO> getSchedulesByEmployeeBetween(Map<String, Object> map);

    void deleteSchedule(String scheId) throws DataAccessException;

}
