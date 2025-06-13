package com.mobydick.hms.login.service;

import com.mobydick.hms.login.dao.LoginDAO;
import com.mobydick.hms.login.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{


    @Autowired
    private LoginDAO loginDAO;

    @Override
    public LoginVO selectEmployeeById(String empl_id) throws Exception{

        System.out.println("service emplId = " + empl_id);
       return loginDAO.selectEmployeeById(empl_id);
    }

}
