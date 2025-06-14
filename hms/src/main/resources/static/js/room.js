let roomTypeMap = {};  // roomTypeMap을 전역으로 정의하여 초기화

$(document).ready(function () {

    // 객실 행 클릭 시 상세 폼 출력
    $('.room-row').on('click', function () {
        const $clickedRow = $(this);
        const $nextRow = $clickedRow.next('.detail-slide');

        if ($nextRow.length > 0) {
            $nextRow.remove(); // 이미 열려있으면 닫기
            return;
        }

        $('.detail-slide').remove(); // 다른 열린 행 제거

        const room = $clickedRow.data('room');
        const type = $clickedRow.data('type');
        const roomId = $clickedRow.data('room-id');
        const reserve = $clickedRow.data('reserve');

        const typeLabel = roomTypeMap[type] || type;  // roomTypeMap이 정의된 후 값이 들어오므로, 여기서 사용하는 방식

        const $detailRow = $(`
            <tr class="detail-slide">
                <td colspan="4">
                    <table style="width:100%; border-collapse: collapse;">
                        <tr>
                            <th style="padding:8px;">객실 번호</th>
                            <td><input type="text" value="${room}" name="roomNum"></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">객실 종류</th>
                            <td><input type="text" value="${typeLabel}" name="roomType"></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">예약 상태</th>
                            <td><input type="text" value="${reserve || '예약되지 않음'}" name="reserve"></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="button-area" style="text-align: center;"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        `);

        role = $('body').data('role');
        const $buttonArea = $detailRow.find('.button-area');

        if (role === 'GR_01') {
            $buttonArea.append('<button class="update-btn">수정</button>');
            $buttonArea.append('<button class="action-btn">삭제</button>');
        } else if (role === 'GR_02') {
            $buttonArea.append('<button class="update-btn">수정</button>');
        }

        $buttonArea.append('<button class="action-btn close-btn">닫기</button>');

        $clickedRow.after($detailRow);

            // 수정 버튼 클릭 시
            $(document).on('click', '.update-btn', function () {
                const $clickedRow = $(this).closest('.room-row');  // 클릭된 버튼이 속한 행을 가져오기

                const updatedRoomNum = $('input[name="roomNum"]').val();
                const updatedRoomType = $('input[name="roomType"]').val();
                const updatedReserve = $('input[name="reserve"]').val();

                const updatedData = {
                    roomId: roomId,  // 수정할 객실 ID
                    roomNumber: updatedRoomNum,
                    /*roomClass: updatedRoomType,*/
                    roomClass: "test",
                    reservDate: updatedReserve,
                    createdDate: "2025-06-14",
                    createdId: "aa",
                    updatedId: "aa",
                };

                // 수정된 데이터 서버로 전송
                $.ajax({
                    type: 'PUT',
                    url: `/room/update/${roomId}`,  // 수정 API URL
                    contentType: 'application/json',
                    data: JSON.stringify(updatedData),
                    success: function (res) {
                        alert('객실 정보가 수정되었습니다.');
                        location.reload();  // 수정 후 화면 갱신
                    },
                    error: function () {
                        alert('수정 실패. 입력 값을 확인해주세요.');
                    }
                });
            });

    });

    // 상세 폼 닫기
    $(document).on('click', '.close-btn', function () {
        $(this).closest('tr.detail-slide').remove();
    });

    // 방 타입과 이름 가져오기
    $.ajax({
        url: '/room/types',  // 룸 타입을 가져오는 API
        type: 'GET',
        success: function(roomTypeMapData) {
            roomTypeMap = roomTypeMapData;  // 서버에서 받은 데이터로 roomTypeMap을 업데이트

            const roomTypeSelect = $('#roomType');

            // Map을 Array로 변환하여 code_id 기준으로 정렬
            const sortedRoomTypes = Object.entries(roomTypeMap).sort((a, b) => {
                return a[0].localeCompare(b[0]);  // code_id 기준으로 오름차순 정렬
            });

            // 정렬된 데이터를 select 박스에 추가
            sortedRoomTypes.forEach(([code, name]) => {
                roomTypeSelect.append(new Option(name, code));  // <option> 추가
            });
        },
        error: function() {
            console.error("객실 종류를 불러오는 데 실패했습니다.");
        }
    });

    // 등록 폼 토글
    $("#add_btn").click(function () {
        $("#newRoomForm").toggle(300);
    });

    $("#add_cancle").click(function () {
        $("#newRoomForm").hide(300);
    });

    // 객실 등록 폼 submit 처리
    $('#newRoomForm').on('submit', function (e) {
        e.preventDefault(); // 기본 form 전송 막기

        const roomNum = $('input[name="roomNum"]').val();
        const roomType = $('select[name="roomType"]').val();  // select 박스에서 roomType 값 가져오기
        const reservDate = $('input[name="res"]').val();
        const cleanState = $('input[name="date"]').val();

        // 유효성 체크 (예시)
        if (!roomNum || !roomType) {
            alert("객실 번호와 종류는 필수입니다.");
            return;
        }

        const data = {
            roomNumber: roomNum,
            roomClass: roomType,
            reservDate: reservDate,
            cleanState: cleanState
        };

        $.ajax({
            type: 'POST',
            url: '/room/add',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                alert('객실이 등록되었습니다.');
                location.reload();
            },
            error: function () {
                alert('등록 실패. 입력 값을 확인해주세요.');
            }
        });
    });
});
