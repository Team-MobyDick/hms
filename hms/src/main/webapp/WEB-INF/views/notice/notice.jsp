<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>공지사항</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- contextPath 전역 변수 선언 -->
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css"/>
    <script src="${pageContext.request.contextPath}/js/notice.js" defer></script>
</head>
<body>

<h1>공지사항</h1>
<button id="add_btn">공지사항 등록</button>

<!-- 등록 폼 (초기에 숨김 처리) -->
<div id="noticeFormWrapper" style="display: none; margin-top: 20px;">
    <form id="noticeForm" method="post" action="${pageContext.request.contextPath}/notice/register">
        <label>제목</label><br>
        <input type="text" name="noticeTitle" style="width: 100%; padding: 8px; margin-bottom: 10px;" required><br>

        <label>내용</label><br>
        <textarea name="noticeContent" class="content-textarea" required></textarea><br>

        <button type="submit" class="action-btn">등록</button>
        <button type="button" id="formCancelBtn" class="action-btn">취소</button>
    </form>
</div>

<table border="1" width="100%">
    <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="notice" items="${noticeList}">
            <tr class="ann-row"
                data-notice-id="${notice.noticeId}"
                data-title="${fn:escapeXml(notice.noticeTitle)}"
                data-writer="${notice.emplId}"
                data-date="<fmt:formatDate value='${notice.createdDate}' pattern='yyyy-MM-dd' />"
                data-content="${fn:escapeXml(notice.noticeContent)}">
                <td>${notice.noticeId}</td>
                <td>${notice.noticeTitle}</td>
                <td>${notice.emplId}</td>
                <td><fmt:formatDate value="${notice.createdDate}" pattern="yyyy-MM-dd" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- 페이지네이션 포함 (선택 사항) -->
<jsp:include page="/WEB-INF/views/include/pagination.jsp" />



</body>
</html>
