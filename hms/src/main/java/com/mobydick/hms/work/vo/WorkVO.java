package com.mobydick.hms.work.vo;

import lombok.Data;

import java.sql.Date;

// 할일 관리 저장용 객체
@Data
public class WorkVO {

    // 기본 업무 마스터 정보
    private String workMId;
    private String workMName;
    private String workMDept;
    private String workMContext;
    private String workMImpo;

    // 기본 업무 디테일 정보
    private String workDId;
    private String workDName;
    private String workDEmplId;
    private String workDDept;
    private String workDDate;
    private String workDContext;
    private String workDExtra;
    private String workDRoomId;
    private String workDImpo;
    private String workDStartName;
    private String workDStartPath;
    private Date workDStartTime;
    private String workDEndName;
    private String workDEndPath;
    private Date workDEndTime;
    private String emplName;
    private String roomName;

    // 생성/수정 정보
    private Date createdDate;      // 등록 일자
    private String createdId;      // 등록자 ID
    private Date updatedDate;      // 수정 일자
    private String updatedId;      // 수정자 ID

    // 코드 정보 (조인용)
    private String codeId;         // 코드
    private String codeName;       // 이름

    // 코드테이블 조인해온 코드명(임시)
    private String workDDeptN;         // 부서명
    private String workDImpoN;       // 중요도
}
