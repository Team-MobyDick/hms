<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 25. 6. 19.
  Time: 오후 3:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">

<html>

    <head>
        <title>405 - 메서드 불허용</title>
    </head>

    <body>
        <div class="error-container">
            <h1>이 메서드는 허용되지 않습니다. 😔</h1>
            <p>잘못된 HTTP 메서드를 사용한 요청입니다.</p>
            <a href="/">홈으로 돌아가기</a>
        </div>
    </body>

</html>
