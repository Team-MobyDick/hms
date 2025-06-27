package com.mobydick.hms.schedule.service;

import com.mobydick.hms.schedule.dao.ScheduleDAO;
import com.mobydick.hms.schedule.vo.EmpVO;
import com.mobydick.hms.schedule.vo.ScheduleDetailVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

// 스케줄 관리 서비스 구현체
@Service
@Transactional
public class ScheduleServiceImpl implements  ScheduleService {

    @Autowired
    ScheduleDAO scheduleDAO;

    /**
     * 전체 스케줄 목록 조회 (관리자용)
     */
    @Override
    public List<ScheduleVO> getAllSchedules() throws Exception {
        return scheduleDAO.getAllSchedules();
    }

    /**
     * 관리자(GR_01): 부서 전체 직원의 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesForAdmin(String deptId) {
        return scheduleDAO.getSchedulesForAdmin(deptId);
    }

    /**
     * 팀장(GR_02): 본인 부서 소속 직원들의 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesByTeamLeader(String emplId) {
        return scheduleDAO.getSchedulesByTeamLeader(emplId);
    }

    /**
     * 일반 직원(GR_03): 본인 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesByEmployee(String emplId) {
        return scheduleDAO.getSchedulesByEmployee(emplId);
    }

    /**
     * 스케줄 등록
     * UUID 기반으로 SCHE_ID를 생성하여 저장
     */
    @Override
    public void insertSchedule(ScheduleVO vo) {
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 하이픈 제거
        vo.setScheId(uuid.substring(0, 20)); // 앞 20자만 사용
        scheduleDAO.insertSchedule(vo);
    }

    /**
     * 사용자 등급에 따라 직원 목록 반환
     * - GR_01: 전체 직원
     * - GR_02: 본인 부서 직원
     * - 그 외: 빈 목록 반환
     */
    @Override
    public List<EmpVO> getEmployeesByGrade(String grade, String deptId) {
        if ("GR_01".equals(grade)) {
            return scheduleDAO.getAllEmployees();
        } else if ("GR_02".equals(grade)) {
            return scheduleDAO.getEmployeesByDept(deptId);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 전체 직원 목록 조회
     */
    @Override
    public List<EmpVO> getAllEmployees() {
        return scheduleDAO.getAllEmployees();
    }

    /**
     * 부서별 직원 목록 조회
     */
    @Override
    public List<EmpVO> getEmployeesByDept(String deptId) {
        return scheduleDAO.getEmployeesByDept(deptId);
    }

    /**
     * 특정 날짜 기준의 스케줄 상세 목록 조회
     * - 사용자의 등급에 따라 범위 제한
     */
    @Override
    public List<ScheduleDetailVO> getScheduleByDate(String date, String emplId, String emplGrade) {
        return scheduleDAO.getScheduleByDate(date, emplId, emplGrade);
    }

    /**
     * 관리자: 날짜 범위 내 전체 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesForAdminBetween(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesForAdminBetween(map);
    }

    /**
     * 팀장: 날짜 범위 내 본인 부서 팀원 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesByTeamLeaderBetween(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesByTeamLeaderBetween(map);
    }

    /**
     * 직원: 날짜 범위 내 본인 스케줄 조회
     */
    @Override
    public List<ScheduleVO> getSchedulesByEmployeeBetween(String emplId, String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("emplId", emplId);
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesByEmployeeBetween(map);
    }

    /**
     * 스케줄 삭제
     * - 관리자(GR_01) 또는 팀장(GR_02)만 삭제 가능
     */
    @Override
    public void deleteSchedule(String scheId, String userGrade) {
        if ("GR_01".equals(userGrade) || "GR_02".equals(userGrade)) {
            scheduleDAO.deleteSchedule(scheId);
        } else {
            throw new SecurityException("삭제할 권한이 없습니다.");
        }
    }
}
