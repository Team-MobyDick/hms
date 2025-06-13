package com.mobydick.hms.room.vo;

import lombok.Data;

import java.sql.Date;

// 객실관리 정보를 담는 VO
@Data
public class RoomVO {

    private String roomId;       // 객실 ID
    private int roomNumber;      // 객실 번호
    private String roomClass;    // 객실 등급
    private String reservDate;   // 예약 일자 (문자열로 관리 중)

    private Date createdDate;    // 등록일자 (기본값: SYSDATE)
    private String createdId;    // 등록자 ID

    private Date updatedDate;     // 수정일자 (기본값: SYSDATE)
    private String updatedId;     // 수정자 ID

}
