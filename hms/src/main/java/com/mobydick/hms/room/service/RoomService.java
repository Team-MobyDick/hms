package com.mobydick.hms.room.service;


import com.mobydick.hms.room.vo.RoomVO;

import java.util.List;

// 객실관리 서비스 인터페이스
public interface RoomService {

    // 전체 객실 조회
    List<RoomVO> selectAllRooms() throws Exception;

}
