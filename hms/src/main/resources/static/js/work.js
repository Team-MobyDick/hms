

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