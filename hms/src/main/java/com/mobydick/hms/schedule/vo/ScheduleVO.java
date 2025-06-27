package com.mobydick.hms.schedule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

// 스케줄 정보를 담는 VO
@Data
public class ScheduleVO {

    private String scheId;      // 근무 일정 ID

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date scheDate;      // 근무 날짜

    private String emplId;      // 직원 ID
    private String scheShift;   // 교대조 코드 (예: SH_01, SH_02)
    private Date createdDate;   // 생성일자
    private String createdId;   // 생성자 ID
    private Date updatedDate;   // 수정일자
    private String updatedId;   // 수정자 ID
    private String dupCheck;    // 중복 등록 방지를 위한 체크값

    // 세션 정보용
    private String emplGrade;   // 직원 직급 코드 (예: GR_02)
    private String emplDept;    // 직원 부서 코드 (예: DP_03)

    // 부서명 (예: 프론트, 하우스키핑 등)
    private String deptName;

    // 교대조 이름 (예: 주간, 야간)
    private String scheShiftName;

    // 직원 이름 (JOIN 결과로 받아오는 값)
    private String emplName;

}
