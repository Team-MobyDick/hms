package com.mobydick.hms.login.vo;

import lombok.Data;

import java.sql.Date;

// 로그인 데이터 저장용 객체
@Data
public class LoginVO {

    private String empl_id;     // 직원 ID
    private String empl_name;   // 직원명
    private String empl_phone;  // 연락처
    private String empl_dept;   // 부서 코드
    private String empl_grade;  // 직책 코드
    private String photo_name;  // 사진 파일명
    private String photo_path;  // 사진 파일 경로
    private String empl_notes;  // 메모
    private Date created_date;  // 작성일자
    private String created_id;  // 작성자 ID
    private Date updated_date;  // 수정일자
    private String updated_id;  // 수정자 ID

}
