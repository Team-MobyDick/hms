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

    <c:choose>
        <c:when test="${mode == 'edit'}">
            <button type="button" id="updateBtn">수정</button>
        </c:when>
        <c:otherwise>
            <button type="button" id="saveBtn">등록</button>
        </c:otherwise>
    </c:choose>
</form>

<script>
    const contextPath = "${pageContext.request.contextPath}";

    $("#saveBtn").click(function () {
        const formData = $("#noticeForm").serialize();
        $.ajax({
            url: contextPath + "/notice/add",
            type: "POST",
            data: formData,
            success: function () {
                alert("등록 성공");
                location.href = contextPath + "/notice/list";
            },
            error: function (err) {
                alert("등록 실패: " + err.responseText);
            }
        });
    });

    $("#updateBtn").click(function () {
        const noticeId = $("input[name='noticeId']").val();
        const formData = $("#noticeForm").serialize();
        $.ajax({
            url: contextPath + "/notice/update/" + noticeId,
            type: "POST", // 🔥 PUT → POST 변경!
            data: formData,
            success: function () {
                alert("수정 성공");
                location.href = contextPath + "/notice/list";
            },
            error: function (err) {
                alert("수정 실패: " + err.responseText);
            }
        });
    });
</script>
</body>
</html>
