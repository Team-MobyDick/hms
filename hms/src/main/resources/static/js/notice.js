$(document).ready(function () {
    loadNotices();

    // 공지 등록 폼 열기/닫기
    $('#noticeAddBtn').click(() => $('#noticeForm').toggle(300));
    $('#noticeCancelBtn').click(() => $('#noticeForm').hide(300));

    // 공지 등록 처리
    $('#newNoticeForm').on('submit', function (e) {
        e.preventDefault();

        const newNotice = {
            noticeTitle: $('input[name="noticeTitle"]').val(),
            emplId: $('input[name="emplId"]').val(),
            createdDate: $('input[name="createdDate"]').val(),
            noticeContent: $('textarea[name="noticeContent"]').val()
        };

        if (!newNotice.noticeTitle || !newNotice.noticeContent) {
            alert("제목과 내용을 입력해주세요.");
            return;
        }

        $.ajax({
            url: '/anno/notice',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newNotice),
            success: function () {
                alert("공지 등록 완료");
                alert("공지 등록 성공!");
                location.reload();
            },
            error: function (xhr, status, error) {
                console.error("xhr:", xhr.responseText);
                alert("등록 실패. 다시 시도해주세요.");
            }
        });
    });



    // 수정 버튼 클릭 시
    $(document).on('click', '.notice-edit-btn', function () {
        const noticeId = $(this).data('id');
        const row = $(this).closest('tr');
        const title = row.find('.notice-title').text();
        const content = row.find('.notice-content').text();

        const $form = $(`
            <tr class="edit-form-row">
                <td colspan="4">
                    <form class="edit-form">
                        <input type="text" name="noticeTitle" value="${title}" required><br>
                        <textarea name="noticeContent" required>${content}</textarea><br>
                        <button type="submit">저장</button>
                        <button type="button" class="edit-cancel-btn">취소</button>
                    </form>
                </td>
            </tr>
        `);

        $('.edit-form-row').remove(); // 기존 폼 제거
        row.after($form);

        // 수정 저장
        $form.find('.edit-form').on('submit', function (e) {
            e.preventDefault();

            const updated = {
                noticeId,
                noticeTitle: $form.find('input[name="noticeTitle"]').val(),
                noticeContent: $form.find('textarea[name="noticeContent"]').val()
            };

            $.ajax({
                url: `/notice/update/${noticeId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(updated),
                success: function () {
                    alert("수정 완료");
                    location.reload();
                },
                error: function () {
                    alert("수정 실패");
                }
            });
        });

        // 취소 버튼
        $form.find('.edit-cancel-btn').click(function () {
            $form.remove();
        });
    });

    // 삭제 버튼 클릭 시
    $(document).on('click', '.notice-delete-btn', function () {
        const noticeId = $(this).data('id');
        if (!confirm("정말 삭제하시겠습니까?")) return;

        $.ajax({
            url: `/notice/delete/${noticeId}`,
            type: 'DELETE',
            success: function () {
                alert("삭제 완료");
                location.reload();
            },
            error: function () {
                alert("삭제 실패");
            }
        });
    });

    // 공지사항 목록 불러오기
    function loadNotices() {
        $.ajax({
            url: '/notice/list',
            type: 'GET',
            success: function (data) {
                const $tableBody = $('#noticeTable tbody');
                $tableBody.empty();

                data.forEach(notice => {
                    $tableBody.append(`
                        <tr>
                            <td>${notice.noticeId}</td>
                            <td class="notice-title">${notice.noticeTitle}</td>
                            <td class="notice-content">${notice.noticeContent}</td>
                            <td>
                                <button class="notice-edit-btn" data-id="${notice.noticeId}">수정</button>
                                <button class="notice-delete-btn" data-id="${notice.noticeId}">삭제</button>
                            </td>
                        </tr>
                    `);
                });
            },
            error: function () {
                console.error("공지사항 목록을 불러오는 데 실패했습니다.");
            }
        });
    }
});
