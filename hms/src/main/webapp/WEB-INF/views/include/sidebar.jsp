<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <!-- sidebar.jsp -->

<c:if test="${not empty sessionScope.loginUser}">

    <c:set var="grade" value="${sessionScope.loginUser.emplGrade}" />

        <div class="sidebar" id="sidebar">

          <ul>

            <li onclick="location.href='${pageContext.request.contextPath}/'">대시보드</li>

            <!-- 관리자 또는 팀장 공통 메뉴 -->
            <c:if test="${grade == 'GR_01' || grade == 'GR_02'}">
                <li onclick="location.href='${pageContext.request.contextPath}/room/list'">객실관리</li>
                <li onclick="location.href='${pageContext.request.contextPath}/work/list'">업무관리</li>
                <li onclick="location.href='${pageContext.request.contextPath}/employee/list'">직원관리</li>
            </c:if>

            <li onclick="location.href='${pageContext.request.contextPath}/schedule/list'">스케줄</li>
            <li onclick="location.href='${pageContext.request.contextPath}/anno/list'">공지사항</li>

          </ul>

          <div class="user-name">${sessionScope.loginUser.emplName}</div>
        </div>

</c:if>
