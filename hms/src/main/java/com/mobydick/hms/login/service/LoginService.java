package com.mobydick.hms.login.service;

import com.mobydick.hms.login.vo.LoginVO;

// 로그인 서비스 인터페이스
public interface LoginService {

    // 직원 ID로 로그인 사용자 정보 조회
    LoginVO selectEmployeeById(String emplId) throws Exception;
    
}
