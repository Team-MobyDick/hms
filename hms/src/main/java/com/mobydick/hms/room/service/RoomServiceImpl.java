package com.mobydick.hms.room.service;

import com.mobydick.hms.room.dao.RoomDAO;
import com.mobydick.hms.room.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 객실관리 서비스 인터페이스 구현 객체
@Service
@Transactional
public class RoomServiceImpl implements RoomService{

    // RoomDAO 주입
    @Autowired
    private RoomDAO roomDAO;

    // 전체 객실 조회
    @Override
    public List<RoomVO> selectAllRooms() throws Exception {
        return roomDAO.selectAllRooms();
    }

    // 객실 추가
    @Override
    public void insertRoom(RoomVO room) throws Exception {
        roomDAO.insertRoom(room);
    }
    
    // 객실 타입과 이름 조회
    @Override
    public Map<String, String> getRoomTypeAndName() {

        List<Map<String, String>> roomTypes = roomDAO.getRoomTypeAndName();
        Map<String, String> roomTypeMap = new HashMap<>();

        // 여러 결과를 Map으로 변환
        for (Map<String, String> roomType : roomTypes) {
            roomTypeMap.put(roomType.get("CODE_ID"), roomType.get("CODE_NAME"));
        }
        return roomTypeMap;
    }

    // 객실 정보 수정
    @Override
    public void updateRoom(String roomId, RoomVO updatedRoom) throws Exception {

        roomDAO.updateRoom(roomId, updatedRoom);

    }

}
