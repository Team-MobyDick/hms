let roomTypeMap = {};
let roomTypeList = [];

$(document).ready(function () {

    // 객실 행 클릭 → 상세 폼 열기
    $('.room-row').on('click', function () {
        const $clickedRow = $(this);
        const $nextRow = $clickedRow.next('.detail-slide');

        if ($nextRow.length > 0) {
            $nextRow.remove();  // 다시 클릭 시 닫기
            return;
        }

        $('.detail-slide').remove(); // 기존 열림 닫기

        const room = $clickedRow.data('room');
        const type = $clickedRow.data('type');
        const roomId = $clickedRow.data('room-id');
        const reserve = $clickedRow.data('reserve');

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
                            <td>
                                <select name="roomType">
                                    ${
                                      roomTypeList.map(rt => {
                                        const selected = (rt.codeId === type) ? 'selected' : '';
                                        return `<option value="${rt.codeId}" ${selected}>${rt.codeName}</option>`;
                                      }).join('')
                                    }
                                </select>
                            </td>
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

        const role = $('body').data('role');
        const $buttonArea = $detailRow.find('.button-area');

        if (role === 'GR_01') {
            $buttonArea.append(`<button class="update-btn" data-room-id="${roomId}">수정</button>`);
            $buttonArea.append('<button class="delete-btn">삭제</button>');
        } else if (role === 'GR_02') {
            $buttonArea.append(`<button class="update-btn" data-room-id="${roomId}">수정</button>`);
        } else if (role === 'GR_03') {
            $detailRow.find('input').attr('readonly', true);
            $detailRow.find('select').attr('disabled', true);
        }

        $buttonArea.append('<button class="action-btn close-btn">닫기</button>');
        $clickedRow.after($detailRow);
    });

    // 상세 폼 닫기
    $(document).on('click', '.close-btn', function () {
        $(this).closest('tr.detail-slide').remove();
    });

    // 수정 버튼 클릭
    $(document).on('click', '.update-btn', function () {
        const $container = $(this).closest('tr.detail-slide');

        const updatedData = {
            roomId: $(this).data('room-id'),
            roomNumber: $container.find('input[name="roomNum"]').val(),
            roomClass: $container.find('select[name="roomType"]').val(),
            reservDate: $container.find('input[name="reserve"]').val()
        };

        $.ajax({
            type: 'PUT',
            url: `/room/update/${updatedData.roomId}`,
            contentType: 'application/json',
            data: JSON.stringify(updatedData),
            success: function () {
                alert('객실 정보가 수정되었습니다.');
                location.reload();
            },
            error: function () {
                alert('수정 실패. 입력 값을 확인해주세요.');
            }
        });
    });

    // 삭제 버튼 클릭
    $(document).on('click', '.delete-btn', function () {
        const roomId = $(this).closest('.detail-slide').find('.update-btn').data('room-id');
        if (!confirm("정말 삭제하시겠습니까?")) return;

        $.ajax({
            url: `/room/delete/${roomId}`,
            type: 'DELETE',
            success: function () {
                alert("삭제 완료되었습니다.");
                location.reload();
            },
            error: function () {
                alert("삭제 실패. 관리자에게 문의하세요.");
            }
        });
    });

    // 방 타입 목록 조회 (초기 렌더링 시)
    $.ajax({
        url: '/room/types',
        type: 'GET',
        success: function(data) {
            roomTypeList = data;
            const sortedList = roomTypeList.sort((a, b) => a.codeId.localeCompare(b.codeId));
            const $select = $('#roomType');

            sortedList.forEach(type => {
                roomTypeMap[type.codeId] = type.codeName;
                $select.append(new Option(type.codeName, type.codeId));
            });
        },
        error: function () {
            console.error("객실 타입을 불러오는 데 실패했습니다.");
        }
    });

    // 등록 폼 토글
    $('#add_btn').click(function () {
        $('#newRoomForm').toggle(300);
    });

    $('#add_cancle').click(function () {
        $('#newRoomForm').hide(300);
    });

    // 등록 폼 제출
    $('#newRoomForm').on('submit', function (e) {
        e.preventDefault();

        const data = {
            roomNumber: $('input[name="roomNum"]').val(),
            roomClass: $('select[name="roomType"]').val(),
            reservDate: $('input[name="res"]').val(),
            cleanState: $('input[name="date"]').val()
        };

        if (!data.roomNumber || !data.roomClass) {
            alert("객실 번호와 종류는 필수입니다.");
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/room/add',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                alert('객실이 등록되었습니다.');
                location.reload();
            },
            error: function () {
                alert('등록 실패. 입력 값을 확인해주세요.');
            }
        });
    });

});
