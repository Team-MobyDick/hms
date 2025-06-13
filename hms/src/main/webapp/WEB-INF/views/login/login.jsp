<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>

 <!-- login.jsp -->

<html>

    <head>
        <title>로그인</title>
    </head>

    <body>

        <div class="login-container">
            <h2>로그인</h2>

            <!-- 에러 메시지 출력 -->
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="POST">

                <div class="form-group">
                    <label for="emplId">사번 (ID)</label><br>
                    <input type="text" id="emplId" name="emplId" required>
                </div>

                <div class="form-group">
                    <button type="submit">로그인</button>
                </div>

            </form>
        </div>

    </body>

</html>
