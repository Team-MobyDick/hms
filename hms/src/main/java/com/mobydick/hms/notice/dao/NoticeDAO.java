package com.mobydick.hms.notice.dao;

import com.mobydick.hms.notice.vo.NoticeVO; //NoticeVO 임포트
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 공지사항 매퍼
@Mapper
public interface NoticeDAO {

        // 공지사항 목록 조회
        List<NoticeVO> selectAllNotice() throws DataAccessException;

        // 공지사항 상세 조회
        NoticeVO selectNoticeById(int noticeId);

        // 공지사항 등록
        void insertNotice(NoticeVO notice) throws DataAccessException;

        // 공지사항 수정
        void updateNotice(NoticeVO notice) throws DataAccessException;

        // 공지사항 삭제
        void deleteNotice(int noticeId) throws DataAccessException;

}
