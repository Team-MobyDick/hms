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

import java.util.*;
import java.util.stream.Collectors;

// 객실 관리 컨트롤러
@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 객실 목록 페이지
    @GetMapping("/list")
    public String room(Model model, @RequestParam(defaultValue = "1") int page) throws Exception {

        int pageSize = 10; // 페이지당 항목 수
        int totalCount = roomService.selectAllRooms().size(); // 전체 객실 수
        int totalPages = (int) Math.ceil((double) totalCount / pageSize); // 전체 페이지 수 계산

        // 페이지 범위 처리 (1페이지 ~ totalPages 페이지까지)
        int startIndex = (page - 1) * pageSize;
        List<RoomVO> roomList = roomService.selectAllRooms().subList(startIndex, Math.min(startIndex + pageSize, totalCount));

        List<RoomVO> roomTypeList = roomService.getRoomTypeAndName();  // 객실 타입 목록

        // 타입 맵 (코드ID → 코드명) 생성
        Map<String, String> roomTypeMap = roomTypeList.stream()
                .collect(Collectors.toMap(RoomVO::getCodeId, RoomVO::getCodeName, (v1, v2) -> v1, LinkedHashMap::new));

        model.addAttribute("screenTitle", "객실 관리");
        model.addAttribute("currentPage", page);                        // 현재 페이지
        model.addAttribute("totalPages", totalPages);                   // 전체 페이지 수
        model.addAttribute("roomTypeMap", roomTypeMap);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("roomList", roomList);                       // 현재 페이지에 해당하는 객실 목록
        model.addAttribute("bodyPage", "room/room.jsp");

        return "index";
    }

    // 객실 등록
    @PostMapping("/add")
    public ResponseEntity<String> addRoom(@RequestBody RoomVO vo, HttpSession session) {
        try {
            // 방 ID 생성
            String roomId = "001" + String.format("%015d", new Random().nextInt(100000));
            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();

            vo.setRoomId(roomId);
            vo.setCreatedId(loginUserId);
            vo.setUpdatedId(loginUserId);

            roomService.insertRoom(vo);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    // 객실 타입 목록 (AJAX)
    @GetMapping("/types")
    @ResponseBody
    public List<RoomVO> getRoomTypes() {
        try {
            return roomService.getRoomTypeAndName();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 객실 정보 수정
    @PutMapping("/update/{roomId}")
    public ResponseEntity<String> updateRoom(@PathVariable String roomId, @RequestBody RoomVO updatedRoom, HttpSession session) {
        try {
            String loginUserId = ((LoginVO) session.getAttribute("loginUser")).getEmplId();
            if (loginUserId == null) loginUserId = "UNKNOWN";

            RoomVO original = roomService.selectRoomById(roomId);
            if (original == null) return ResponseEntity.notFound().build();

            updatedRoom.setRoomId(roomId);
            updatedRoom.setUpdatedId(loginUserId);
            roomService.updateRoom(updatedRoom);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    // 객실 삭제
    @DeleteMapping("/delete/{roomId}")
    @ResponseBody
    public ResponseEntity<String> deleteRoom(@PathVariable("roomId") String roomId) {
        try {
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }
}
