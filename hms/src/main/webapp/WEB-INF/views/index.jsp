<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Hello JSP</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sample.css">
</head>
<body>

    <%-- 공통 헤더 --%>
    <jsp:include page="include/header.jsp" />

    <%-- 공통 사이드바 --%>
    <jsp:include page="include/sidebar.jsp" />

    <%-- 동적 바디 --%>
    <jsp:include page="${bodyPage}" />

    <%-- 공통 푸터 --%>
    <jsp:include page="include/footer.jsp" />
</body>
</html>
