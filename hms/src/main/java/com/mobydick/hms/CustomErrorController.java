package com.mobydick.hms;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 공통 에러페이지 컨트롤러
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // HTTP 에러 코드 확인
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            // 404 에러 처리
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";  // 404 페이지로 이동
            }

            // 405 에러 처리
            if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "error/405";  // 405 페이지로 이동
            }
        }
        return "/error";  // 일반 에러 페이지
    }

}
