package com.mobydick.hms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

/**
 * Spring MVC에서 확장자(.js 등)에 따라 MIME 타입을 지정하는 설정 클래스.
 * 단, 이 설정은 정적 리소스(static/js/*.js)에는 적용되지 않으며,
 * Controller(@ResponseBody)에서 직접 응답하는 경우에만 유효하다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 확장자 기반 Content-Type MIME 타입 지정.
     * ex) /sample.js → application/javascript; charset=UTF-8
     * → 주의: 정적 자원에는 적용되지 않음.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("js", new MediaType("application", "javascript", StandardCharsets.UTF_8));
    }
}