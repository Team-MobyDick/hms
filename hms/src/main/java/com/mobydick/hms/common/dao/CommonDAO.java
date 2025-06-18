package com.mobydick.hms.common.dao;

import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonDAO {

    // 오늘의 사원 (직책 GR_03인 직원 중 3명 랜덤 조회)
    List<EmployeeVO> selectRandomEmployeesGR03();

    // 주간 스케줄 조회 (SYSDATE 기준 현재 주의 스케줄)
    List<ScheduleVO> selectWeeklySchedules();

    // 최신 공지사항 3개 조회
    List<NoticeVO> selectLatestNotices();
}