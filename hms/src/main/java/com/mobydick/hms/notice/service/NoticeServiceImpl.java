package com.mobydick.hms.notice.service;

import com.mobydick.hms.notice.dao.NoticeDAO;
import com.mobydick.hms.notice.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  //트랙잭션 관리 어노테이션

import java.util.List;

// 공지사항 서비스 구현 객체
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService{

    @Autowired  //스프링이 NoticeDAO 구현체를 자동으로 주입해 줌
    private NoticeDAO noticeDAO;

    // 공지사항 조회
    @Override
    @Transactional(readOnly = true) // 조회 전용 트랜잭션 설정
    public List<NoticeVO> selectAllNotices() throws Exception {
        return noticeDAO.selectAllNotices(); //DAO 메서드 이름과 일치하는지 확인 (DAO에서는 selectAllNotice())
    }

    // 공지사항 상세
    @Override
    @Transactional(readOnly = true) // 조회 전용 트랜잭션 설정
    public NoticeVO selectNoticeById(int noticeId) throws Exception {
        return noticeDAO.selectNoticeById(noticeId);
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
    public void deleteNotice(int noticeId) throws Exception {
        noticeDAO.deleteNotice(noticeId);

    }

}
