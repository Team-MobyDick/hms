package com.mobydick.hms.notice.dao;

import com.mobydick.hms.notice.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDAO {

        // 전체 공지사항 목록
        List<NoticeVO> selectAllNotices();

        // 공지사항 상세 조회
        NoticeVO selectNoticeById(int noticeId);

        // 공지사항 등록
        void insertNotice(NoticeVO noticeVO);

        // 공지사항 수정
        void updateNotice(NoticeVO noticeVO);

        // 공지사항 삭제
        void deleteNotice(int noticeId);
}
