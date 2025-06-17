package com.mobydick.hms.notice.vo;

import lombok.Data;
import java.sql.Date;

// 공지사항 데이터 저장 객체
@Data
public class NoticeVO {

    // 기본 공지사항 정보
    private int noticeId;   // 공지 ID
    private String noticeTitle; //공지 제목
    private String emplId;  //글쓴이
    private String noticeContent;   //공지 내용


    // 생성/수정 정보
    private Date createdDate;   // 작성일자
    private String createdId;   // 작성자 ID
    private Date updatedDate;   // 수정일자
    private String updatedId;    // 수정자 ID


}
