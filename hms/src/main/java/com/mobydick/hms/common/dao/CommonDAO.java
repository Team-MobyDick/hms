package com.mobydick.hms.common.dao;

import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.work.vo.WorkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonDAO {

    // 오늘의 사원
    List<EmployeeVO> selectRandomEmployees();

    // 모든 직원의 커피 당첨 상태를 'N'으로 초기화
    void resetCoffeeWinnerStatus();

    // 활성 상태의 모든 직원 조회 (커피 당첨자 선정을 위함)
    List<EmployeeVO> selectAllActiveEmployees();

    // 특정 직원의 커피 당첨 상태를 'Y'로 업데이트
    void updateCoffeeWinner(@Param("emplId") String emplId);

    // 주간 스케줄 조회 (SYSDATE 기준 현재 주의 스케줄)
    List<ScheduleVO> selectWeeklySchedules(@Param("emplId") String emplId);

    // 최신 공지사항 3개 조회
    List<NoticeVO> selectLatestNotices();

    // 오늘의 할 일 조회
    List<WorkVO> selectTodayWorks(@Param("emplId") String emplId);

}