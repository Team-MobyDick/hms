package com.mobydick.hms.login.vo;

import lombok.Data;

import java.sql.Date;

// 로그인 시 사용자 정보를 담는 VO
@Data
public class LoginVO {

    private String emplId;     // 직원 ID
    private String emplName;   // 직원명
    private String emplPhone;  // 연락처
    private String emplDept;   // 부서 코드
    private String emplGrade;  // 직책 코드
    private String photoName;  // 사진 파일명
    private String photoPath;  // 사진 파일 경로
    private String emplNotes;  // 메모
    private Date createdDate;  // 작성일자
    private String createdId;  // 작성자 ID
    private Date updatedDate;  // 수정일자
    private String updatedId;  // 수정자 ID

}
