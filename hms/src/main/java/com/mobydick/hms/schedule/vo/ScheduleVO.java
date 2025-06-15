package com.mobydick.hms.schedule.vo;

import lombok.Data;

import java.sql.Date;

// 스케줄 정보를 담는 VO
@Data
public class ScheduleVO {

    private String scheId;
    private String scheDate;
    private String emplId;
    private String scheShift;
    private Date createdDate;
    private String createdId;
    private Date updatedDate;
    private String updatedId;

}
