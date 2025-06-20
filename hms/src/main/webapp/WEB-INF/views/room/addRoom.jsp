<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}"/>

<%-- addRoom.jsp --%>

<!DOCTYPE html>
<html>

    <head>
        <title>객실 등록</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <body>

        <form id="addForm" method="post">

            <table>

                <tr>
                    <th>객실 이름</th>
                    <th>객실 종류</th>
                    <th>버튼</th>
                </tr>

                <tr>

                    <td>
                        <input
                                id="roomName"
                                type="text"
                                name="roomName"
                                placeholder="객실 이름을 100자 내로 적어주세요."
                                maxlength="100">
                    </td>

                    <td>
                        <select name="roomType" id="roomType">
                            <c:forEach var="code" items="${codeList}">
                                <option value="${code.codeId}">${code.codeName}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <button type="button" onclick="addRoom();">등록</button>
                        <button type="button" id="add_cancle">취소</button>
                    </td>

                </tr>

            </table>
        </form>

    </body>

</html>
