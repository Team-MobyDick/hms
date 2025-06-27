package com.mobydick.hms.schedule.vo;

import lombok.Data;

import java.sql.Date;

// 스케줄 상세 정보를 담는 VO
@Data
public class ScheduleDetailVO {

    private String scheId;           // 스케줄 ID
    private String scheDate;         // 스케줄 일자 (yyyy-MM-dd 형식 문자열)
    private String scheShiftName;    // 교대조 이름 (예: 주간, 야간)

    private String emplName;         // 직원 이름

    private String workdName;        // 업무 이름
    private String workdContext;     // 업무 내용 (상세 설명)
    private String workdImpoName;    // 업무 중요도 이름 (예: 높음, 보통, 낮음)
    private String workdStateName;   // 업무 상태 이름 (예: 완료, 미완료, 진행중)
    private String roomName;         // 배정된 객실 이름
    private String workDId;          // 업무 상세 ID
    private String workDate;         // 업무 일자 (yyyy-MM-dd 형식 문자열)

    private String emplDept;         // 직원 부서 이름 (예: 프론트, 시설관리 등)
    private String emplGrade;        // 직원 직급 이름 (예: 팀장, 일반 등)

}
