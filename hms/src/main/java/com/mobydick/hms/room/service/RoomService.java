package com.mobydick.hms.room.service;


import com.mobydick.hms.room.vo.RoomVO;

import java.util.List;
import java.util.Map;

// 객실관리 서비스 인터페이스
public interface RoomService {

    // 전체 객실 조회
    List<RoomVO> selectAllRooms() throws Exception;
    
    // 객실 추가
    void insertRoom(RoomVO room) throws Exception;

    // 객실 타입과 이름 조회
    Map<String, String> getRoomTypeAndName() throws Exception;

    // 객실 정보 수정
    void updateRoom(String roomId, RoomVO updatedRoom) throws Exception;
}
