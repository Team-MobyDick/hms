package com.mobydick.hms.room.service;

import com.mobydick.hms.room.dao.RoomDAO;
import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    // 객실 세부 조회
    @Override
    public RoomVO selectRoomById(String roomId) throws Exception {
        return roomDAO.selectRoomById(roomId);
    }

    // 객실 필터링
    @Override
    public List<RoomVO> selectRoomsByType(String roomType) throws Exception {
        return roomDAO.selectRoomsByType(roomType);
    }

    // 객실 등록
    @Override
    public void insertRoom(RoomVO roomVO) throws Exception {
        roomDAO.insertRoom(roomVO);
    }

    // 객실 수정
    @Override
    public void roomUpdate(RoomVO roomVO) throws Exception {
        roomDAO.roomUpdate(roomVO);
    }

    // 객실 삭제
    @Override
    public void roomDelete(String roomId) throws Exception {
        roomDAO.roomDelete(roomId);
    }

    // 객실 할일 조회


    @Override
    public List<WorkVO> selectWorkListByRoomId(String roomId) {
        return roomDAO.selectWorkListByRoomId(roomId);
    }
}
