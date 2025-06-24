<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}"/>

<!DOCTYPE html>
<html>
<head>
    <title>스케줄 관리</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/locales-all.js"></script>
</head>

<body>

    <!-- 조건부 등록 버튼 -->
    <c:if test="${userRole eq 'GR_01' or userRole eq 'GR_02'}">
        <div style="text-align: center; margin-bottom: 10px;">
            <button id="btnOpenModal">스케줄 등록</button>
        </div>
    </c:if>

    <!-- 캘린더 영역 -->
    <div id='calendar'></div>

    <!-- 달력 아래 스케줄 상세 출력 영역 -->
    <div id="scheduleDetailBox">
        <h3>스케줄 상세</h3>
        <div id="scheduleDetailContent">날짜를 클릭하면 스케줄이 표시됩니다.</div>
        <c:if test="${userRole eq 'GR_01' or userRole eq 'GR_02'}">
            <button id="btnDeleteSchedule" style="display: none;">선택된 스케줄 삭제</button>
            <input type="hidden" id="selectedScheId"/>
        </c:if>
    </div>

    <!-- 등록 모달 -->
    <div id="scheduleModal" style="display:none;">
        <form id="scheduleForm">
            <label>부서</label>
            <input type="text" id="deptName" readonly/>

            <label>직원명</label>
            <select id="emplSelect" name="emplId" onchange="updateDeptName()"></select>

            <label>교대</label>
            <select name="scheShift" id="scheShift">
                <option value="SH_01">주간</option>
                <option value="SH_02">야간</option>
            </select>

            <label>업무일자</label>
            <input type="date" id="startDate" required/>
            ~
            <input type="date" id="endDate" required/>

            <div style="margin-top: 15px;">
                <button type="submit">등록하기</button>
                <button type="button" onclick="$('#scheduleModal').hide()">취소하기</button>
            </div>
        </form>
    </div>


    <script src="${pageContext.request.contextPath}/js/schedule.js" defer></script>

</body>
</html>
