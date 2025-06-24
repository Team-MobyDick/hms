/* 공지사항 등록 및 수정 시 사용할 js */

// 등록
$("#saveBtn").click(function () {

    const formData = $("#noticeForm").serialize();

    /* 제목과 내용 */
    var noticeTitle = document.getElementsByName("noticeTitle")[0].value.trim();
    var noticeContent = document.getElementsByName("noticeContent")[0].value.trim();

    if (noticeTitle === "" || noticeTitle === null || noticeContent === "" ||  noticeContent === null) {

        alert("제목과 내용을 입력해주세요!");
        return;

    }

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

// 수정
$("#updateBtn").click(function () {
    const noticeId = $("input[name='noticeId']").val();

    /* 제목과 내용 */
    var noticeTitle = document.getElementsByName("noticeTitle")[0].value.trim();
    var noticeContent = document.getElementsByName("noticeContent")[0].value.trim();

    if (noticeTitle === "" || noticeTitle === null || noticeContent === "" ||  noticeContent === null) {

        alert("제목과 내용을 입력해주세요!");
        return;

    }

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

// 취소
$("#cancelBtn").click(function () {
    window.history.back();
});