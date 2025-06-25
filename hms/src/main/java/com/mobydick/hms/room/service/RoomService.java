package com.mobydick.hms.room.service;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.vo.WorkVO;

import java.util.List;

// 객실 관리 서비스 인터페이스
public interface RoomService {

    // 전체 객실 목록 조회
    List<RoomVO> selectAllRooms() throws Exception;
    
    // 객실 세부 조회
    RoomVO selectRoomById(String roomId) throws Exception;

    // 객실 필터링
    List<RoomVO> selectRoomsByType(String roomType) throws Exception;

    // 객실 등록
    void insertRoom(RoomVO roomVO) throws Exception;

    // 객실 수정
    void roomUpdate(RoomVO roomVO) throws Exception;

    // 객실 삭제
    void roomDelete(String roomId) throws Exception;

    // 객실 할일 조회
    List<WorkVO> selectWorkListByRoomId(String roomId);
}
