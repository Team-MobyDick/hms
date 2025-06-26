package com.mobydick.hms.room.dao;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.vo.WorkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

// 객실 관리 DAO (Mapper interface)
@Mapper
public interface RoomDAO {

    // 전체 객실 조회
    List<RoomVO> selectAllRooms() throws DataAccessException;
    
    // 객실 세부 조회
    RoomVO selectRoomById(String roomId) throws DataAccessException;

    // 객실 필터링
    List<RoomVO> selectRoomsByType(String roomType) throws DataAccessException;
    
    // 객실 등록
    void insertRoom(RoomVO roomVO) throws DataAccessException;

    // 객실 등록 시 호실 중복 체크
    RoomVO selectRoomsByName(String roomName) throws DataAccessException;

    // 객실 수정 시 객실 타입이 다르면 수정시키게 하기 위함
    RoomVO selectRoomsByClass(String roomName) throws DataAccessException;

    // 객실 수정
    void roomUpdate(RoomVO roomVO) throws DataAccessException;

    // 객실 삭제
    void roomDelete(String roomId) throws DataAccessException;

    // 객실 할일 조회
    List<WorkVO> selectWorkListByRoomId(String roomId);

}
