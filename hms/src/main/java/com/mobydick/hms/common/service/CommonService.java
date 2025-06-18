package com.mobydick.hms.common.service;

import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;

import java.util.List;

public interface CommonService {

    List<EmployeeVO> getEmployeesOfTheDay() throws Exception;

    List<ScheduleVO> getWeeklySchedules() throws Exception;

    List<NoticeVO> getLatestNotices() throws Exception;

}