package com.mobydick.hms.schedule.service;

import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;

import java.util.List;

// 스케줄 관리 서비스 인터페이스
public interface ScheduleService {

    // 전체 스케줄 조회
    List<ScheduleVO> getAllSchedules() throws Exception;

    List<ScheduleVO> getSchedulesForAdmin(String deptId);     // GR_01
    List<ScheduleVO> getSchedulesByTeamLeader(String emplId); // GR_02
    List<ScheduleVO> getSchedulesByEmployee(String emplId);   // GR_03
    void insertSchedule(ScheduleVO vo);
    List<EmpVO> getEmployeesByGrade(String grade, String deptId);

    List<EmpVO> getAllEmployees();
    List<EmpVO> getEmployeesByDept(String deptId);
}
