<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />
<c:set var="userDept" value="${sessionScope.loginUser.emplDept}" />

<!DOCTYPE html>
<html>
    <head>
        <title>직원 관리</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>
    </head>
    <body>

    <h2>직원 관리</h2>

    <div class="controls-container">
        <button id="add_btn">직원 등록</button>

        <div class="sort-buttons">
            <button id="sort_by_dept" class="sort-btn" data-sort-order="emplDept_ASC">부서별 정렬</button>
            <button id="sort_by_grade" class="sort-btn" data-sort-order="emplGrade_ASC">직책별 정렬</button>
        </div>
    </div>

    <form id="newEmployeeForm" hidden>
        <table>
            <tr>
                <th>이름</th>
                <th>부서</th>
                <th>직책</th>
                <th>전화번호</th>
                <th>메모</th>
                <th>동작</th>
            </tr>
            <tr>
                <td><input type="text" name="emplName" placeholder="이름" maxlength="50" required></td>
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
                        <c:if test="${userRole == 'GR_01'}">
                            <option value="GR_01">총지배인</option>
                        </c:if>
                        <option value="GR_02">팀장</option>
                        <option value="GR_03">일반</option>
                    </select>
                </td>
                <td><input type="tel" name="emplPhone" placeholder="연락처(- 없이 적어주세요)" maxlength="11" pattern="[0-9]{2,3}[0-9]{3,4}[0-9]{4}"></td>
                <td><textarea name="emplNotes" placeholder="메모" maxlength="2000"></textarea></td>
                <td>
                    <button type="submit">등록</button>
                    <button type="button" id="add_cancel">취소</button>
                </td>
            </tr>
        </table>
    </form>

    <table>
        <thead>
            <tr>
                <th>직원 ID</th>
                <th>이름</th>
                <th>부서</th>
                <th>직책</th>
                <th>전화번호</th>
                <th>메모</th>
                <th>등록일</th>
                <th>사진</th>
                <th>QR 출력</th>
                <th>퇴사 여부</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty employeeList}">
                    <c:forEach var="emp" items="${employeeList}">
                        <tr class="employee-row grade-${emp.emplGrade}"
                            data-id="${emp.emplId}"
                            data-name="${emp.emplName}"
                            data-dept="${emp.emplDept}"
                            data-grade="${emp.emplGrade}"
                            data-phone="${emp.emplPhone}"
                            data-note="${emp.emplNotes}"
                            data-created-date="<fmt:formatDate value='${emp.createdDate}' pattern='yyyy-MM-dd'/>"
                            data-photo-name="${emp.photoName}"
                            data-photo-path="${emp.photoPath}"
                            data-retired-yn="${emp.retiredYn}"
                        >
                            <td>${emp.emplId}</td>
                            <td>${emp.emplName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${emp.emplDept eq 'DP_01'}">하우스키핑</c:when>
                                    <c:when test="${emp.emplDept eq 'DP_02'}">시설관리</c:when>
                                    <c:when test="${emp.emplDept eq 'DP_03'}">프론트</c:when>
                                    <c:otherwise>미정</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${emp.emplGrade eq 'GR_01'}">
                                        <span style="color: #4B0082; font-weight: bold;">총지배인</span>
                                    </c:when>
                                    <c:when test="${emp.emplGrade eq 'GR_02'}">
                                        <span style="font-weight: bold;">팀장</span>
                                     </c:when>
                                    <c:when test="${emp.emplGrade eq 'GR_03'}">
                                        일반
                                    </c:when>
                                    <c:otherwise>미정</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${emp.emplPhone}</td>
                            <td>${emp.emplNotes}</td>
                            <td><fmt:formatDate value="${emp.createdDate}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <div class="photo-container">
                                    <c:if test="${not empty emp.photoName}">
                                        <img src="/employee_photos/${emp.photoName}" alt="사진"/>
                                    </c:if>
                                </div>
                            </td>
                            <td><button class="printQR">출력하기</button></td>
                            <td>
                                <c:choose>
                                    <c:when test="${emp.retiredYn eq 'Y'}">
                                        <span style="color: red; font-weight: bold;">퇴사</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>재직</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="10">직원 목록이 비어 있습니다.</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <jsp:include page="/WEB-INF/views/include/pagination.jsp" />

    <div class="modal">
        <div class="modal_popup">
            <h3>직원 QR 코드</h3>
            <div id="qrcode"></div>
            <button type="button" class="print_btn">인쇄하기</button>
            <button type="button" class="close_btn">닫기</button>
        </div>
    </div>

    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var userRoleJs = "${userRole}";
        var userDeptJs = "${userDept}";
    </script>

    <script src="${pageContext.request.contextPath}/js/employee.js" defer></script>

    </body>
</html>