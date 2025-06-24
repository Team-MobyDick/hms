<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}"/>

<%-- room.jsp --%>

<!DOCTYPE html>
<html>

    <head>
        <title>객실 관리</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <%-- 객실 관리 처리용 js --%>
        <script src="${pageContext.request.contextPath}/js/room.js"></script>
    </head>

    <body>

    <h2>객실 관리</h2>


        <button id="add_btn" onclick="toggleAddRoom();">객실 등록</button>

        <%-- 객실 등록 폼 --%>
        <div id="addRoomContainer" style="display: none;">
            <jsp:include page="addRoom.jsp" />
        </div>

        <%-- 객실 목록 --%>
        <table id="room_table">

            <thead>
                <tr>
                    <th>객실 이름</th>
                    <th>객실 종류</th>
                    <th>업무 내용</th>
                    <th >담당자</th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty roomList}">

                        <c:forEach var="room" items="${roomList}">
                            <tr onclick="loadDetail('${room.roomId}')">
                                <td>${room.roomName}</td>
                                <td>${room.roomClassName}</td>
                                <td>${room.workdName}</td>
                                <td>${room.emplName}</td>
                            </tr>
                        </c:forEach>

                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td colspan="4" style="text-align: center;">객실 목록이 비어 있습니다.</td>
                        </tr>
                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

        <%-- 객실 상세 정보 페이지 --%>
        <div id="roomDetailContainer" style="display: none; margin-top: 20px;"></div>

        <%-- 페이징 처리 영역 --%>
        <jsp:include page="/WEB-INF/views/include/pagination.jsp" />

    </body>

</html>
