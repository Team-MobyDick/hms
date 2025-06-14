<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>
        <c:choose>
            <c:when test="${employee.emplId != null}">직원 수정</c:when>
            <c:otherwise>직원 등록</c:otherwise>
        </c:choose>
    </title>
    <style>
        table {
            width: 60%;
            margin: auto;
            border-collapse: collapse;
        }
        td {
            padding: 10px;
        }
        input[type="text"], textarea, select {
            width: 100%;
            padding: 8px;
        }
        .form-buttons {
            text-align: center;
            margin-top: 20px;
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
        .photo-box {
            width: 150px;
            height: 180px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            text-align: center;
            line-height: 180px;
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>

<h2 style="text-align: center;">
    <c:choose>
        <c:when test="${employee.emplId != null}">직원 수정</c:when>
        <c:otherwise>직원 등록</c:otherwise>
    </c:choose>
</h2>

<c:choose>
    <c:when test="${employee.emplId != null}">
        <form action="${pageContext.request.contextPath}/employee/update" method="post">
    </c:when>
    <c:otherwise>
        <form action="${pageContext.request.contextPath}/employee/save" method="post">
    </c:otherwise>
</c:choose>

    <table>
        <tr>
            <td rowspan="4" style="width: 180px;">
                <div class="photo-box">
                    사진 영역
                </div>
                <input type="file" name="emplPhoto" />
            </td>
            <td>이름</td>
            <td><input type="text" name="emplName" value="${employee.emplName}" required /></td>
        </tr>
        <tr>
            <td>ID 번호</td>
            <td>
                <c:choose>
                    <c:when test="${employee.emplId != null}">
                        <input type="text" name="emplId" value="${employee.emplId}" readonly />
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="emplId" required />
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>전화번호</td>
            <td><input type="text" name="emplPhone" value="${employee.emplPhone}" /></td>
        </tr>
        <tr>
            <td colspan="1">부서</td>
            <td colspan="2">
                <select name="emplDept" required>
                    <option value="">-- 부서 선택 --</option>
                    <option value="청소관리" <c:if test="${employee.emplDept == '청소관리'}">selected</c:if>>청소관리</option>
                    <option value="시설/소방관리" <c:if test="${employee.emplDept == '시설/소방관리'}">selected</c:if>>시설/소방관리</option>
                    <option value="안내" <c:if test="${employee.emplDept == '안내'}">selected</c:if>>안내</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="1">직책</td>
            <td colspan="2">
                <select name="emplGrade" required>
                    <option value="">-- 직책 선택 --</option>
                    <option value="팀장" <c:if test="${employee.emplGrade == '팀장'}">selected</c:if>>팀장</option>
                    <option value="사원" <c:if test="${employee.emplGrade == '직원'}">selected</c:if>>직원</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>메모</td>
            <td colspan="2">
                <textarea name="emplNotes" rows="4">${employee.emplNotes}</textarea>
            </td>
        </tr>
    </table>

    <div class="form-buttons">
        <input type="submit" value="<c:choose><c:when test='${employee.emplId != null}'>수정</c:when><c:otherwise>등록</c:otherwise></c:choose>" class="btn" />
        <a href="${pageContext.request.contextPath}/employee/list" class="btn">취소</a>
    </div>

</form>

</body>
</html>
