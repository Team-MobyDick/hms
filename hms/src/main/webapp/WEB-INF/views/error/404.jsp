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
        <title>404 - 페이지를 찾을 수 없습니다.</title>
    </head>

    <body>
        <div class="error-container">
            <h1>페이지를 찾을 수 없습니다. 😞</h1>
            <p>입력한 주소가 잘못되었거나<br>페이지가 삭제되었을 수 있습니다.</p>
            <a href="/">홈으로 돌아가기</a>
        </div>
    </body>

</html>
