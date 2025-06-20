package com.mobydick.hms.code.service;

import com.mobydick.hms.code.vo.CodeVO;

import java.util.List;

// 코드 서비스 인터페이스
public interface CodeService {

    List<CodeVO> getRoomCode();

}
