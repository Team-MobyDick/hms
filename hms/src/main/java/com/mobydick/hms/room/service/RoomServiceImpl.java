package com.mobydick.hms.room.service;

import com.mobydick.hms.room.dao.RoomDAO;
import com.mobydick.hms.room.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 객실관리 서비스 인터페이스 구현 객체
@Service
@Transactional
public class RoomServiceImpl implements RoomService{

    // RoomDAO 주입
    @Autowired
    private RoomDAO roomDAO;

    @Override
    public List<RoomVO> selectAllRooms() throws Exception {
        return roomDAO.selectAllRooms();
    }
}
