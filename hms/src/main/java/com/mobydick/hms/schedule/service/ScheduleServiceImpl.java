package com.mobydick.hms.schedule.service;

import com.mobydick.hms.schedule.dao.ScheduleDAO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 스케줄 관리 서비스 구현체
@Service
@Transactional
public class ScheduleServiceImpl implements  ScheduleService {

    @Autowired
    ScheduleDAO scheduleDAO;

    @Override
    public List<ScheduleVO> selectAllschedule() throws Exception {
        return scheduleDAO.selectAllschedule();
    }
}
