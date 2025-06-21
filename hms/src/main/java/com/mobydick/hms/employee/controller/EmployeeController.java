package com.mobydick.hms.employee.controller;

import com.mobydick.hms.employee.service.EmployeeService;
import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.login.vo.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID; // 고유한 파일명 생성을 위함

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 이미지 저장 기본 경로 (실제 환경에 맞게 수정 필요)
    private final String uploadDir = "C:/hms_uploads/employee_photos";

    public EmployeeController() {
        // 업로드 디렉토리가 없으면 생성
        try {
            Path path = Paths.get(uploadDir);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

        } catch (Exception e) {

            e.printStackTrace();

            // 에러 처리: 디렉토리 생성 실패
            System.err.println("Failed to create upload directory: " + uploadDir);
        }
    }

    // 직원 목록 조회
    @GetMapping("/list")
    public String employeeList(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "default") String sortOrder, // <-- 추가된 부분
            HttpSession session) throws Exception {

        int pageSize = 10; // 페이지당 항목 수
        // 전체 직원 수 조회 시 정렬 기준은 필요 없음 (페이징을 위한 전체 개수)
        int totalCount = employeeService.selectAllEmployees(null).size(); // <-- null 전달
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        int startIndex = (page - 1) * pageSize;

        // 정렬 기준을 서비스 계층으로 전달
        List<EmployeeVO> allEmployees = employeeService.selectAllEmployees(sortOrder); // <-- sortOrder 전달

        List<EmployeeVO> employeeList = allEmployees.subList(startIndex, Math.min(startIndex + pageSize, totalCount));

        System.out.println("test = " + employeeList.toString());

        LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

        model.addAttribute("currentPage", page);                        // 현재 페이지
        model.addAttribute("totalPages", totalPages);                   // 전체 페이지 수
        model.addAttribute("sortOrder", sortOrder); // <-- 현재 정렬 기준을 JSP로 다시 전달하여 <select>의 selected 옵션을 유지

        if (loginUser != null) {
            model.addAttribute("userRole", loginUser.getEmplGrade());
            model.addAttribute("userDept", loginUser.getEmplDept());
        } else {
            model.addAttribute("userRole", "");
            model.addAttribute("userDept", "");
        }

        model.addAttribute("screenTitle", "직원 관리");
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("bodyPage", "employee/employee.jsp");

        return "index";
    }

    // 직원 등록 처리
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerEmployee(@ModelAttribute EmployeeVO employeeVO, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

            String uuid = UUID.randomUUID().toString().replace("-", "");
            employeeVO.setEmplId(uuid.substring(0, 20));

            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            employeeVO.setCreatedId(loginUser.getEmplId());

            if (
                    employeeVO.getEmplName() == null || employeeVO.getEmplName().trim().isEmpty() || // 이름 없음
                    employeeVO.getEmplDept() == null || employeeVO.getEmplDept().trim().isEmpty() || // 부서 없음
                    employeeVO.getEmplGrade() == null || employeeVO.getEmplGrade().trim().isEmpty() // 직급 없음
            ) {
                return ResponseEntity.badRequest().body("필수 입력 항목(이름, 부서, 직책)을 모두 채워주세요.");
            }

            // 등록 시에는 사진 관련 필드를 null로 설정
            employeeVO.setPhotoName(null);
            employeeVO.setPhotoPath(null);

            employeeService.insertEmployee(employeeVO);

            return ResponseEntity.ok("직원이 성공적으로 등록되었습니다.");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 등록 중 오류가 발생했습니다: " + e.getMessage());

        }
    }

    // 직원 정보 수정 처리
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateEmployee(@ModelAttribute EmployeeVO employeeVO, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            String userRole = loginUser.getEmplGrade();
            String userDept = loginUser.getEmplDept();

            if (!"GR_01".equals(userRole) && !"GR_02".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("직원 정보를 수정할 권한이 없습니다.");
            }

            EmployeeVO targetEmployee = employeeService.selectEmployeeById(employeeVO.getEmplId());

            if (targetEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정하려는 직원을 찾을 수 없습니다.");
            }

            if ("GR_02".equals(userRole)) {
                if (!userDept.equals(targetEmployee.getEmplDept())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀장은 같은 부서의 직원 정보만 수정할 수 있습니다.");
                }
            }

            if (
                    employeeVO.getEmplName() == null || employeeVO.getEmplName().trim().isEmpty() ||
                    employeeVO.getEmplDept() == null || employeeVO.getEmplDept().trim().isEmpty() ||
                    employeeVO.getEmplGrade() == null || employeeVO.getEmplGrade().trim().isEmpty()
            ) {
                return ResponseEntity.badRequest().body("필수 입력 항목(이름, 부서, 직책)을 모두 채워주세요.");
            }

            employeeVO.setUpdatedId(loginUser.getEmplId());

            employeeService.updateEmployee(employeeVO);

            return ResponseEntity.ok("직원 정보가 성공적으로 수정되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 정보 수정 중 오류가 발생했습니다: " + e.getMessage());

        }
    }

    // 직원 사진 업로드 및 수정 처리
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public ResponseEntity<?> uploadEmployeePhoto(
            @RequestParam("emplId") String emplId,
            @RequestParam("photo") MultipartFile file,
            HttpSession session
    ) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            String userRole = loginUser.getEmplGrade();
            String userDept = loginUser.getEmplDept();

            // GR_01 (총지배인) 또는 GR_02 (팀장)만 사진 업로드/수정 가능
            if (!"GR_01".equals(userRole) && !"GR_02".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사진을 업로드/수정할 권한이 없습니다.");
            }

            EmployeeVO targetEmployee = employeeService.selectEmployeeById(emplId);

            if (targetEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("직원을 찾을 수 없습니다.");
            }

            // 팀장(GR_02)인 경우, 같은 부서의 직원만 사진 업로드/수정 가능하도록 제한
            if ("GR_02".equals(userRole)) {
                if (!userDept.equals(targetEmployee.getEmplDept())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀장은 같은 부서의 직원 사진만 수정할 수 있습니다.");
                }
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("업로드할 파일이 없습니다.");
            }

            // 파일 저장 로직
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uniqueFilename = UUID.randomUUID().toString() + fileExtension; // 고유한 파일명 생성
            Path filePath = Paths.get(uploadDir, uniqueFilename);

            // 파일을 서버에 저장
            Files.copy(file.getInputStream(), filePath);

            // DB 업데이트
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO.setEmplId(emplId);
            employeeVO.setPhotoName(uniqueFilename); // 실제 저장된 파일명
            employeeVO.setPhotoPath("employee_photos"); // 하위 경로 (uploadDir 바로 아래라고 가정)
            employeeVO.setUpdatedId(loginUser.getEmplId());

            employeeService.updateEmployeePhoto(employeeVO);

            return ResponseEntity.ok("사진이 성공적으로 업로드되었습니다.::" + uniqueFilename + "::" + "employee_photos"); // 파일명, 경로 반환

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사진 업로드 중 오류가 발생했습니다: " + e.getMessage());

        }
    }


    // 직원 삭제 처리
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteEmployee(@RequestParam("emplId") String emplId, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            String userRole = loginUser.getEmplGrade();
            if (!"GR_01".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("직원 정보를 삭제할 권한이 없습니다.");
            }

            if (emplId == null || emplId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("삭제할 직원 ID가 필요합니다.");
            }

            // DB에서 사진 정보를 가져와 파일 시스템에서 삭제 (선택 사항)
            EmployeeVO employeeToDelete = employeeService.selectEmployeeById(emplId);

            if (employeeToDelete != null && employeeToDelete.getPhotoName() != null && !employeeToDelete.getPhotoName().isEmpty()) {
                try {
                    Path photoPath = Paths.get(uploadDir, employeeToDelete.getPhotoName());
                    Files.deleteIfExists(photoPath);
                } catch (Exception e) {
                    System.err.println("Failed to delete employee photo file: " + employeeToDelete.getPhotoName() + " - " + e.getMessage());
                    // 파일 삭제 실패해도 직원 정보는 삭제 진행
                }
            }

            employeeService.deleteEmployee(emplId);

            return ResponseEntity.ok("직원이 성공적으로 삭제되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 삭제 중 오류가 발생했습니다: " + e.getMessage());

        }
    }

    // 직원 퇴사 처리
    @PostMapping("/retire")
    @ResponseBody
    public ResponseEntity<?> retireEmployee(@RequestParam("emplId") String emplId, HttpSession session) {
        try {
            LoginVO loginUser = (LoginVO) session.getAttribute("loginUser");

            if (loginUser == null || loginUser.getEmplId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다. 다시 로그인 해주세요.");
            }

            String userRole = loginUser.getEmplGrade();
            String updatedId = loginUser.getEmplId();

            // 총지배인(GR_01)만 퇴사 처리 권한 설정
            if (!"GR_01".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("직원을 퇴사 처리할 권한이 없습니다.");
            }

            if (emplId == null || emplId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("퇴사 처리할 직원 ID가 필요합니다.");
            }

            EmployeeVO targetEmployee = employeeService.selectEmployeeById(emplId);

            if (targetEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("퇴사 처리하려는 직원을 찾을 수 없습니다.");
            }
            if ("Y".equals(targetEmployee.getRetiredYn())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 퇴사 처리된 직원입니다.");
            }

            // emplId와 updatedId를 모두 서비스에 전달
            employeeService.retireEmployee(emplId, updatedId);

            return ResponseEntity.ok("직원이 성공적으로 퇴사 처리되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 퇴사 처리 중 오류가 발생했습니다:" + e.getMessage());
        }
    }

}