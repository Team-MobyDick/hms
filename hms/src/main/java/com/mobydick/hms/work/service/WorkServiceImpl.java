package com.mobydick.hms.work.service;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.dao.WorkDAO;
import com.mobydick.hms.work.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 할일 관리 서비스 인터페이스 구현 객체
@Service
@Transactional
public class WorkServiceImpl implements WorkService{

    @Autowired
    WorkDAO workDAO;

    @Override
    public List<WorkVO> getCodeIdAndName() throws Exception {
        return workDAO.getCodeIdAndName();
    };

    @Override
    public List<WorkVO> selectAllWorkM() throws Exception {
        return workDAO.selectAllWorkM();
    };

    @Override
    public List<WorkVO> selectWorkDByEmplByDate(String emplId, String date) {
        return workDAO.selectWorkDByEmplByDate(emplId, date);
    };
}
