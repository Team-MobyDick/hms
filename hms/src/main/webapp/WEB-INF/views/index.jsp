<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8" />
    <title>호텔 관리 시스템</title>

    <!-- 공통 CSS (순서 중요: reset → layout → header/sidebar/footer → 개별 페이지) -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css"/>

    <!-- 개별 페이지용 CSS (예: room.jsp)  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/room.css"/>
</head>

<body>

<!-- 로그인된 사용자만 본문 출력 -->
<c:if test="${not empty sessionScope.loginUser}">

    <!-- 상단 헤더 -->
    <jsp:include page="include/header.jsp" />

    <div class="container">

        <!-- 좌측 사이드바 -->
        <jsp:include page="include/sidebar.jsp" />

        <!-- 우측 본문 영역 -->
        <div class="main-content" id="mainContent">
            <jsp:include page="${bodyPage}" />
        </div>
    </div>

    <!-- 하단 푸터 -->
    <jsp:include page="include/footer.jsp" />

    <!-- 공통 JS -->
    <script src="${pageContext.request.contextPath}/js/index.js" defer></script>

</c:if>

<!-- 로그인되지 않은 경우 로그인 페이지로 리다이렉트 -->
<c:if test="${empty sessionScope.loginUser}">
    <c:redirect url="/login" />
</c:if>

</body>
</html>
