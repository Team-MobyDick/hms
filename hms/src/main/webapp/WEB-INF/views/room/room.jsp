<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />
<!DOCTYPE html>

<html>

    <head>
        <title>객실 관리</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/room.css"/>
    </head>

    <body>

        <h2>객실 관리</h2>

        <button id="add_btn">객실 등록</button>

        <table>

            <thead>
                <tr>
                    <th>객실 번호</th>
                    <th>객실 종류</th>
                    <th>예약 상태</th>
                    <th>청소 상태</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${not empty roomList}">
                        <c:forEach var="room" items="${roomList}">
                            <tr class="room-row"
                                data-room="${room.roomNumber}"
                                data-type="${room.roomClass}"
                                data-reserve="${room.reservDate}">
                                <td data-label="객실 번호">${room.roomNumber}</td>
                                <td data-label="객실 종류">
                                   <c:choose>
                                       <c:when test="${room.roomClass eq 'TP_01'}">스탠다드룸</c:when>
                                       <c:when test="${room.roomClass eq 'TP_02'}">디럭스룸</c:when>
                                       <c:when test="${room.roomClass eq 'TP_03'}">트윈룸</c:when>
                                       <c:when test="${room.roomClass eq 'TP_04'}">패밀리룸</c:when>
                                       <c:when test="${room.roomClass eq 'TP_05'}">스위트룸</c:when>
                                       <c:when test="${room.roomClass eq 'TP_06'}">펜트하우스</c:when>
                                       <c:otherwise>미정</c:otherwise>
                                   </c:choose>
                                </td>
                                <td data-label="예약 상태">
                                    <c:choose>
                                        <c:when test="${not empty room.reservDate}">
                                            ${room.reservDate}
                                        </c:when>
                                        <c:otherwise>
                                            예약되지 않음
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td data-label="청소 상태">Cleaning</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="4">객실 목록이 비어 있습니다.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>

        </table>

        <!-- 객실 추가 폼 -->
        <form id="newRoomForm" hidden>
            <table>
                <tr>
                    <th>번호</th>
                    <th>객실 종류</th>
                    <th>예약 상태</th>
                    <th>청소 상태</th>
                    <th>동작</th>
                </tr>
                <tr>
                    <td><input type="text" name="roomNum" placeholder="번호"></td>
                    <td><input type="text" name="roomType" placeholder="객실 종류"></td>
                    <td><input type="text" name="res" placeholder="예약 상태"></td>
                    <td><input type="text" name="date" placeholder="청소 상태"></td>
                    <td>
                        <button type="submit">등록</button>
                        <button type="button" id="add_cancle">취소</button>
                    </td>
                </tr>
            </table>
        </form>

        <script src="${pageContext.request.contextPath}/js/room.js" defer></script>

    </body>
</html>
