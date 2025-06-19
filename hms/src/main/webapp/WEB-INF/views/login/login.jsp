<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
    <script src="https://unpkg.com/html5-qrcode" type="text/javascript"></script>
</head>

<body>
<div class="login-container">
    <h2>로그인</h2>

    <!-- 에러 메시지 출력 -->
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <!-- 일반 로그인 -->
    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
        <div class="form-group">
            <label for="emplId">사번 (ID)</label><br>
            <input type="text" id="emplId" name="emplId" required>
        </div>

        <div class="form-group">
            <button type="submit">로그인</button>
            <pre></pre>
            <button type="button" id="qr-login-btn">QR로 로그인</button>
        </div>
    </form>

    <!-- QR 코드 리더기 표시 영역 -->
    <div id="qr-reader"></div>
</div>

<!-- QR 스캔 스크립트 -->
<script src="${pageContext.request.contextPath}/js/login.js" defer></script>

</body>
</html>
