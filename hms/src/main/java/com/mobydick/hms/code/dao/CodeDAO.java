package com.mobydick.hms.code.dao;

import com.mobydick.hms.code.vo.CodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 코드 매퍼
@Mapper
public interface CodeDAO {

    // 객실 관련 코드 조회
    List<CodeVO> getRoomCode();
    
}
