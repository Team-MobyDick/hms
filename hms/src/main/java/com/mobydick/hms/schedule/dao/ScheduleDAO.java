package com.mobydick.hms.schedule.dao;

import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

// 스케줄 관리 DAO
@Mapper
public interface ScheduleDAO {

    // 전체 스케줄 조회 (관리자용)
    List<ScheduleVO> getAllSchedules() throws DataAccessException;

    // 관리자(GR_01): 부서 전체 스케줄 조회
    List<ScheduleVO> getSchedulesForAdmin(String deptId) throws DataAccessException;

    // 팀장(GR_02): 팀 소속 직원들 스케줄
    List<ScheduleVO> getSchedulesByTeamLeader(String emplId) throws DataAccessException;

    // 일반 직원(GR_03): 본인 스케줄 조회
    List<ScheduleVO> getSchedulesByEmployee(String emplId) throws DataAccessException;

    // 스케줄 등록
    void insertSchedule(ScheduleVO vo);

    // 직원 전체 목록 (관리자용)
    List<EmpVO> getAllEmployees();

    // 부서별 직원 목록 (팀장용)
    List<EmpVO> getEmployeesByDept(String deptId);

    // 날짜 기준 스케줄 상세 목록 (FullCalendar용 상세 조회)
    List<ScheduleDetailVO> getScheduleByDate(
            @Param("date") String date,
            @Param("emplId") String emplId,
            @Param("emplGrade") String emplGrade
    ) throws DataAccessException;

    // 관리자: 날짜 범위 스케줄 조회
    List<ScheduleVO> getSchedulesForAdminBetween(Map<String, Object> map);

    // 팀장: 날짜 범위 팀원 스케줄 조회
    List<ScheduleVO> getSchedulesByTeamLeaderBetween(Map<String, Object> map);

    // 직원: 날짜 범위 본인 스케줄 조회
    List<ScheduleVO> getSchedulesByEmployeeBetween(Map<String, Object> map);

    // 스케줄 삭제
    void deleteSchedule(String scheId) throws DataAccessException;

}
