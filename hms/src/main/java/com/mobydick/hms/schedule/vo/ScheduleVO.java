package com.mobydick.hms.schedule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

// 스케줄 정보를 담는 VO
@Data
public class ScheduleVO {

    private String scheId;      // 근무일정 ID

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date scheDate;      // 근무 날짜

    private String emplId;      // 직원 ID
    private String scheShift;   // 근무조
    private Date createdDate;   // 작성일자
    private String createdId;   // 작성자 ID
    private Date updatedDate;   // 수정일자
    private String updatedId;   // 수정자 ID

    // 세션 정보용
    private String emplGrade;
    private String emplDept;

    // 부서명 (GR_01 출력용)
    private String deptName;

    private String scheShiftName;

    // 작성자 이름 (JOIN을 통해 가져올 필드)
    private String emplName;

}
