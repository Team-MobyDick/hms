package com.mobydick.hms.schedule.vo;

import lombok.Data;

import java.sql.Date;

// 직원 정보를 담는 VO
@Data
public class EmpVO {

    private String emplId;       // 직원 ID
    private String emplName;     // 직원 이름
    private String emplPhone;    // 직원 연락처 (전화번호)
    private String deptName;     // 부서 이름 (예: 프론트, 하우스키핑 등)
    private String emplGrade;    // 직원 직급 (예: 팀장, 일반 등)

    private String photoName;    // 프로필 사진 파일명
    private String photoPath;    // 프로필 사진 저장 경로

    private String emplNotes;    // 비고 또는 메모

    private Date createdDate;    // 등록일자
    private String createdId;    // 등록자 ID

    private Date updatedDate;    // 수정일자
    private String updatedId;    // 수정자 ID

}
