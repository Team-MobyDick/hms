package com.mobydick.hms.room.service;

import com.mobydick.hms.room.vo.RoomVO;
import java.util.List;

// 객실 관리 서비스 인터페이스
public interface RoomService {

    // 전체 객실 목록 조회
    List<RoomVO> selectAllRooms() throws Exception;

    // 객실 등록
    void insertRoom(RoomVO room) throws Exception;

    // 객실 타입 목록 조회 (codeId, codeName)
    List<RoomVO> getRoomTypeAndName() throws Exception;

    // 객실 정보 수정
    void updateRoom(RoomVO room) throws Exception;

    // 객실 ID로 상세 조회
    RoomVO selectRoomById(String roomId) throws Exception;

    // 객실 삭제
    void deleteRoom(String roomId);
}
