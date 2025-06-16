package com.mobydick.hms.room.vo;

import lombok.Data;
import java.sql.Date;

// 객실 정보를 담는 VO
@Data
public class RoomVO {

    // 기본 객실 정보
    private String roomId;         // 객실 ID
    private String roomName;       // 객실 이름
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

    // 업무 디테일 정보 (업무 관련)
    private String emplId;         // 담당자 ID
    private String cleanState;     // 청소 상태
    private String extraInfo;      // 특이사항
    private String emplName;      // 담당자 이름

}
