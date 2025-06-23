
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>공지 상세보기</title></head>
<body>
<h2>${notice.noticeTitle}</h2>
<p><strong>작성자:</strong> ${notice.emplId}</p>
<p>${notice.noticeContent}</p>
<a href="${pageContext.request.contextPath}/notice/list">목록으로</a>
</body>
</html>
