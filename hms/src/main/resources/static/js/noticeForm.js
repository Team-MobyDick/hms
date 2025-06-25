/* 공지사항 등록 및 수정 시 사용할 js */

// 등록
$("#saveBtn").click(function () {

    const formData = $("#noticeForm").serialize();

    const form = document.getElementById('noticeForm');

    if (!form.checkValidity()) {
        form.reportValidity(); // 브라우저에서 메시지 자동 표시
        return;
    }

    $.ajax({
        url: contextPath + "/notice/add",
        type: "POST",
        data: formData,
        success: function () {
            alert("처리가 완료되었습니다.");
            location.href = contextPath + "/notice/list";
        },
        error: function (err) {
            alert("등록 처리에 실패하였습니다. 잠시 후 다시 시도하시거나 관리자에게 문의 바랍니다.");
        }
    });

});

// 수정
$("#updateBtn").click(function () {

    const noticeId = $("input[name='noticeId']").val();
    const formData = $("#noticeForm").serialize();

    const form = document.getElementById('noticeForm');

    if (!form.checkValidity()) {
        form.reportValidity(); // 브라우저에서 메시지 자동 표시
        return;
    }

    $.ajax({
        url: contextPath + "/notice/update/" + noticeId,
        type: "POST", // 🔥 PUT → POST 변경!
        data: formData,
        success: function () {
            alert("처리가 완료되었습니다.");
            location.href = contextPath + "/notice/list";
        },
        error: function (err) {
            alert("수정 처리에 실패하였습니다. 잠시 후 다시 시도하시거나 관리자에게 문의 바랍니다.");
        }
    });
});

// 취소
$("#cancelBtn").click(function () {
    window.history.back();
});