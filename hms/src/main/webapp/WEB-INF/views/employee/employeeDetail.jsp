<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>직원 상세 정보</title>
    <style>
        table {
            width: 50%;
            margin: auto;
            border-collapse: collapse;
        }
        td {
            padding: 8px;
            border: 1px solid #ccc;
        }
        .btn {
            padding: 10px 16px;
            margin: 5px;
            border: none;
            background-color: #ccc;
            color: black;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
    </style>
</head>
<body>

<h2 style="text-align: center;">직원 상세 정보</h2>

<table>
    <tr>
        <td>이름</td>
        <td>${employee.emplName}</td>
    </tr>
    <tr>
        <td>ID</td>
        <td>${employee.emplId}</td>
    </tr>
    <tr>
        <td>전화번호</td>
        <td>${employee.emplPhone}</td>
    </tr>
    <tr>
        <td>부서</td>
        <td>${employee.emplDept}</td>
    </tr>
    <tr>
        <td>직책</td>
        <td>${employee.emplGrade}</td>
    </tr>
    <tr>
        <td>메모</td>
        <td>${employee.emplNotes}</td>
    </tr>
</table>

<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/employee/list" class="btn">목록으로</a>
</div>

</body>
</html>
