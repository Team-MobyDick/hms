<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="setDate" value="${now}" />
<fmt:formatDate value="${setDate}" pattern="yyyy-MM-dd" var="formattedDate" />

<!DOCTYPE html>
<html>
<head>
    <title>업무</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/work.css"/>

</head>
<body data-role="${userRole}">
<input type="hidden" id="serverDate" value="${formattedDate}" />
    <h2>업무 목록</h2>

    <!-- 날짜 영역 -->
    <div class="date-container">
        <button id="prevBtn">←</button>

        <div>
            <span id="dateText">${formattedDate}</span>
            <input type="date" id="datePicker" class="hidden">
        </div>

        <button id="nextBtn">→</button>
    </div>

    <!-- 라디오버튼 영역 -->
    <div class="mode-container" style="margin-top: 8px;">
        <label style="margin-right: 12px;">
            <input type="radio" name="mode" value="ALL"
               <c:if test="${userRole == 'GR_01' || userRole == 'GR_02'}">checked</c:if>> 전체업무
        </label>
        <label>
            <input type="radio" name="mode" value="MINE"
               <c:if test="${userRole != 'GR_01' && userRole != 'GR_02'}">checked</c:if>> 내업무
        </label>
    </div>

    <c:if test="${userRole == 'GR_01'}">
        <button id="add_btn_M">주 업무 등록</button>
    </c:if>

    <!-- 주 업무 등록 폼 -->
    <form id="newWorkMForm" hidden>
        <table>
            <tr>
                <th>업무명</th>
                <th>부서</th>
                <th>중요도</th>
                <th>업무내용</th>
                <th>동작</th>
            </tr>
            <tr>
                <td><input id="workMName" type="text" name="workMName" placeholder="업무명" maxlength="50"></td>
                <td>
                    <select name="workMDept" id="workMDept">
                    </select>
                </td>
                <td>
                    <select name="workMImpo" id="workMImpo">
                    </select>
                </td>
                <td>
                    <textarea name="workMContext" id="workMContext"></textarea>
                </td>
                <td>
                    <button type="submit">등록</button>
                    <button type="button" id="add_cancle_M">취소</button>
                </td>
            </tr>
        </table>
    </form>

    <table>
        <c:choose>
            <c:when test="${not empty workMList}">
                <thead id="workTableHead">
                    <tr>
                        <th>부서</th>
                        <th>업무명</th>
                        <th>담당자</th>
                        <th>중요도</th>
                        <th>업무배분/상세</th>
                    </tr>
                </thead>
                <tbody id="workTableBody">
                    <c:forEach var="workM" items="${workMList}">
                        <tr class="workM-row"
                            data-workm-name="${workM.workMName}"
                            data-workm-dept="${workM.workMDept}"
                            data-workm-deptname="${codeMap[workM.workMDept]}"
                            data-workm-impo="${workM.workMImpo}"
                            data-workm-imponame="${codeMap[workM.workMImpo]}"
                            data-workm-id="${workM.workMId}"
                            data-userrole="${userRole}"
                            data-date="${formattedDate}"
                            data-workm-context="${workM.workMContext}">
                            <td data-label="부서">${codeMap[workM.workMDept]}</td>
                            <td data-label="업무명">${workM.workMName}</td>
                            <td data-label="담당자"></td>
                            <td data-label="중요도">${codeMap[workM.workMImpo]}</td>
                            <td data-label="상세/업무배분">
                            <c:if test="${userRole == 'GR_01'||userRole == 'GR_02'}">
                            <button class="add_btn_D" >업무 배분</button>
                            </c:if>
                            <button class="detail_btn_M">업무 상세</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:when>
            <c:when test="${not empty workDList}">
                <thead id="workTableHead">
                    <tr>
                        <th>업무명</th>
                        <th>담당자</th>
                        <th>중요도</th>
                        <th>업무배분/상세</th>
                    </tr>
                </thead>
                <tbody id="workTableBody">
                    <c:forEach var="workD" items="${workDList}">
                        <tr class="workD-row"
                            data-parent="${workD.workMId}"
                            data-workd-id="${workD.workDId}">
                            <td data-label="업무명">${workD.workDName}</td>
                            <td data-label="담당자">${workD.emplName}</td>
                            <td data-label="중요도">${codeMap[workD.workDImpo]}</td>
                            <td data-label="업무상세"><button id="detail_btn_D">업무 상세</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:when>
            <c:otherwise>
                <thead id="workTableHead">
                    <tr>
                        <th>부서</th>
                        <th>업무명</th>
                        <th>담당자</th>
                        <th>중요도</th>
                        <th>업무배분/상세</th>
                    </tr>
                </thead>
                <tbody id="workTableBody">
                    <tr><td colspan="6">업무 목록이 비어 있습니다.</td></tr>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>

    <!-- 주 업무 상세 모달 폼 -->
    <div class="workMDetail">
        <form class="workMDetailForm">
            <input type="hidden" name="workMId" />
            <label>업무명
                <input type="text" name="workMName"
                            ${userRole != 'GR_01' ? 'readonly' : ''} />
            </label>
            <label>부서
                <c:choose>
                    <c:when test="${userRole != 'GR_01'}">
                        <input type="text" name="workMDept" readonly>
                    </c:when>
                    <c:otherwise>
                        <select name="workMDept">
                        </select>
                    </c:otherwise>
                </c:choose>
            </label>
            <label>중요도
                <c:choose>
                    <c:when test="${userRole != 'GR_01'}">
                        <input type="text" name="workMImpo" readonly>
                    </c:when>
                    <c:otherwise>
                        <select name="workMImpo">
                    </c:otherwise>
                </c:choose>
                </select>
            </label>
            <label>업무내용
                <textarea name="workMContext"
                            ${userRole != 'GR_01' ? 'readonly' : ''}>
                </textarea>
            </label>
            <c:if test="${userRole == 'GR_01'}">
                <button type="submit">수정</button>
                <button type="button" class="delete_btn_M">삭제</button>
            </c:if>
            <button type="button" class="cancle_btn_M">취소</button>
        </form>
    </div>
</body>
<script>
    const contextPath = '${pageContext.request.contextPath}';
    var userRole = '${userRole}';
</script>
<script src="/js/work.js"></script>
</html>
