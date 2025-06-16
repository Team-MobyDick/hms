let roomTypeMap = {};
let roomTypeList = [];

$(document).ready(function () {
    // 페이지 로드 시 URL 파라미터에서 필터 상태 읽어오기
    const urlParams = new URLSearchParams(window.location.search);
    let selectedType = urlParams.get('roomType') || 'ALL';  // 기본값 'ALL'

    // 로컬 스토리지에서 필터값을 가져와서 설정 (새로고침 이후에도 값 유지)
    if(localStorage.getItem('selectedRoomType')) {
        selectedType = localStorage.getItem('selectedRoomType');
    }

    // 필터 드롭다운에 선택된 값 설정
    $('#roomTypeFilter').val(selectedType);

    // 방 타입 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/room/types', // 서버에서 방 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            roomTypeList = data;
            roomTypeList.forEach(rt => {
                roomTypeMap[rt.codeId] = rt.codeName; // map에 추가
            });

            // 방 타입을 등록 폼의 셀렉트 박스에 추가
            populateRoomTypes();

            // AJAX 완료 후 filterRooms 실행
            filterRooms(selectedType);

            // 필터 선택 시 URL에 파라미터 추가 및 페이지 새로 고침 없이 URL만 변경
            $('#roomTypeFilter').on('change', function () {
                const selectedType = $(this).val();

                // 로컬 스토리지에 선택된 필터 저장
                localStorage.setItem('selectedRoomType', selectedType);

                const currentUrl = new URL(window.location.href);
                currentUrl.searchParams.set('roomType', selectedType);  // 필터값을 URL 파라미터에 추가
                window.history.pushState({}, '', currentUrl.toString());  // 새로 고침 없이 URL만 변경

                filterRooms(selectedType);  // 필터링 적용
            });
        },
        error: function() {
            console.error("객실 타입을 불러오는 데 실패했습니다.");
        }
    });

    // 필터링 함수
    function filterRooms(selectedType) {
        $('.room-row').each(function () {
            const roomType = $(this).data('type');
            if (selectedType === 'ALL' || roomType === selectedType) {
                $(this).show();
            } else {
                $(this).hide();
            }

            // 상세 폼도 닫기
            $(this).next('.detail-slide').remove();
        });
    }

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
                <td colspan="6">
                    <table style="width:100%; border-collapse: collapse;">
                        <tr>
                            <th style="padding:8px;">객실 이름</th>
                            <td><input type="text" value="${room}" name="roomName"></td>
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
                            <td>
                                <select name="res">
                                    <option value="Yes" ${reserve === 'Yes' ? 'selected' : ''}>Yes</option>
                                    <option value="No" ${reserve === 'No' ? 'selected' : ''}>No</option>
                                </select>
                            </td>
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

        // 예약 상태 선택
        const reservStatus = $container.find('select[name="res"]').val();

        const updatedData = {
            roomId: $(this).data('room-id'),
            roomName: $container.find('input[name="roomName"]').val(),
            roomClass: $container.find('select[name="roomType"]').val(),
            reservDate: reservStatus // 'Yes' 또는 'No' 값
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

        // 등록 폼 토글
        $('#add_btn').click(function () {
            $('#newRoomForm').toggle(300);
        });

        $('#add_cancle').click(function () {
            $('#newRoomForm').hide(300);
        });

        // 등록 폼의 셀렉트 박스에 방 타입 채우기
        function populateRoomTypes() {
            const $roomTypeSelect = $('select[name="roomType"]');
            $roomTypeSelect.empty(); // 기존 옵션을 지움

            roomTypeList.forEach(rt => {
                $roomTypeSelect.append(`<option value="${rt.codeId}">${rt.codeName}</option>`);
            });
        }

        // 등록 폼 제출
        $('#newRoomForm').on('submit', function (e) {
            e.preventDefault();

            const data = {
                roomName: $('input[name="roomName"]').val(),
                roomClass: $('select[name="roomType"]').val(),
                reservDate: $('input[name="res"]').val(),
            };

            if (!data.roomName || !data.roomClass) {
                alert("객실 이름과 종류는 필수입니다.");
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
