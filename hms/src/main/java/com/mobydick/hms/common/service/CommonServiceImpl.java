package com.mobydick.hms.common.service;

import com.mobydick.hms.common.dao.CommonDAO;
import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 서비스 인터페이스 구현 객체
@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public List<EmployeeVO> getEmployeesOfTheDay() throws Exception {
        return commonDAO.selectRandomEmployeesGR03();
    }

    @Override
    public List<ScheduleVO> getWeeklySchedules() throws Exception {
        return commonDAO.selectWeeklySchedules();
    }

    @Override
    public List<NoticeVO> getLatestNotices() throws Exception {
        return commonDAO.selectLatestNotices();
    }
}
