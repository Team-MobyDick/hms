<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    // 임시 사용자 역할 지정 (예: 'ADMIN', 'USER')
    const userRole = 'test';

    // 역할에 따른 버튼 생성 함수
    function getButtonsByRole(role) {
        if (role === 'test') {
            return `
                <button class="action-btn edit-btn">수정</button>
                <button class="action-btn delete-btn">삭제</button>
            `;
        } else {
            return `
                <button class="action-btn close-btn">닫기</button>
            `;
        }
    }

    // 문서가 준비되면 실행
    $(document).ready(function () {

        // 이벤트 위임 방식으로 클릭 이벤트 바인딩
        $(document).on('click', '.ann-row', function () {
            const $clickedRow = $(this);
            const $nextRow = $clickedRow.next('.detail-slide');

            if ($nextRow.length > 0) {
                $nextRow.remove(); // 슬라이드 닫기
                return;
            }

            $('.detail-slide').remove(); // 다른 열려있는 슬라이드 제거

            const title = $clickedRow.data('title');
            const write = $clickedRow.data('writer');
            const content = $clickedRow.data('content');
            const date = $clickedRow.data('date');

            const $detailRow = $('<tr class="detail-slide"><td colspan="3"></td></tr>');
            const $slideContent = $('<div class="slide-content"></div>');

            $slideContent.append('<strong>제목: </strong><input type="text" value="' + title + '" readonly><br>');
            $slideContent.append('<strong>작성자: </strong><input type="text" value="' + write + '" readonly><br>');
            $slideContent.append('<strong>내용:</strong><br><textarea class="content-textarea" readonly>' + content + '</textarea><br>');
            $slideContent.append('<strong>작성일: </strong><input type="text" value="' + date + '" readonly><br>');

            // 버튼 렌더링
            $slideContent.append(getButtonsByRole(userRole));

            $detailRow.find('td').append($slideContent);
            $clickedRow.after($detailRow);
        });

        // 닫기 버튼 이벤트
        $(document).on('click', '.close-btn', function () {
            $(this).closest('.detail-slide').remove();
        });

        // 수정 버튼 예시
        $(document).on('click', '.edit-btn', function () {
            alert('수정 기능은 추후 구현 예정입니다.');
        });

        // 삭제 버튼 예시
        $(document).on('click', '.delete-btn', function () {
            if (confirm('정말로 삭제하시겠습니까?')) {
                alert('삭제 처리 호출 (예시)');
                $(this).closest('.detail-slide').prev('.ann-row').remove();
                $(this).closest('.detail-slide').remove();
            }
        });

    });
</script>
