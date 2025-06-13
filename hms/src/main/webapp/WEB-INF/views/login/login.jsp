<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <h1>login</h1>

    <form action="/login/login" method="POST">

        <label for="empl_id">ID</label><br>
        <input type="text" id="empl_id" name="empl_id"><br><br>

        <button type="submit">로그인</button>

    </form>

</body>
</html>
