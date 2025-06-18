<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>공지사항</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css"/>
</head>
<body>
<h1>공지사항</h1>

<button id="add_btn">공지사항 등록</button>

<script>
    // 등록 버튼 클릭 시 폼 이동
    document.getElementById("add_btn").onclick = function () {
        location.href = '${pageContext.request.contextPath}/notice/form';
    };
</script>

<table border="1" width="100%">
    <thead>
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>내용</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="notice" items="${noticeList}">
        <tr class="ann-row">
            <td>${notice.noticeId}</td>
            <td>${notice.noticeTitle}</td>
            <td>${notice.emplId}</td>
            <td>
                <fmt:formatDate value="${notice.createdDate}" pattern="yyyy-MM-dd" />
            </td>
            <td>${notice.noticeContent}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- 페이지네이션 포함 (선택 사항) -->
<jsp:include page="/WEB-INF/views/include/pagination.jsp" />

<!-- 공지 더블클릭 수정 이동 스크립트 -->
<script>
    $(document).ready(function () {
        $("tbody").on("dblclick", "tr.ann-row", function () {
            const noticeId = $(this).find("td:first").text().trim();
            if (noticeId) {
                window.location.href = "${pageContext.request.contextPath}/notice/update/" + noticeId;
            }
        });
    });
</script>

</body>
</html>
