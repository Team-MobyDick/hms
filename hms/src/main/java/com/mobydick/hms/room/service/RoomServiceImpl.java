package com.mobydick.hms.room.service;

import com.mobydick.hms.room.dao.RoomDAO;
import com.mobydick.hms.room.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 객실 관리 서비스 구현체
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDAO roomDAO;

    // 전체 객실 조회
    @Override
    public List<RoomVO> selectAllRooms() throws Exception {
        return roomDAO.selectAllRooms();
    }

    // 객실 등록
    @Override
    public void insertRoom(RoomVO room) throws Exception {
        roomDAO.insertRoom(room);
    }

    // 객실 타입 목록 조회
    @Override
    public List<RoomVO> getRoomTypeAndName() {
        return roomDAO.getRoomTypeAndName();
    }

    // 객실 정보 수정
    @Override
    public void updateRoom(RoomVO room) throws Exception {
        roomDAO.updateRoom(room);
    }

    // 객실 ID로 단건 조회
    @Override
    public RoomVO selectRoomById(String roomId) throws Exception {
        return roomDAO.selectRoomById(roomId);
    }

    // 객실 삭제
    @Override
    public void deleteRoom(String roomId) {
        roomDAO.deleteRoom(roomId);
    }

    // 청소 상태, 특이 사항, 담당자 조회
    @Override
    public List<RoomVO> selectRoomsWithWorkDetails() {
        return roomDAO.selectRoomsWithWorkDetails();
    }
}
