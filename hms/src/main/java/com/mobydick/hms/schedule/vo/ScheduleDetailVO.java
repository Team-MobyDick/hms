package com.mobydick.hms.schedule.vo;

import lombok.Data;

import java.sql.Date;

// 스케줄 정보를 담는 VO
@Data
public class ScheduleDetailVO {

    private String scheId;
    private String scheDate;
    private String scheShiftName;
    private String emplName;
    private String workdName;
    private String workdContext;
    private String workdImpoName;
    private String workdStateName;
    private String roomName;
    private String workDId;
    private String workDate;

}
