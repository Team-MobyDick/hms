package com.mobydick.hms.common.vo;

import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import lombok.Data;

import java.util.List;

// 대시보드 데이터 저장 객체
@Data
public class CommonVO {
    private List<EmployeeVO> employeesOfTheDay;
    private List<ScheduleVO> weeklySchedules;
    private List<NoticeVO> latestNotices;
}