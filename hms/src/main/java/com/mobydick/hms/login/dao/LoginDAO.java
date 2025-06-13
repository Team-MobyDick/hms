package com.mobydick.hms.login.dao;

import com.mobydick.hms.login.vo.LoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

// 로그인 매퍼
@Mapper
public interface LoginDAO {

    // 직원 ID로 로그인 사용자 정보 조회
    LoginVO selectEmployeeById(String emplId) throws DataAccessException;

}
