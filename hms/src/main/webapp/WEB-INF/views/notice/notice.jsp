<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>

    <head>
        <title>공지사항</title>
            <style>

            </style>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css"/>
    </head>

    <body>
        <h1>공지사항</h1>
        <button id = "add_btn">공지사항 등록</button>

       <!-- <input type = "text" placeholder="검색어를 입력해 주세요."> -->

        <table>
            <thead>
                <tr>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
                    <c:forEach var="notice" items="${noticeList}">
                        <tr class="ann-row"
                                data-title="${notice.noticeTitle}"
                                data-writer="${notice.emplId}"
                                data-content="${notice.noticeContent}"
                                data-date="${notice.createdDate}">
                                <td>${notice.noticeTitle}</td>
                                <td>${notice.emplId}</td>
                                <td>${notice.createdDate}</td>
                        </tr>
                    </c:forEach>
            </tbody>
        </table>

        <jsp:include page="/WEB-INF/views/include/pagination.jsp" />

        <!-- 공지사항 등록 폼 -->
        <form id="newNoticeForm" hidden>
            <table>
                <tr>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>내용</th>
                    <th>작성일</th>
                </tr>
                <tr>
                      <td><input type="text" name="noticeTitle" placeholder="제목"></td>
                      <td><input type="text" name="emplId" placeholder="작성자"></td>
                      <td><input type="text" name="noticeContent" placeholder="내용"></td>
                      <td><input type="text" name="createdDate" placeholder="작성일"></td>
                      <td>
                        <button type="submit">등록</button>
                        <button id = "add_cancle">취소</button>
                      </td>
                </tr>
              </table>
        </form>
         <script src="${pageContext.request.contextPath}/js/notice.js" defer></script>

    </body>
</html>
