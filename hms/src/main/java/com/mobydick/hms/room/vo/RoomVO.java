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
    private String roomClassName;  // 객실 등급 코드 이름 (스탠다드 룸 등)

    // 생성, 수정 정보
    private Date createdDate;      // 등록 일자
    private String createdId;      // 등록자 ID
    private Date updatedDate;      // 수정 일자
    private String updatedId;      // 수정자 ID

    // 업무명, 담당자명
    private String workdName;      // 업무명
    private String emplName;       // 담당자명

}
