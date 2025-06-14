<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>직원 목록</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
        }
        .btn {
            padding: 6px 12px;
            margin-right: 4px;
            background-color: #ccc;
            color: black;
            border: none;
            text-decoration: none;
            cursor: pointer;
            border-radius: 4px;
        }
        .btn:hover {
            background-color: #ccc;
        }
        .role-buttons {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>직원 목록</h2>

<!-- 직원 등록 버튼 -->
<div style="text-align: right; margin-bottom: 10px;">
    <a href="${pageContext.request.contextPath}/employee/new" class="btn">직원 등록</a>
</div>

<!-- 직원 리스트 테이블 -->
<table>
    <thead>
        <tr>
            <th>이름</th>
            <th>부서</th>
            <th>직책</th>
            <th>QR 출력</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/employee/${emp.emplId}">
                        ${emp.emplName}
                    </a>
                </td>
                <td>${emp.emplDept}</td>
                <td>${emp.emplGrade}</td>
                <td>
                    <button class="btn" onclick="alert('QR 출력')">QR 출력</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
