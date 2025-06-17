
document.addEventListener("DOMContentLoaded", function () {
    // 서버에서 넘긴 날짜
    const dateStr = document.getElementById("serverDate").value;
    let currentDate = new Date(dateStr);

    function updateDateDisplay() {
        const yyyy = currentDate.getFullYear();
        const mm = String(currentDate.getMonth() + 1).padStart(2, '0');
        const dd = String(currentDate.getDate()).padStart(2, '0');
        const formatted = `${yyyy}-${mm}-${dd}`;
        document.getElementById("dateText").textContent = formatted;
        document.getElementById("datePicker").value = formatted;
    }

    // 날짜 클릭 시 input[type=date] 보이기
    $('#dateText').on('click', function () {
        $('#dateText').addClass('hidden');
        $('#datePicker').removeClass('hidden').focus();
    });

    // input에서 날짜 선택 시 반영
    $('#datePicker').on('change', function () {
        currentDate = new Date($(this).val());
        updateDateDisplay();
        $('#datePicker').addClass('hidden');
        $('#dateText').removeClass('hidden');
    });

    // 하루씩 이동
    $('#prevBtn').on('click', function () {
        currentDate.setDate(currentDate.getDate() - 1);
        updateDateDisplay();
    });

    $('#nextBtn').on('click', function () {
        currentDate.setDate(currentDate.getDate() + 1);
        updateDateDisplay();
    });

    // 초기 화면 표시
    updateDateDisplay();
});

$(document).ready(function () {

    // 주업무 클릭 → 상세 업무 열기
    $('.workM-row').on('click', function () {
        const $clickedRow = $(this);
        const $nextRow = $clickedRow.next('.workD-row');

        if ($nextRow.length > 0) {
            $nextRow.remove();  // 다시 클릭 시 닫기
            return;
        }

        $('.workD-row').remove(); // 기존 열림 닫기

        $.ajax({
            url: '/getSubWorks', // 실제 엔드포인트로 교체
            method: 'GET',
            data: { workMId: workMId },
            dataType: 'json',
            success: function (subWorks) {
                if (subWorks.length === 0) {
                    const emptyRow = $(`
                        <tr class="detail-row">
                            <td colspan="6" style="background:#f9f9f9;">상세업무가 없습니다.</td>
                        </tr>
                    `);
                    $row.after(emptyRow);
                } else {
                    const detailRows = [];
                    subWorks.forEach(sub => {
                        const detailRow = $(`
                            <tr class="detail-row" style="display:none;">
                                <td colspan="6" style="padding: 10px; background:#f0f0f0;">
                                    <strong>업무명:</strong> ${sub.name}<br>
                                    <strong>부서:</strong> ${sub.dept}<br>
                                    <strong>중요도:</strong> ${sub.impo}<br>
                                    <strong>일자:</strong> ${sub.date}
                                </td>
                            </tr>
                        `);
                        detailRows.push(detailRow);
                    });

                    // 역순으로 삽입 (순서 보존)
                    for (let i = detailRows.length - 1; i >= 0; i--) {
                        $row.after(detailRows[i]);
                        detailRows[i].slideDown();
                    }
                }
            },
            error: function () {
                alert('상세업무를 불러오는 데 실패했습니다.');
            }
        });
    });
});
