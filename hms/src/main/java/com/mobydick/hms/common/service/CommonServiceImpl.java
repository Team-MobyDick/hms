// CommonServiceImpl.java
package com.mobydick.hms.common.service;

import com.mobydick.hms.common.dao.CommonDAO;
import com.mobydick.hms.common.vo.CommonVO;
import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public CommonVO getDashboardData() throws Exception {
        // 대시보드 데이터를 담을 CommonVO 객체 생성
        CommonVO dashboardData = new CommonVO();

        // 1. 오늘의 사원 정보 조회 후 CommonVO에 설정
        List<EmployeeVO> employees = commonDAO.selectRandomEmployeesGR03();
        dashboardData.setEmployeesOfTheDay(employees);

        // 2. 주간 스케줄 정보 조회 후 CommonVO에 설정
        List<ScheduleVO> schedules = commonDAO.selectWeeklySchedules();
        dashboardData.setWeeklySchedules(schedules);

        // 3. 최신 공지사항 정보 조회 후 CommonVO에 설정
        List<NoticeVO> notices = commonDAO.selectLatestNotices();
        dashboardData.setLatestNotices(notices);

        return dashboardData;
    }
}