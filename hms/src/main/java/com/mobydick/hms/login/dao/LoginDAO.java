package com.mobydick.hms.login.dao;

import com.mobydick.hms.login.vo.LoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

// 로그인 매퍼
@Mapper
public interface LoginDAO {

    // 로그인 유저 정보
    LoginVO selectEmployeeById(String empl_id) throws DataAccessException;

}
