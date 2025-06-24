<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>HMS 대시보드</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css"/>
    </head>

    <body>
        <div class="dashboard-container">
            <div class="dashboard-card">

                <h2>오늘의 사원</h2>

                <div class="employee-of-day-list">
                    <c:choose>
                        <c:when test="${not empty dashboardData.employeesOfTheDay}">
                            <c:forEach var="emp" items="${dashboardData.employeesOfTheDay}">
                                <div class="employee-item" data-empl-grade="${emp.emplGrade}" onclick="return handleEmployeeClick(this);">

                                    <c:set var="photoSrc">
                                        <c:choose>
                                            <c:when test="${not empty emp.photoName && not empty emp.photoPath}">
                                                ${pageContext.request.contextPath}/${emp.photoPath}/${emp.photoName}
                                            </c:when>
                                            <c:otherwise>
                                                ${pageContext.request.contextPath}/static/images/default_profile.png
                                            </c:otherwise>
                                        </c:choose>
                                    </c:set>

                                    <img src="${photoSrc}" alt="${emp.emplName} 사진">

                                    <div class="employee-info">
                                        <div class="name">
                                            ${emp.emplName}
                                            <c:if test="${emp.coffeeWinnerYn == 'Y'}">
                                                <span class="coffee-winner-badge">☕ 오늘 커피 사는 사람!</span>
                                            </c:if>
                                        </div>
                                        <div class="grade">(${emp.emplGradeName})</div>
                                    </div>

                                </div>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <p class="empty-data">오늘의 사원 정보가 없습니다.</p>
                        </c:otherwise>

                    </c:choose>
                </div>
            </div>

            <div class="dashboard-card">

                <h2>주간 스케줄</h2>

                <ul>
                    <c:choose>
                        <c:when test="${not empty dashboardData.weeklySchedules}">
                            <c:forEach var="schedule" items="${dashboardData.weeklySchedules}">

                                <li class="schedule-item">
                                    <a href="${pageContext.request.contextPath}/schedule/list">
                                        <div class="schedule-content">
                                            <span class="schedule-date"><fmt:formatDate value="${schedule.scheDate}" pattern="MM/dd (E)" /></span>
                                            <span class="schedule-info">
                                                <span class="schedule-empl-name">${schedule.emplName}</span>  <c:choose>
                                                    <c:when test="${schedule.scheShift == 'SH_01'}">
                                                        <span class="shift-day">주간</span>
                                                     </c:when>
                                                    <c:when test="${schedule.scheShift == 'SH_02'}">
                                                        <span class="shift-night">야간</span>
                                                    </c:when>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </a>
                                </li>

                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <p class="empty-data">이번 주 스케줄이 없습니다.</p>
                        </c:otherwise>

                    </c:choose>
                </ul>
            </div>

            <div class="dashboard-card">
                <h2>오늘 할 일</h2>

                <c:choose>
                    <c:when test="${not empty dashboardData.todayWorks}">
                        <table class="activity-table">
                            <thead>
                                <tr>
                                    <th>날짜</th>
                                    <th>중요도</th>
                                    <th>업무명</th>
                                    <th>내용</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="work" items="${dashboardData.todayWorks}">

                                    <tr>
                                        <td>${work.workDDate}</td>
                                        <td>
                                            <span class='activity-badge
                                                ${work.workDImpo == "IM_03" ? "text-danger": work.workDImpo == "IM_02" ? "text-warning": "text-success" }'>
                                                <c:choose>
                                                    <c:when test="${work.workDImpo == 'IM_03'}">높음</c:when>
                                                    <c:when test="${work.workDImpo == 'IM_02'}">보통</c:when>
                                                    <c:otherwise>낮음</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </td>
                                        <td><strong>${work.workDName}</strong></td>
                                        <td><p>${work.workDContext}</p></td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <p class="empty-data">오늘 할 일이 없습니다.</p>
                    </c:otherwise>

                </c:choose>
            </div>

            <div class="dashboard-card">
                <h2>공지사항</h2>

                <c:choose>
                    <c:when test="${not empty dashboardData.latestNotices}">
                        <table class="notice-table">
                            <thead>
                                <tr>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>날짜</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="notice" items="${dashboardData.latestNotices}">

                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/notice/list"><strong>${notice.noticeTitle}</strong></a></td>
                                        <td>${notice.emplName}</td>
                                        <td><fmt:formatDate value="${notice.createdDate}" pattern="yyyy.MM.dd" /></td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <p class="empty-data">최신 공지사항이 없습니다.</p>
                    </c:otherwise>

                </c:choose>
            </div>

        </div>

        <script>
            function handleEmployeeClick() {
                const loggedInUserGrade = '${loginUser.emplGrade}';

                console.log("현재 로그인한 사용자 등급:", loggedInUserGrade);

                if (loggedInUserGrade === 'GR_01' || loggedInUserGrade === 'GR_02') {
                    location.href = '${pageContext.request.contextPath}/employee/list';
                    return true;
                } else if (loggedInUserGrade === 'GR_03') {
                    alert('일반 등급 직원은 직원 관리 페이지로 이동할 수 없습니다.');
                    return false;
                }
            }
        </script>
    </body>

</html>