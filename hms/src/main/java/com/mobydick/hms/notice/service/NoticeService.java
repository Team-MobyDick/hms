package com.mobydick.hms.notice.service;

import com.mobydick.hms.notice.vo.NoticeVO;

import java.util.List;

// 공지사항 서비스 인터페이스
public interface NoticeService {

    // 전체 공지사항 목록 조회
    List<NoticeVO> selectAllNotices() throws Exception;

    // 공지사항 상세 조회 (ID로 가져오기)
    NoticeVO selectNoticeById(int noticeId) throws Exception;

    // 공지사항 등록
    void insertNotice(NoticeVO notice) throws Exception;

    // 공지사항 수정
    void updateNotice(NoticeVO notice) throws Exception;

    // 공지사항 삭제
    void deleteNotice(int noticeId) throws Exception;
}
