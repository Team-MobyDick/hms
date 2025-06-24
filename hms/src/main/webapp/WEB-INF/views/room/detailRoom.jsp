<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}"/>

<%-- detailRoom.jsp --%>

<c:if test="${not empty roomDetail}">
    <form id="editForm_${roomDetail.roomId}">
        <table>
            <tr>
                <th colspan="2">객실 상세 보기 / 수정</th>
            </tr>
            <tr>
                <th>객실 이름</th>
                <c:if test="${userRole == 'GR_01'}">
                    <td><input type="text" id="roomName" name="roomName" value="${roomDetail.roomName}" /></td>
                </c:if>
                <c:if test="${userRole != 'GR_01'}">
                    <td><input type="text" id="roomName" name="roomName" value="${roomDetail.roomName}" disabled/></td>
                </c:if>
            </tr>
            <tr>
                <th>객실 종류</th>
                <td>
                <c:if test="${userRole == 'GR_01'}">
                    <select name="roomType" id="roomType">
                        <c:forEach var="code" items="${codeList}">
                            <option
                                value="${code.codeId}"
                                ${roomDetail.roomClass eq code.codeId ? 'selected' : ''}>
                                    ${code.codeName}
                            </option>
                        </c:forEach>
                    </select>
                </c:if>
                    <c:if test="${userRole != 'GR_01'}">
                    <select name="roomType" id="roomType" disabled>
                        <c:forEach var="code" items="${codeList}">
                            <option
                                value="${code.codeId}"
                                ${roomDetail.roomClass eq code.codeId ? 'selected' : ''}>
                                    ${code.codeName}
                            </option>
                        </c:forEach>
                    </select>
                </c:if>
                </td>
            </tr>
            <tr>
                <th>업무 내용</th>
                <td>${roomDetail.workdName}</td>
            </tr>
            <tr>
                <th>담당자</th>
                <td>${roomDetail.emplName}</td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${userRole == 'GR_01'}">
                        <button type="button" onclick="deleteRoom('${roomDetail.roomId}')">삭제하기</button>
                        <button type="button" onclick="saveChanges('${roomDetail.roomId}')">수정하기</button>
                    </c:if>
                    <button type="button" onclick="closeDetail('${roomDetail.roomId}')">닫기</button>
                </td>
            </tr>
        </table>
    </form>
</c:if>

<c:if test="${empty roomDetail}">
    <p>상세 정보를 불러올 수 없습니다.</p>
</c:if>
