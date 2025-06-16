package com.mobydick.hms.notice.dao;

import com.mobydick.hms.notice.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 공지사항 매퍼
@Mapper
public interface NoticeDAO {

        // 공지사항 조회
        List<NoticeVO> selectAllNotice() throws DataAccessException;

        // 공지사항 등록
        void insertNotice(NoticeVO notice) throws DataAccessException;

        // 공지사항 수정
        void updateNotice(NoticeVO notice) throws DataAccessException;

        // 공지사항 삭제
        void deleteNotice(int noticeId) throws DataAccessException;

}
