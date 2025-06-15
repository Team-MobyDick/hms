package com.mobydick.hms.schedule.dao;

import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 스케줄 관리 DAO
@Mapper
public interface ScheduleDAO {

    // 전체 스케줄 조회
    List<ScheduleVO> selectAllschedule() throws DataAccessException;

}
