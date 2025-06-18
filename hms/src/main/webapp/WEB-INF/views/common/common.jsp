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
                        <c:when test="${not empty employeesOfTheDay}">
                            <c:forEach var="emp" items="${employeesOfTheDay}">
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
                                        <div class="grade">(${emp.emplGrade})</div>
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
                        <c:when test="${not empty weeklySchedules}">
                            <c:forEach var="schedule" items="${weeklySchedules}">
                                <li class="schedule-item"> <a href="${pageContext.request.contextPath}/schedule/list">
                                        <div class="schedule-content">
                                            <span class="schedule-date"><fmt:formatDate value="${schedule.scheDate}" pattern="MM/dd (E)" /></span>
                                            <span class="schedule-info">[${schedule.emplId}] ${schedule.scheShift}</span>
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
                        <c:when test="${not empty latestNotices}">
                            <c:forEach var="notice" items="${latestNotices}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/anno/list">
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