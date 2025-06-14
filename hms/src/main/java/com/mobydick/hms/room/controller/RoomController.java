package com.mobydick.hms.room.controller;

import com.mobydick.hms.login.vo.LoginVO;
import com.mobydick.hms.room.service.RoomService;
import com.mobydick.hms.room.vo.RoomVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

// 객실관리 컨트롤러
@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public String room(Model model) throws Exception {

        // DB에서 객실 정보 조회
        List<RoomVO> roomList = roomService.selectAllRooms();

        model.addAttribute("roomList", roomList);
        model.addAttribute("bodyPage", "room/room.jsp");

        return "index";

    }

    // 객실 추가
    @PostMapping("/add")
    public ResponseEntity<String> addRoom(@RequestBody RoomVO vo, HttpSession session) {
        try {

            // 방 번호 랜덤 생성
            Random random = new Random();
            int randomNumber = random.nextInt(100000);
            String roomId = "001" + String.format("%015d", randomNumber); 

            // 로그인 유저 아이디 정보
            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

            // 객실 vo에 방 번호, 로그인 유저 전달
            vo.setRoomId(roomId);
            vo.setCreatedId(loginUserId);
            vo.setUpdatedId(loginUserId);

            // 객실 추가
            roomService.insertRoom(vo);
            
            return ResponseEntity.ok("success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");

        }
    }

    // 객실 타입을 반환하는 API (AJAX 호출 시 사용)
    @GetMapping("/types")
    @ResponseBody  // 이 어노테이션을 추가하여 JSON 응답으로 반환
    public Map<String, String> getRoomTypes() {
        try {
            // 객실 타입과 이름 조회
            Map<String, String> roomTypeMap = roomService.getRoomTypeAndName();
            return roomTypeMap;  // Map 형태로 반환되어 JSON으로 자동 변환됩니다.
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();  // 오류 발생 시 빈 Map 반환
        }
    }

    // 객실 정보 수정
    @PutMapping("/update/{roomId}")
    public ResponseEntity<String> updateRoom(@PathVariable("roomId") String roomId, @RequestBody RoomVO updatedRoom, HttpSession session) {

        try {

            System.out.println("updatedRoom = " + updatedRoom);

            // 서비스로 수정 요청을 전달
            roomService.updateRoom(roomId, updatedRoom);

            return ResponseEntity.ok("객실 정보 수정 성공");

        } catch (Exception e) {
            return ResponseEntity.status(400).body("수정 실패");
        }
    }

}
