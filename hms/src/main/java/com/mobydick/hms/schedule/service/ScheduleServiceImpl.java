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

    @Override
    public List<ScheduleVO> getAllSchedules() throws Exception {
        return scheduleDAO.getAllSchedules();
    }

    @Override
    public List<ScheduleVO> getSchedulesForAdmin(String deptId) {
        return scheduleDAO.getSchedulesForAdmin(deptId);
    }

    @Override
    public List<ScheduleVO> getSchedulesByTeamLeader(String emplId) {
        return scheduleDAO.getSchedulesByTeamLeader(emplId);
    }

    @Override
    public List<ScheduleVO> getSchedulesByEmployee(String emplId) {
        return scheduleDAO.getSchedulesByEmployee(emplId);
    }

    @Override
    public void insertSchedule(ScheduleVO vo) {
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 하이픈 제거 (32자)
        vo.setScheId(uuid.substring(0, 20)); // 앞에서 20자만 사용
        scheduleDAO.insertSchedule(vo);
    }

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

    @Override
    public List<EmpVO> getAllEmployees() {
        return scheduleDAO.getAllEmployees();
    }

    @Override
    public List<EmpVO> getEmployeesByDept(String deptId) {
        return scheduleDAO.getEmployeesByDept(deptId);
    }

    @Override
    public List<ScheduleDetailVO> getScheduleByDate(String date, String emplId) {
        return scheduleDAO.getScheduleByDate(date, emplId);
    }


    @Override
    public List<ScheduleVO> getSchedulesForAdminBetween(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesForAdminBetween(map);
    }

    @Override
    public List<ScheduleVO> getSchedulesByTeamLeaderBetween(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesByTeamLeaderBetween(map);
    }

    @Override
    public List<ScheduleVO> getSchedulesByEmployeeBetween(String emplId, String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("emplId", emplId);
        map.put("start", startDate);
        map.put("end", endDate);
        return scheduleDAO.getSchedulesByEmployeeBetween(map);
    }

    @Override
    public void deleteSchedule(String scheId, String userGrade) {
        if ("GR_01".equals(userGrade) || "GR_02".equals(userGrade)) {
            scheduleDAO.deleteSchedule(scheId);
        } else {
            throw new SecurityException("삭제할 권한이 없습니다.");
        }
    }
}
