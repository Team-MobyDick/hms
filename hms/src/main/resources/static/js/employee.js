const userRole = userRoleJs;

const gradeMap = {
    'GR_01': '총지배인',
    'GR_02': '팀장',
    'GR_03': '일반'
};

const deptMap = {
    'DP_01': '하우스키핑',
    'DP_02': '시설관리',
    'DP_03': '프론트'
};

$(document).ready(function () {

    // 직원 행 클릭 - 상세 폼 토글
    $('.employee-row').on('click', function () {
        const $clickedRow = $(this);
        const $nextRow = $clickedRow.next('.detail-slide');

        // 이미 상세 폼 열려있으면 닫기
        if ($nextRow.length > 0) {
            $nextRow.remove();
            return;
        }

        // 다른 상세 폼 닫기
        $('.detail-slide').remove();

        // 직원 데이터 가져오기
        const empId = $clickedRow.data('id');
        const name = $clickedRow.data('name');
        const dept = $clickedRow.data('dept');
        const grade = $clickedRow.data('grade');
        const phone = $clickedRow.find('td[data-label="전화번호"]').text().trim();
        const note = $clickedRow.data('note');

        const gradeLabel = gradeMap[grade] || grade;
        const deptLabel = deptMap[dept] || dept;

        // 상세 폼 HTML 생성 (수정 가능 input/select 포함)
        const $detailRow = $(`
            <tr class="detail-slide">
                <td colspan="7"> <table style="width:100%; border-collapse: collapse;">
                        <tr><th style="padding:8px;">직원 ID</th><td><input type="text" name="emplId" value="${empId}" readonly></td></tr>
                        <tr><th style="padding:8px;">이름</th><td><input type="text" name="emplName" value="${name}"></td></tr>
                        <tr><th style="padding:8px;">부서</th>
                            <td>
                                <select name="emplDept">
                                    <option value="DP_01" ${dept==='DP_01' ? 'selected' : ''}>하우스키핑</option>
                                    <option value="DP_02" ${dept==='DP_02' ? 'selected' : ''}>시설관리</option>
                                    <option value="DP_03" ${dept==='DP_03' ? 'selected' : ''}>프론트</option>
                                </select>
                            </td>
                        </tr>
                        <tr><th style="padding:8px;">직책</th>
                            <td>
                                <select name="emplGrade">
                                    <option value="GR_01" ${grade==='GR_01' ? 'selected' : ''}>총지배인</option>
                                    <option value="GR_02" ${grade==='GR_02' ? 'selected' : ''}>팀장</option>
                                    <option value="GR_03" ${grade==='GR_03' ? 'selected' : ''}>일반</option>
                                </select>
                            </td>
                        </tr>
                        <tr><th style="padding:8px;">전화번호</th><td><input type="text" name="emplPhone" value="${phone}"></td></tr>
                        <tr><th style="padding:8px;">메모</th><td><input type="text" name="emplNotes" value="${note}"></td></tr>
                        <tr><td colspan="2" style="text-align: center;" class="button-area"></td></tr>
                    </table>
                </td>
            </tr>
        `);

        const $buttonArea = $detailRow.find('.button-area');

        // 권한별 버튼 노출
        if (userRole === 'GR_01') {
            $buttonArea.append('<button class="action-btn btn-update">수정</button> ');
            $buttonArea.append('<button class="action-btn btn-delete">삭제</button> ');
        } else if (userRole === 'GR_02') {
            $buttonArea.append('<button class="action-btn btn-update">수정</button> ');
        }
        $buttonArea.append('<button class="action-btn btn-close">닫기</button>');

        $clickedRow.after($detailRow);
    });

    // 상세 폼 닫기 버튼
    $(document).on('click', '.btn-close', function () {
        $(this).closest('tr.detail-slide').remove();
    });

    // 신규 등록 폼 열기/닫기
    $('#add_btn').click(() => $('#newEmployeeForm').toggle(300));
    $('#add_cancel').click(() => {
        $('#newEmployeeForm')[0].reset();
        $('#newEmployeeForm').hide(300);
    });

    // 신규 등록 폼 제출
    $('#newEmployeeForm').on('submit', function (e) {
        e.preventDefault();

        const formData = $(this).serialize();

        $.ajax({
            url: contextPath + '/employee/register',
            type: 'POST',
            data: formData,
            success: (response) => {
                alert(response);
                location.reload();
            },
            error: (xhr, status, error) => {
                let errorMessage = '등록 중 오류가 발생했습니다.';
                if (xhr.responseText) {
                    errorMessage += ': ' + xhr.responseText;
                } else if (error) {
                    errorMessage += ': ' + error;
                }
                alert(errorMessage);
                console.error("AJAX Error:", status, error, xhr.responseText);
            }
        });
    });

    // 상세 폼 수정 버튼 제출 (AJAX)
    $(document).on('click', '.btn-update', function () {
        const $detailTable = $(this).closest('table');
        const formData = {};
        $detailTable.find('input, select, textarea').each(function() {
            formData[$(this).attr('name')] = $(this).val();
        });

        $.ajax({
            url: contextPath + '/employee/update',
            type: 'POST',
            data: formData,
            success: (response) => {
                alert(response);
                location.reload();
            },
            error: (xhr, status, error) => {
                let errorMessage = '직원 정보 수정 중 오류가 발생했습니다.';
                if (xhr.responseText) {
                    errorMessage += ': ' + xhr.responseText;
                } else if (error) {
                    errorMessage += ': ' + error;
                }
                alert(errorMessage);
                console.error("AJAX Error:", status, error, xhr.responseText);
            }
        });
    });

    // 상세 폼 삭제 버튼 제출 (AJAX)
    $(document).on('click', '.btn-delete', function () {
        const $detailTable = $(this).closest('table');
        const emplIdToDelete = $detailTable.find('input[name="emplId"]').val();

        if (confirm(`직원 ID: ${emplIdToDelete} 을(를) 정말 삭제하시겠습니까?`)) {
            $.ajax({
                url: contextPath + '/employee/delete',
                type: 'POST',
                data: { emplId: emplIdToDelete },
                success: (response) => {
                    alert(response);
                    location.reload(); // 페이지 새로고침하여 삭제된 정보 반영
                },
                error: (xhr, status, error) => {
                    let errorMessage = '직원 삭제 중 오류가 발생했습니다.';
                    if (xhr.responseText) {
                        errorMessage += ': ' + xhr.responseText;
                    } else if (error) {
                        errorMessage += ': ' + error;
                    }
                    alert(errorMessage);
                    console.error("AJAX Error:", status, error, xhr.responseText);
                }
            });
        }
    });
});