package com.mobydick.hms.code.service;

import com.mobydick.hms.code.dao.CodeDAO;
import com.mobydick.hms.code.vo.CodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 서비스 인터페이스 구현 객체
@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeDAO codeDAO;
    
    // 객실 관련 코드 조회
    @Override
    public List<CodeVO> getRoomCode() {
        return codeDAO.getRoomCode();
    }
}
