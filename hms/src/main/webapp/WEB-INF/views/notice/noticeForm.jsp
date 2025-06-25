<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css"/>
<html>

    <head>
        <title>공지사항 등록/수정</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <body>
        <h2>공지사항 ${mode == 'edit' ? '수정' : '등록'}</h2>

        <form id="noticeForm">
            <c:if test="${mode == 'edit'}">
                <input type="hidden" name="noticeId" value="${notice.noticeId}" />
            </c:if>

            <label>제목:</label>
            <input type="text" name="noticeTitle" value="${notice.noticeTitle}" required /><br/>

            <label>내용:</label><br/>
            <textarea name="noticeContent" rows="6" cols="60" required>${notice.noticeContent}</textarea><br/>

            <div class="buttons" id="buttons">
                <c:choose>
                    <c:when test="${mode == 'edit'}">
                        <button type="button" id="updateBtn" style="background-color: #007bff;">수정</button>
                        <button type="button" id="cancelBtn">취소</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" id="saveBtn">등록</button>
                        <button type="button" id="cancelBtn">취소</button>
                    </c:otherwise>
                </c:choose>
            </div>

        </form>

        <script>
            const contextPath = "${pageContext.request.contextPath}";
        </script>

        <%-- 수정 및 삭제 처리용 js --%>
        <script src="${pageContext.request.contextPath}/js/noticeForm.js" defer></script>

    </body>
</html>
