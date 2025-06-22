package com.mobydick.hms.work.service;

import com.mobydick.hms.room.vo.RoomVO;
import com.mobydick.hms.work.dao.WorkDAO;
import com.mobydick.hms.work.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// 할일 관리 서비스 인터페이스 구현 객체
@Service
@Transactional
public class WorkServiceImpl implements WorkService{
    private final Path uploadDir = Paths.get("C:/hms_uploads/work_photos");

    @Override
    public String saveFile(MultipartFile file) throws Exception {
        // 업로드 폴더가 없으면 생성
        if (Files.notExists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex); // ".jpg", ".png" 등
        }

        // 고유 파일 이름
        String newFileName = UUID.randomUUID().toString() + extension;
        Path filePath = uploadDir.resolve(newFileName);

        // 파일 쓰기
        file.transferTo(filePath.toFile());

        return newFileName;
    };

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
    public List<WorkVO> getDetailWorkList(String workMId, String date) {
        return workDAO.getDetailWorkList(workMId, date);
    };

    @Override
    public List<WorkVO> selectWorkDByEmplByDate(String emplId, String date) {
        return workDAO.selectWorkDByEmplByDate(emplId, date);
    };

    @Override
    public void insertWorkM(WorkVO workM) throws Exception {
        workDAO.insertWorkM(workM);
    };

    @Override
    public void updateWorkM(WorkVO workM) throws Exception {
        workDAO.updateWorkM(workM);
    };

    @Override
    public void deleteWorkM(String workMId) throws Exception {
        workDAO.deleteWorkM(workMId);
    };

    @Override
    public void insertWorkD(WorkVO workD) throws Exception {
        workDAO.insertWorkD(workD);
    };

    @Override
    public WorkVO selectDetailWorkD(String workDId) throws Exception {
        return workDAO.selectDetailWorkD(workDId);
    };

    @Override
    public void updateWorkD(String workDId, String workDName, String workDEmplId, LocalDate workDDate, String workDStartPath, Timestamp workDStartTime, String workDEndPath, Timestamp workDEndTime) throws Exception {
        workDAO.updateWorkD(workDId, workDName, workDEmplId, workDDate, workDStartPath, workDStartTime, workDEndPath, workDEndTime);
    };

    @Override
    public List<WorkVO> getDept() {
        return workDAO.getDept();
    };
    @Override
    public List<WorkVO> getImpo() {
        return workDAO.getImpo();
    };
    @Override
    public List<WorkVO> getRoom() {
        return workDAO.getRoom();
    };
    @Override
    public List<WorkVO> getEmpl() {
        return workDAO.getEmpl();
    };
    @Override
    public List<WorkVO> selectEmployeesByDept(String emplDept) throws Exception {
        return workDAO.selectEmployeesByDept(emplDept);
    };
}
