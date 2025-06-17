<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="setDate" value="${now}" />

<!DOCTYPE html>
<html>
<head>
    <title>업무</title>
</head>
<body data-role="${userRole}">
    <h2>업무 목록</h2>
    <button id="add_btn">업무 등록</button>

    <table>
        <thead>
            <tr>
                <th> </th>
                <th>업무명</th>
                <th>부서</th>
                <th>담당자</th>
                <th>중요도</th>
                <th>업무일자/배분</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty workMList}">
                    <c:forEach var="workM" items="${workMList}">
                        <tr class="workM-row"
                            data-workM="${workM.workMName}"
                            data-dept="${workM.workMDept}"
                            data-workM-id="${workM.workMId}"
                            data-createdDate="${workM.createdDate}"
                            data-createdId="${workM.createdId}"
                            data-updatedDate="${workM.updatedDate}"
                            data-updatedId="${workM.updatedId}">
                            <td data-label="level">"주 업무"</td>
                            <td data-label="업무명">${workM.workMName}</td>
                            <td data-label="부서">${workM.workMDept}</td>
                            <td data-label="담당자"></td>
                            <td data-label="중요도">${workM.workMImpo}</td>
                            <td data-label="업무배분"><button>업무배분</button></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="6">업무 목록이 비어 있습니다.</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
