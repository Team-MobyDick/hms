package com.mobydick.hms.login.service;

import com.mobydick.hms.login.vo.LoginVO;

// 로그인 서비스 인터페이스
public interface LoginService {

    // 로그인 유저 정보
    LoginVO selectEmployeeById(String empl_id) throws Exception;
    
}
