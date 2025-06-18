<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>HMS 대시보드</title>
    <style>
        .dashboard-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            max-width: 1200px;
            margin: 20px auto;
        }
        .dashboard-card {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 25px;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            border: 1px solid #d4edda;
        }
        .dashboard-card h2 {
            margin-top: 0;
            color: #28a745;
            font-size: 24px;
            border-bottom: 2px solid #28a745;
            padding-bottom: 10px;
            margin-bottom: 15px;
            text-align: center;
        }
        .dashboard-card ul {
            list-style: none;
            padding: 0;
            margin: 0;
            flex-grow: 1;
        }
        .dashboard-card ul li {
            padding: 10px 0;
            border-bottom: 1px solid #e2f0d4;
            cursor: pointer;
        }
        .dashboard-card ul li:last-child {
            border-bottom: none;
        }
        .dashboard-card ul li a {
            text-decoration: none;
            color: #333;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: color 0.2s ease;
        }
        .dashboard-card ul li a:hover {
            color: #218838; /* employee.css 의 더 진한 초록색 */
        }
        .dashboard-card .empty-data {
            text-align: center;
            color: #888;
            padding: 20px;
        }

        /* 오늘의 사원 카드 스타일 */
        .employee-of-day-list {
            display: flex;
            flex-direction: column;
            gap: 15px;
            align-items: center;
            flex-grow: 1;
        }
        .employee-item {
            display: flex;
            align-items: center;
            gap: 15px;
            background-color: #e9f5e9; /* employee.css 의 연한 녹색 배경 */
            padding: 10px 15px;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08);
            cursor: pointer;
            transition: background-color 0.2s ease;
        }
        .employee-item:hover {
            background-color: #d4edda; /* employee.css 의 더 진한 연두색 */
        }
        .employee-item img {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #28a745; /* employee.css 의 초록색 테두리 */
        }
        .employee-info {
            flex-grow: 1;
            font-size: 1.1em;
            color: #333;
        }
        .employee-info .name {
            font-weight: bold;
        }
        .employee-info .grade {
            font-size: 0.9em;
            color: #666;
        }

        /* 공지사항/스케줄 항목 스타일 */
        .list-item-title {
            flex-grow: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .list-item-date {
            font-size: 0.9em;
            color: #777;
            flex-shrink: 0;
            margin-left: 10px;
        }
    </style>
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
                            <li>
                                <a href="${pageContext.request.contextPath}/schedule/list">
                                    <span class="list-item-title">[${schedule.emplId}] ${schedule.scheShift}</span>
                                    <%-- SCHE_DATE가 VARCHAR2(14)이므로 SimpleDateFormat으로 파싱하여 출력 --%>
                                    <c:set var="scheDateStr" value="${schedule.scheDate}" />
                                    <fmt:parseDate value="${scheDateStr.substring(0,8)}" pattern="yyyyMMdd" var="parsedDate" />
                                    <span class="list-item-date"><fmt:formatDate value="${parsedDate}" pattern="MM/dd (E)" /></span>
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