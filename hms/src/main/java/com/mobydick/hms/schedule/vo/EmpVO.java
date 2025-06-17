package com.mobydick.hms.schedule.vo;

import lombok.Data;

import java.sql.Date;

// 스케줄 정보를 담는 VO
@Data
public class EmpVO {

    private String emplId;
    private String emplName;
    private String emplPhone;
    private String deptName;
    private String emplGrade;
    private String photoName;
    private String photoPath;
    private String emplNotes;
    private Date createdDate;
    private String createdId;
    private Date updatedDate;
    private String updatedId;

}
