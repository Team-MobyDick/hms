package com.mobydick.hms.schedule.service;

import com.mobydick.hms.schedule.vo.ScheduleVO;

import java.util.List;

// 스케줄 관리 서비스 인터페이스
public interface ScheduleService {

    // 전체 스케줄 조회
    List<ScheduleVO> selectAllschedule() throws Exception;

}
