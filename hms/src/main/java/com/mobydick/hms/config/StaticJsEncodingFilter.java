package com.mobydick.hms.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 정적 리소스(.js)에 대해 Content-Type 응답 헤더에 charset=UTF-8을 명시적으로 추가해주는 필터.
 * 이 설정은 Spring Boot의 정적 리소스 처리(static/js/...)에 직접 적용되므로
 * 브라우저에서 한글 주석 등 인코딩 깨짐 문제를 해결할 수 있다.
 */
@Component
@Order(1)
public class StaticJsEncodingFilter implements Filter {

    /**
     * 모든 요청 중 .js 로 끝나는 URL에 대해 응답 헤더에 charset=UTF-8을 추가한다.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // JS 파일 요청에만 적용
        if (httpRequest.getRequestURI().endsWith(".js")) {
            httpResponse.setHeader("Content-Type", "application/javascript; charset=UTF-8");
        }

        chain.doFilter(request, response);
    }
}