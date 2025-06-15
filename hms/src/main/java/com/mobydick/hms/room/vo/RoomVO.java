package com.mobydick.hms.room.vo;

import lombok.Data;
import java.sql.Date;

// 객실 정보를 담는 VO
@Data
public class RoomVO {

    // 기본 객실 정보
    private String roomId;         // 객실 ID
    private int roomNumber;        // 객실 번호
    private String roomClass;      // 객실 등급 코드 (TP_01 등)
    private String reservDate;     // 예약 일자 (yyyy-MM-dd)

    // 생성/수정 정보
    private Date createdDate;      // 등록 일자
    private String createdId;      // 등록자 ID
    private Date updatedDate;      // 수정 일자
    private String updatedId;      // 수정자 ID

    // 객실 등급 코드 정보 (조인용)
    private String codeId;         // 객실 타입 코드
    private String codeName;       // 객실 타입 이름
}
