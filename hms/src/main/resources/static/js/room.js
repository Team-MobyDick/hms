/* room.jsp에서 사용될 js */

/* 객실 등록 버튼 */

function toggleAddRoom() {
    const container = document.getElementById('addRoomContainer');
    const button = document.getElementById('add_btn');

    const isHidden = !container.classList.contains('show');

    closeDetail();

    container.classList.toggle('show');
    button.textContent = isHidden ? '닫기' : '객실 등록';
}

/* 객실 상세 정보 로딩 */
function loadDetail(roomId) {
    const $clickedRow = $(event.currentTarget);
    const $existingRow = $('.room-detail-row');

    $('#addRoomContainer').removeClass('show');
    $('#add_btn').text('객실 등록');

    if ($existingRow.length && $existingRow.data('roomId') === roomId) {
        closeDetail();
        return;
    }

    $existingRow.remove(); // 기존 상세 닫기

    // 상세 <tr> 즉시 삽입 (애니메이션 없음)
    const $newRow = $(`
        <tr class="room-detail-row" data-room-id="${roomId}">
            <td colspan="4">
                <div class="room-detail-content" style="opacity: 0;">로딩중...</div>
            </td>
        </tr>
    `);
    $clickedRow.after($newRow);

    // Ajax 요청
    $.ajax({
        url: '/room/detail',
        type: 'GET',
        data: { roomId: roomId },
        success: function (response) {
            const $content = $newRow.find('.room-detail-content');
            $content.html(response).css('opacity', 0).animate({ opacity: 1 }, 200); // 부드럽게 fade-in
        },
        error: function () {
            $newRow.find('.room-detail-content').text('불러오기 실패');
        }
    });
}

/* 상세 보기에서 닫기 버튼 눌렀을 때 */
function closeDetail() {
    const $row = $('.room-detail-row');
    $row.fadeOut(150, function () {
        $row.remove();
    });
}


$(document).ready(function() {
    $('#cancelBtn').on('click', function() {
        $('#addRoomContainer').hide();
    });
});

/* 등록 처리 */
function addRoom() {

    const roomName = $('#roomName').val();
    const roomClass = $('#roomType').val();
    const roomClassName = $('#roomType option:selected').text();

    const form = document.getElementById('addForm');

    if (!form.checkValidity()) {
        form.reportValidity(); // 브라우저에서 메시지 자동 표시
        return;
    }

    if(confirm('객실을 등록하시겠습니까?')) {

        $.ajax({
            url: '/room/add',
            type: 'POST',
            data: {
                roomName: roomName,
                roomClass: roomClass,
                roomClassName: roomClassName,
            },
            success: function() {
                alert('처리가 완료되었습니다.');
                $('#addRoomContainer').slideUp(); // 등록 후 닫기
                $('#addForm')[0].reset(); // 입력 초기화

                location.reload();

            },
            error: function(xhr, status, error) {
                alert('등록 실패: ' + error);
            }
        });

    } else {
        alert("취소하였습니다.");
    }
}

/* 상세정보에서 수정하기 버튼 눌렀을 때 */
function saveChanges(roomId) {

    if(confirm('객실을 수정하시겠습니까?')) {
        
        // 입력된 수정 값들 가져오기
        const roomName = $('#editForm_' + roomId + ' input[name="roomName"]').val();
        const roomClassId = $('#editForm_' + roomId + ' select[name="roomType"]').val();
        const roomClassName = $('#editForm_' + roomId + ' select[name="roomType"] option:selected').text();

        // 입력값 전달
        $.ajax({
            url: '/room/upd',
            type: 'POST',
            data: {
                roomId: roomId,
                roomName: roomName,
                roomClass: roomClassId,
                roomClassName: roomClassName,
            },
            success: function() {
                alert("처리가 완료되었습니다.");
                // 수정 후 새로 고침 혹은 변경된 내용 반영
                location.reload();
            },
            error: function(xhr, status, error) {
                alert("수정 실패: " + error);
            }
        });

    } else {

        alert("취소하였습니다.");

    }

}

/* 삭제하기 버튼 눌렀을 때 */
function deleteRoom(roomId) {

    if(confirm('객실을 삭제하시겠습니까?')) {

        // 삭제할 방의 ID전달
        $.ajax({
            url: '/room/delete',
            type: 'POST',
            data: {
                roomId: roomId,
            },
            success: function() {
                alert("처리가 완료되었습니다.");
                location.reload();
            },
            error: function(xhr, status, error) {
                alert("삭제 실패: " + error);
            }
        });

    } else {

        alert("취소하였습니다.");

    }

}