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
                        <%-- dashboardData에서 오늘의 사원 리스트를 가져옴 --%>
                        <c:when test="${not empty dashboardData.employeesOfTheDay}">
                            <c:forEach var="emp" items="${dashboardData.employeesOfTheDay}">
                                <div class="employee-item" onclick="location.href='${pageContext.request.contextPath}/employee/list';">
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
                                        <div class="name">${emp.emplName}</div>
                                        <%-- emplGrade 대신 emplGradeName(직책명)을 표시 --%>
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
                        <%-- dashboardData에서 주간 스케줄 리스트를 가져옴 --%>
                        <c:when test="${not empty dashboardData.weeklySchedules}">
                            <c:forEach var="schedule" items="${dashboardData.weeklySchedules}">
                                <li class="schedule-item"> <a href="${pageContext.request.contextPath}/schedule/list">
                                        <div class="schedule-content">
                                            <span class="schedule-date"><fmt:formatDate value="${schedule.scheDate}" pattern="MM/dd (E)" /></span>
                                            <%-- emplId 대신 emplName(사원명)을 표시 --%>
                                            <span class="schedule-info">[${schedule.emplName}] ${schedule.scheShift}</span>
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
                <h2>공지사항</h2>
                <ul>
                    <c:choose>
                        <%-- dashboardData에서 공지사항 리스트를 가져옴 --%>
                        <c:when test="${not empty dashboardData.latestNotices}">
                            <c:forEach var="notice" items="${dashboardData.latestNotices}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/notice/list">
                                        <span class="list-item-title">${notice.noticeTitle}</span>
                                        <span class="list-item-date"><fmt:formatDate value="${notice.createdDate}" pattern="yyyy.MM.dd" /></span>
                                    </a>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-data">최신 공지사항이 없습니다.</p>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </body>

</html>