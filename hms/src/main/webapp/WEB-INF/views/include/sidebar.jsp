<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar" id="sidebar">
  <ul>
    <li onclick="location.href='${pageContext.request.contextPath}/'">대시보드</li>
    <li onclick="location.href='${pageContext.request.contextPath}/room/list'">객실관리</li>
    <li onclick="location.href='${pageContext.request.contextPath}/'">업무관리</li>
    <li onclick="location.href='${pageContext.request.contextPath}/'">직원관리</li>
    <li onclick="location.href='${pageContext.request.contextPath}/'">스케줄</li>
    <li onclick="location.href='${pageContext.request.contextPath}/anno/list'">공지사항</li>
  </ul>
  <div class="user-name">${sessionScope.userName}</div>
</div>
