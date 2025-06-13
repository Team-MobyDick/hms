package com.mobydick.hms.room.dao;

import com.mobydick.hms.room.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

// 객실관리 매퍼
@Mapper
public interface RoomDAO {

    // 전체 객실을 조회
    List<RoomVO>  selectAllRooms() throws DataAccessException;
    
}
