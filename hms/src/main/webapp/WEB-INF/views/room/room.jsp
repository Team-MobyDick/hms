<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />

<!DOCTYPE html>
<html>
<head>
    <title>객실 관리</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body data-role="${userRole}">

    <h2>객실 관리</h2>
    <button id="add_btn">객실 등록</button>

    <div style="margin-bottom: 10px;">
      <label for="roomTypeFilter">객실 종류 필터:</label>
      <select id="roomTypeFilter">
        <option value="ALL">전체</option>
        <c:forEach var="entry" items="${roomTypeMap}">
          <option value="${entry.key}">${entry.value}</option>
        </c:forEach>
      </select>
    </div>

    <table>
        <thead>
            <tr>
                <th>객실 번호</th>
                <th>객실 종류</th>
                <th>예약 상태</th>
                <th>청소 상태</th>
                <th>특이 사항</th>
                <th>담당자</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty roomList}">
                    <c:forEach var="room" items="${roomList}">
                        <tr class="room-row"
                            data-room="${room.roomNumber}"
                            data-type="${room.roomClass}"
                            data-room-id="${room.roomId}"
                            data-reserve="${not empty room.reservDate ? 'Yes' : 'No'}"
                            data-createdDate="${room.createdDate}"
                            data-createdId="${room.createdId}"
                            data-updatedDate="${room.updatedDate}"
                            data-updatedId="${room.updatedId}">
                            <td data-label="객실 번호">${room.roomNumber}</td>
                            <td data-label="객실 종류">
                                <c:out value="${roomTypeMap[room.roomClass]}" default="미정" />
                            </td>
                            <td data-label="예약 상태">
                                <c:choose>
                                    <c:when test="${room.reservDate == 'Yes'}">
                                        예약되어 있습니다.
                                    </c:when>
                                    <c:otherwise>
                                        예약되지 않았습니다.
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${room.cleanState}</td>
                            <td>${room.extraInfo}</td>
                            <td>${room.emplName}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="4">객실 목록이 비어 있습니다.</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <jsp:include page="/WEB-INF/views/include/pagination.jsp" />

    <!-- 객실 등록 폼 -->
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
                <td>
                    <select name="roomType" id="roomType"></select>
                </td>
                <td>
                    <select name="res">
                        <option value="Yes">Yes</option>
                        <option value="No" selected>No</option>
                    </select>
                </td>
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
