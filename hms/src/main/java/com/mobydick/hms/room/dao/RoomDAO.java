package com.mobydick.hms.room.dao;

import com.mobydick.hms.room.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;

// 객실관리 매퍼
@Mapper
public interface RoomDAO {

    // 전체 객실을 조회
    List<RoomVO>  selectAllRooms() throws DataAccessException;

    // 객실 추가
    void insertRoom(RoomVO room) throws DataAccessException;

    // 객실 타입과 이름 조회
    List<Map<String, String>> getRoomTypeAndName() throws DataAccessException;

    // 객실 정보 수정
    void updateRoom(String roomId, RoomVO updatedRoom);

}
