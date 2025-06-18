<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- header.jsp -->

<div class="header">

    <!-- 좌측: 메뉴 아이콘 -->
    <div class="header-left">
        <div class="menu-icon" id="menuToggle">☰</div>
        <img src="../../images/sample.jpg" onclick="goMain();"></img>
        <div class="screen-title">${screenTitle}</div>
    </div>

    <!-- 우측: 사용자 정보 + 로그아웃 -->
    <div class="header-right">
        <span>${sessionScope.loginUser.emplName} 님 환영합니다</span>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">로그아웃</a>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/header.js" defer></script>