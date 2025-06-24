
const userRole = userRoleJs;


// 사용하지 않는 소스
    // function getButtonsByRole(role, noticeId) {
    //     if (role === 'test') {
    //         return `
    //             <button class="action-btn edit-btn" data-id="${noticeId}">수정</button>
    //             <button class="action-btn delete-btn" data-id="${noticeId}">삭제</button>
    //             <button class="action-btn close-btn">닫기</button>
    //         `;
    //     } else {
    //         return `<button class="action-btn close-btn">닫기</button>`;
    //     }
    // }

$(document).ready(function () {
    // const userRole = 'test'; // 실제론 세션 등에서 받아야 함

    function getButtonsByRole(role, noticeId) {
        if (role === 'GR_01') {
            return `
                <button class="action-btn edit-btn" data-id="${noticeId}">수정</button>
                <button class="action-btn delete-btn" data-id="${noticeId}">삭제</button>
                <button class="action-btn close-btn">닫기</button>
            `;
        } else {
            return `<button class="action-btn close-btn">닫기</button>`;
        }
    }

    // 등록 버튼 클릭 시 폼 이동
    $('#add_btn').on('click', function getButtonsByRole (userRole, id) {

        location.href = contextPath + '/notice/form';


    });

    // 공지 클릭 시 슬라이드 오픈
    $('tbody').on('click', '.ann-row', function () {
        const $row = $(this);
        const $next = $row.next('.detail-slide');

        if ($next.length > 0) {
            $next.remove(); // 이미 열려 있으면 닫기
            return;
        }

        $('.detail-slide').remove(); // 기존 열림 닫기

        const id = $row.data('notice-id');
        const title = $row.data('title');
        const writer = $row.data('writer');
        const date = $row.data('date');
        const content = $row.data('content');

        const $detail = $(`
            <tr class="detail-slide">
                <td colspan="4">
                    <table style="width:100%; border: 1px solid #ccc;">                        
                        <tr><th style="width:100px;">내용</th><td style="white-space: pre-wrap;">${content}</td></tr>
                        <tr>
                            <td colspan="2" class="button-area" style="text-align:center;">
                                ${getButtonsByRole(userRole, id)}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        `);

        $row.after($detail);
    });

    // 닫기 버튼
    $(document).on('click', '.close-btn', function () {
        $(this).closest('tr.detail-slide').remove();
    });

    // 수정 버튼
    $(document).on('click', '.edit-btn', function () {
        const noticeId = $(this).data('id');
        location.href = contextPath + '/notice/update/' + noticeId;
    });

    // 삭제 버튼
    $(document).on('click', '.delete-btn', function () {
        const noticeId = $(this).data('id');
        if (confirm("정말 삭제하시겠습니까?")) {
            $.ajax({
                url: contextPath + '/notice/delete/' + noticeId,
                type: 'DELETE',
                success: function () {
                    alert("삭제되었습니다.");
                    location.reload();
                },
                error: function () {
                    alert("삭제 실패. 관리자에게 문의하세요.");
                }
            });
        }
    });
});