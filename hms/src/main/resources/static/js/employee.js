const userRole = '${fn:escapeXml(userRole)}'; // JS에서 사용 가능하도록 안전하게 출력

// 직책(직급) 코드 매핑 객체 (jsp에 따른 명칭으로 맞춤)
const gradeMap = {
    'GR_01': '총지배인',
    'GR_02': '팀장',
    'GR_03': '일반'
};

$(document).ready(function () {

    // 직원 행 클릭 시 상세 폼 출력
    $('.employee-row').on('click', function () {
        const $clickedRow = $(this);
        const $nextRow = $clickedRow.next('.detail-slide');

        if ($nextRow.length > 0) {
            $nextRow.remove(); // 이미 열려있으면 닫기
            return;
        }

        $('.detail-slide').remove(); // 다른 열린 행 제거

        const empId = $clickedRow.data('id');
        const name = $clickedRow.data('name');
        const dept = $clickedRow.data('dept');
        const grade = $clickedRow.data('grade');
        const phone = $clickedRow.find('td[data-label="전화번호"]').text().trim();

        const gradeLabel = gradeMap[grade] || grade;

        const $detailRow = $(`
            <tr class="detail-slide">
                <td colspan="6">
                    <table style="width:100%; border-collapse: collapse;">
                        <tr>
                            <th style="padding:8px;">직원 번호</th>
                            <td><input type="text" value="${empId}" readonly></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">이름</th>
                            <td><input type="text" value="${name}"></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">부서</th>
                            <td><input type="text" value="${dept}"></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">직책</th>
                            <td><input type="text" value="${gradeLabel}"></td>
                        </tr>
                        <tr>
                            <th style="padding:8px;">전화번호</th>
                            <td><input type="text" value="${phone}"></td>
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
        $("#newEmployeeForm").toggle(300);
    });

    $("#add_cancel").click(function () {
        $("#newEmployeeForm").hide(300);
    });

});
