package com.mobydick.hms.schedule.service;

import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;

import java.util.List;

// 스케줄 관리 서비스 인터페이스
public interface ScheduleService {

    /** 전체 스케줄 목록 조회 (관리자용) */
    List<ScheduleVO> getAllSchedules() throws Exception;

    /** 관리자(GR_01): 본인 부서 전체 직원의 스케줄 조회 */
    List<ScheduleVO> getSchedulesForAdmin(String deptId);

    /** 팀장(GR_02): 본인 부서 팀원들의 스케줄 조회 */
    List<ScheduleVO> getSchedulesByTeamLeader(String emplId);

    /** 일반 직원(GR_03): 본인 스케줄 조회 */
    List<ScheduleVO> getSchedulesByEmployee(String emplId);

    /** 스케줄 등록 */
    void insertSchedule(ScheduleVO vo);

    /**
     * 등급에 따라 직원 목록 가져오기
     * @param grade 로그인한 사용자 등급 (GR_01, GR_02 등)
     * @param deptId 부서 ID (팀장일 경우 본인 부서)
     */
    List<EmpVO> getEmployeesByGrade(String grade, String deptId);

    /** 전체 직원 목록 조회 (관리자용) */
    List<EmpVO> getAllEmployees();

    /** 부서별 직원 목록 조회 (팀장용) */
    List<EmpVO> getEmployeesByDept(String deptId);

    /**
     * 특정 날짜에 해당하는 스케줄 상세 목록 조회
     * @param date 조회할 날짜 (형식: yy/MM/dd%)
     * @param emplId 로그인한 사용자 ID
     * @param emplGrade 로그인한 사용자 등급
     */
    List<ScheduleDetailVO> getScheduleByDate(String date, String emplId, String emplGrade);

    /** 관리자: 날짜 범위 내 전체 스케줄 조회 */
    List<ScheduleVO> getSchedulesForAdminBetween(String startDate, String endDate);

    /** 팀장: 날짜 범위 내 본인 부서 팀원 스케줄 조회 */
    List<ScheduleVO> getSchedulesByTeamLeaderBetween(String startDate, String endDate);

    /** 직원: 날짜 범위 내 본인 스케줄 조회 */
    List<ScheduleVO> getSchedulesByEmployeeBetween(String emplId, String startDate, String endDate);

    /**
     * 스케줄 삭제
     * @param scheId 삭제할 스케줄 ID
     * @param userGrade 삭제를 시도하는 사용자의 등급
     */
    void deleteSchedule(String scheId, String userGrade);
}
