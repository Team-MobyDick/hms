package com.mobydick.hms.work.service;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.vo.WorkVO;

import java.util.List;

// 할일 관리 서비스 인터페이스
public interface WorkService {

    // 전체 코드리스트 불러오기 (codeId, codeName)
    List<WorkVO> getCodeIdAndName() throws Exception;

    // 전체 주 업무 목록 조회
    List<WorkVO> selectAllWorkM() throws Exception;

    // 개인 업무 목록 조회
    List<WorkVO> selectWorkDByEmplByDate(String emplId, String date);

    // 주 업무 등록 (최고관리자)

    // 주 업무 수정 (최고관리자)

    // 주 업무 삭제 (최고관리자)

    // 주 업무별 상세 업무 배분 목록 조회

    // 상세 업무 등록 (최고관리자, 중간관리자)

    // 상세 업무 수정 (최고관리자, 중간관리자, 직원)

    // 상세 업무 삭제 (최고관리자, 중간관리자)

}
