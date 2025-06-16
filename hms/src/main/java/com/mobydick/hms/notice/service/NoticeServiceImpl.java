package com.mobydick.hms.notice.service;

import com.mobydick.hms.notice.dao.NoticeDAO;
import com.mobydick.hms.notice.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 공지사항 서비스 구현 객체
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeDAO noticeDAO;

    // 공지사항 조회
    @Override
    public List<NoticeVO> selectAllNotices() throws Exception {
        return noticeDAO.selectAllNotice();
    }

    // 공지사항 등록
    @Override
    public void insertNotice(NoticeVO notice) throws Exception {
        noticeDAO.insertNotice(notice);
    }

    // 공지사항 수정
    @Override
    public void updateNotice(NoticeVO notice) throws Exception {
        noticeDAO.updateNotice(notice);

    }

    // 공지사항 삭제
    @Override
    public void deleteNotice(int noticeId) {
        noticeDAO.deleteNotice(noticeId);

    }

    // 전체 공지사항 조회
}
