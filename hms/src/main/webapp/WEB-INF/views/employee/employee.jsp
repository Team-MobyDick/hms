<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />

<!DOCTYPE html>

<html>

    <head>
        <title>직원 관리</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/employee.css"/>
    </head>

    <body>

        <h2>직원 관리</h2>

        <button id="add_btn">직원 등록</button>

        <table>

            <thead>
                <tr>
                    <th>직원 ID</th>
                    <th>이름</th>
                    <th>부서</th>
                    <th>직책</th>
                    <th>전화번호</th>
                    <th>메모</th>
                    <th>사진</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${not empty employeeList}">
                        <c:forEach var="emp" items="${employeeList}">
                            <tr class="employee-row"
                                data-id="${emp.emplId}"
                                data-name="${emp.emplName}"
                                data-dept="${emp.emplDept}"
                                data-grade="${emp.emplGrade}">
                                <td data-label="직원 ID">${emp.emplId}</td>
                                <td data-label="이름">${emp.emplName}</td>
                                <td data-label="부서">
                                    <c:choose>
                                        <c:when test="${emp.emplDept eq 'DP_01'}">하우스키핑</c:when>
                                        <c:when test="${emp.emplDept eq 'DP_02'}">시설관리</c:when>
                                        <c:when test="${emp.emplDept eq 'DP_03'}">프론트</c:when>
                                        <c:otherwise>미정</c:otherwise>
                                    </c:choose>
                                </td>
                                <td data-label="직책">
                                    <c:choose>
                                        <c:when test="${emp.emplGrade eq 'GR_01'}">총지배인</c:when>
                                        <c:when test="${emp.emplGrade eq 'GR_02'}">팀장</c:when>
                                        <c:when test="${emp.emplGrade eq 'GR_03'}">일반</c:when>
                                        <c:otherwise>미정</c:otherwise>
                                    </c:choose>
                                </td>
                                <td data-label="전화번호">${emp.emplPhone}</td>
                                <td data-label="메모">${emp.emplNotes}</td>
                                <td data-label="사진">
                                    <c:if test="${not empty emp.photoName}">
                                        <img src="${pageContext.request.contextPath}/images/${emp.photoPath}/${emp.photoName}" alt="사진" width="60"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="6">직원 목록이 비어 있습니다.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>

        </table>

        <form id="newEmployeeForm" hidden>
            <h3>새 직원 등록</h3>
            <table>
                <tr>
                    <th>ID</th>
                    <th>이름</th>
                    <th>부서</th>
                    <th>직책</th>
                    <th>전화번호</th>
                    <th>메모</th>
                    <th>동작</th>
                </tr>
                <tr>
                    <td><input type="text" name="emplId" placeholder="ID" required></td>
                    <td><input type="text" name="emplName" placeholder="이름" required></td>
                    <td>
                        <select name="emplDept" required>
                            <option value="">부서 선택</option>
                            <option value="DP_01">하우스키핑</option>
                            <option value="DP_02">시설관리</option>
                            <option value="DP_03">프론트</option>
                        </select>
                    </td>
                    <td>
                        <select name="emplGrade" required>
                            <option value="">직책 선택</option>
                            <option value="GR_01">총지배인</option>
                            <option value="GR_02">팀장</option>
                            <option value="GR_03">일반</option>
                        </select>
                    </td>
                    <td><input type="text" name="emplPhone" placeholder="연락처"></td>
                    <td><textarea name="emplNotes" placeholder="메모"></textarea></td>
                    <td>
                        <button type="submit">등록</button>
                        <button type="button" id="add_cancel">취소</button>
                    </td>
                </tr>
            </table>
        </form>

        <%-- !!! 중요: employee.js 로드 전에 contextPath와 userRole 변수 정의 !!! --%>
        <script>
            // JavaScript 변수로 JSP에서 평가된 값을 할당
            var contextPath = "${pageContext.request.contextPath}";
            // userRole을 다른 이름으로 명확히 구분
            var userRoleJs = "${userRole}";
        </script>
        <script src="${pageContext.request.contextPath}/js/employee.js" defer></script>

    </body>
</html>