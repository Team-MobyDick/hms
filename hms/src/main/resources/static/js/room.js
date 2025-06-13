  const userRole = '${fn:escapeXml(userRole)}'; // JS에서 사용 가능하도록 안전하게 출력

// 객실 코드 매핑 객체
const roomTypeMap = {
    'TP_01': '스탠다드룸',
    'TP_02': '디럭스룸',
    'TP_03': '트윈룸',
    'TP_04': '패밀리룸',
    'TP_05': '스위트룸',
    'TP_06': '펜트하우스'
};

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
            const reserve = $clickedRow.data('reserve');

            const typeLabel = roomTypeMap[type] || type;

            const $detailRow = $(`
                <tr class="detail-slide">
                    <td colspan="4">
                        <table style="width:100%; border-collapse: collapse;">
                            <tr>
                                <th style="padding:8px;">객실 번호</th>
                                <td><input type="text" value="${room}"></td>
                            </tr>
                            <tr>
                                <th style="padding:8px;">객실 종류</th>
                                <td><input type="text" value="${typeLabel}"></td>
                            </tr>
                            <tr>
                                <th style="padding:8px;">예약 상태</th>
                                <td><input type="text" value="${reserve || '예약되지 않음'}"></td>
                            </tr>
                            <tr>
                                <td colspan="2" class="button-area" style="text-align: center;"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            `);

            const $buttonArea = $detailRow.find('.button-area');

            if (userRole === 'GR_01') {
                $buttonArea.append('<button class="action-btn">수정</button>');
                $buttonArea.append('<button class="action-btn">삭제</button>');
            } else if (userRole === 'GR_02') {
                $buttonArea.append('<button class="action-btn">수정</button>');
            }

            $buttonArea.append('<button class="action-btn close-btn">닫기</button>');

            $clickedRow.after($detailRow);
        });

        // 상세 폼 닫기
        $(document).on('click', '.close-btn', function () {
            $(this).closest('tr.detail-slide').remove();
        });

        // 등록 폼 토글
        $("#add_btn").click(function () {
            $("#newRoomForm").toggle(300);
        });

        $("#add_cancle").click(function () {
            $("#newRoomForm").hide(300);
        });
    });