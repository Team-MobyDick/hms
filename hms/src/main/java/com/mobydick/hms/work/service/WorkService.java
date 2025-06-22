package com.mobydick.hms.work.service;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.vo.WorkVO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    void insertWorkM(WorkVO vo) throws Exception;

    // 주 업무 수정 (최고관리자)
    void updateWorkM(WorkVO vo) throws Exception;

    // 주 업무 삭제 (최고관리자)
    void deleteWorkM(String workMId) throws Exception;;

    // 주 업무별 상세 업무 배분 목록 조회
    List<WorkVO> getDetailWorkList(String workMId, String date);

    // 상세 업무 등록 (최고관리자, 중간관리자)
    void insertWorkD(WorkVO vo) throws Exception;

    // 상세 업무 페이지 이동 (최고관리자, 중간관리자, 직원)
    WorkVO selectDetailWorkD(String workDId) throws Exception;

    // 상세 업무 수정 (최고관리자, 중간관리자, 직원)
    void updateWorkD(String workDId, String workDName, String workDEmplId, LocalDate workDDate, String workDStartPath, Timestamp workDStartTime, String workDEndPath, Timestamp workDEndTime) throws Exception;

    // 상세 업무 삭제 (최고관리자, 중간관리자)



    // 부서,중요도,방,직원 목록 가져오기
    List<WorkVO> getDept();
    List<WorkVO> getImpo();
    List<WorkVO> getRoom();
    List<WorkVO> getEmpl();
    List<WorkVO> selectEmployeesByDept(String emplDept) throws Exception;

    // 파일 저장후 경로 반환
    String saveFile(MultipartFile file) throws Exception;
}
