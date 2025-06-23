package com.mobydick.hms.login.service;

import com.mobydick.hms.login.dao.LoginDAO;
import com.mobydick.hms.login.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{


    // LoginDAO 주입
    @Autowired
    private LoginDAO loginDAO;

    // 직원 ID로 로그인 사용자 정보 조회
    @Override
    public LoginVO selectEmployeeById(String emplId) throws Exception{

        LoginVO loginUser = loginDAO.selectEmployeeById(emplId);

        // 퇴사자는 로그인 불가
        if (loginUser != null && "Y".equals(loginUser.getRetiredYn())) {
            return null;
        }

        return loginUser;

    }

}
