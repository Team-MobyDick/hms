package com.mobydick.hms.room.controller;


import com.mobydick.hms.code.service.CodeService;
import com.mobydick.hms.code.vo.CodeVO;
import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.room.service.RoomService;
import com.mobydick.hms.room.vo.RoomVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 객실 관리 컨트롤러
@Controller
@RequestMapping("/room")
public class RoomController {

    // 의존성 주입
    @Autowired
    private RoomService roomService;

    @Autowired
    private CodeService codeService;

    // 객실 목록 페이지
    @GetMapping("/list")
    public String room(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            HttpSession session
    ) throws Exception {
        
        try {

            int totalCount = roomService.selectAllRooms().size();               // 전체 객실 수 조회
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);   // 한 페이지에 표시할 객실 수
            int startIndex = (page - 1) * pageSize;                             // 시작 위치

            // 전체 객실 조회
            List<RoomVO> allRooms =  roomService.selectAllRooms();
            List<RoomVO> roomList;

            // 페이징 처리
            if (startIndex < totalCount) {
                roomList = allRooms.subList(startIndex, Math.min(startIndex + pageSize, totalCount));
            } else {
                roomList = new ArrayList<>();
            }

            // 객실 관련 코드 조회
            List<CodeVO> codeList =  codeService.getRoomCode();

            // view에 전달할 값
            model.addAttribute("screenTitle", "객실 관리");
            model.addAttribute("roomList", roomList);
            model.addAttribute("codeList", codeList);
            model.addAttribute("bodyPage", "room/room.jsp");

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);

            return "index";

        } catch (Exception e) {

            e.printStackTrace();    // 에러 출력 용(운영시에는 뺼것)
            model.addAttribute("errorMessage", "목록 조회 중 오류 발생");
            return "error";

        }

    }

    // 객실 세부 조회
    @GetMapping("/detail")
    public String roomDetail(Model model,
                             @RequestParam String roomId) throws Exception {

        // 선택한 객실 한 개만 조회
        RoomVO vo = roomService.selectRoomById(roomId);

        // 객실 관련 코드 조회
        List<CodeVO> codeList =  codeService.getRoomCode();

        // view에 전달할 값
        model.addAttribute("codeList", codeList);
        model.addAttribute("roomDetail", vo);

        return "room/detailRoom";

    }

    // 객실 등록
    @PostMapping("/add")
    @ResponseBody
    public String addRoom(RoomVO roomVO, HttpSession session) throws Exception {

        // 로그인 유저 정보
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String user = loginUser.getEmplId();

        // 객실 번호 생성
        String roomId = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

        // vo에 값 세팅
        roomVO.setRoomId(roomId);
        roomVO.setCreatedId(user);
        roomVO.setUpdatedId(user);

        // 등록 처리
        roomService.insertRoom(roomVO);
        
        return "success";

    }

    // 객실 수정
    @PostMapping("/upd")
    @ResponseBody
    public String updateRoom(RoomVO roomVO,
                             @RequestParam String roomId,
                             @RequestParam String roomName,
                             @RequestParam String roomClass,
                             @RequestParam String roomClassName,
                             HttpSession session) throws Exception {

        // 로그인 유저 정보
        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");
        String user = loginUser.getEmplId();

        // 수정할 객실 정보 조회
        RoomVO vo = roomService.selectRoomById(roomId);

        // 수정할 데이터 세팅
        vo.setUpdatedId(user);
        vo.setRoomName(roomName);
        vo.setRoomClass(roomClass);
        vo.setRoomClassName(roomClassName);

        // 객실 수정
        roomService.roomUpdate(vo);

        return "success";

    }

    // 객실 삭제
    @PostMapping("/delete")
    @ResponseBody
    public String deleteRoom(@RequestParam String roomId) throws Exception {

        // 삭제 처리
        roomService.roomDelete(roomId);
        return "success";

    }

}
