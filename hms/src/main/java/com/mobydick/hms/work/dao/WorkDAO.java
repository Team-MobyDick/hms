package com.mobydick.hms.work.dao;

import com.mobydick.hms.work.vo.WorkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

// 할일 관리 매퍼
@Mapper
public interface WorkDAO {


    List<WorkVO> getCodeIdAndName() throws DataAccessException;

    List<WorkVO> selectAllWorkM() throws DataAccessException;

    List<WorkVO> selectWorkDByEmplByDate(String emplId, String date) throws DataAccessException;

    void insertWorkM(WorkVO workM) throws DataAccessException;

    void updateWorkM(WorkVO workM) throws DataAccessException;

    void insertWorkD(WorkVO workD) throws DataAccessException;

    List<WorkVO> getDetailWorkList(String workMId, String date) throws DataAccessException;

    List<WorkVO> getDept() throws DataAccessException;

    List<WorkVO> getImpo() throws DataAccessException;

    List<WorkVO> getRoom() throws DataAccessException;

    List<WorkVO> getEmpl() throws DataAccessException;
}
