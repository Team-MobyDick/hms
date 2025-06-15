package com.mobydick.hms.room.dao;

import com.mobydick.hms.room.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

// 객실 관리 DAO (Mapper interface)
@Mapper
public interface RoomDAO {

    // 전체 객실 조회
    List<RoomVO> selectAllRooms() throws DataAccessException;

    // 객실 등록
    void insertRoom(RoomVO room) throws DataAccessException;

    // 객실 타입 목록 조회 (codeId, codeName)
    List<RoomVO> getRoomTypeAndName() throws DataAccessException;

    // 객실 정보 수정
    void updateRoom(RoomVO room) throws DataAccessException;

    // 객실 ID로 상세 조회
    RoomVO selectRoomById(String roomId) throws DataAccessException;

    // 객실 삭제
    void deleteRoom(String roomId) throws DataAccessException;
}
