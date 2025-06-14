package com.mobydick.hms.employee.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeVO {
    private String emplId;
    private String emplName;
    private String emplPhone;
    private String emplDept;
    private String emplGrade;
    private String photoName;
    private String photoPath;
    private String emplNotes;
    private Date createdDate;
    private String createdId;
    private Date updatedDate;
    private String updatedId;
}
