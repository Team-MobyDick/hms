/* room.jsp에서 사용될 js */

/* 객실 등록 버튼 */
function toggleAddRoom() {
    const container = document.getElementById('addRoomContainer');
    container.style.display = (container.style.display === 'none') ? 'block' : 'none';
}

/* 객실 상세 정보 로딩 */
function loadDetail(roomId) {

    /* 선택한 행의 아이디로 조회 */
    $.ajax({
        url: '/room/detail',
        type: 'GET',
        data:  {roomId : roomId},
        success: function (response) {

            $('#roomDetailContainer').html(response).slideDown();

        },
        error: function (xhr, status, error) {
            alert("상세 정보를 불러오지 못했습니다: " + error);
        }
    });
}

/* 상세 보기에서 닫기 버튼 눌렀을 때 */
function closeDetail() {
    $('#roomDetailContainer').slideUp();
}


$(document).ready(function() {
    $('#add_cancle').on('click', function() {
        $('#addRoomContainer').hide();
    });
});

/* 등록 처리 */
function addRoom() {

    const roomName = $('#roomName').val();
    const roomClass = $('#roomType').val();
    const roomClassName = $('#roomType option:selected').text();

    if(confirm('등록 하시겠습니까?')) {

        $.ajax({
            url: '/room/add',
            type: 'POST',
            data: {
                roomName: roomName,
                roomClass: roomClass,
                roomClassName: roomClassName,
            },
            success: function(response) {
                alert('등록 성공!');
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

    if(confirm('수정 하시겠습니까?')) {
        
        // 입력된 수정 값들 가져오기
        const roomName = $('#editForm_' + roomId + ' input[name="roomName"]').val();
        const roomClassId = $('#roomType option:selected').val();
        const roomClassName = $('#roomType option:selected').text();

        // 입력값 전달
        $.ajax({
            url: '/room/upd',
            type: 'POST',
            data: {
                roomId: roomId,
                roomName: roomName,
                roomClass: roomClassId,
                roomClassName: roomClassName
            },
            success: function(response) {
                alert("수정이 완료되었습니다.");
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

    if(confirm('삭제 하시겠습니까?')) {

        // 삭제할 방의 ID전달
        $.ajax({
            url: '/room/delete',
            type: 'POST',
            data: {
                roomId: roomId,
            },
            success: function(response) {
                alert("삭제가 완료되었습니다.");
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