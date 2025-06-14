package com.mobydick.hms.employee.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Date;

// 직원관리 데이터 저장용 객체
@Getter
@Setter
@NoArgsConstructor
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