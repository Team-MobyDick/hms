const userRole = userRoleJs;
const userDept = userDeptJs;

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

    // 정렬 버튼 클릭 이벤트
    $('.sort-btn').on('click', function() {
        // data-sort-order 속성에서 값 가져오기
        const selectedSortOrder = $(this).data('sort-order');

        // 현재 URL 가져오기
        const currentUrl = new URL(window.location.href);

        // sortOrder 파라미터 업데이트 또는 추가
        currentUrl.searchParams.set('sortOrder', selectedSortOrder);

        // 페이지 번호를 1로 초기화 (정렬 기준이 바뀌면 보통 첫 페이지로 이동)
        currentUrl.searchParams.set('page', '1');

        // 업데이트된 URL로 이동
        window.location.href = currentUrl.toString();

    });

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
        const phone = $clickedRow.data('phone');
        const note = $clickedRow.data('note');
        const createdDate = $clickedRow.data('created-date');
        const photoName = $clickedRow.data('photo-name'); // 기존 사진 파일명
        const photoPath = $clickedRow.data('photo-path'); // 기존 사진 경로
        const retiredYn = $clickedRow.data('retired-yn');

        // 상세 폼 HTML 생성 (수정 가능 input/select 포함 및 사진 업로드 섹션 추가)
        let detailHTML = `
            <tr class="detail-slide">
                <td colspan="8">
                    <div class="detail-content-wrapper">
                        <div class="detail-photo-area">
                            <div class="photo-container" id="detailPhotoContainer">
                                ${photoName ? `<img src="${contextPath}/${photoPath}/${photoName}" alt="사진">` : ''}
                            </div>
                            <div class="upload-section" id="uploadSection" style="display: none;">
                                <button type="button" class="action-btn select-photo-button">사진 선택</button>
                                <input type="file" id="photoUploadInput_${empId}" class="photo-upload-input" name="photo" accept="image/*" style="display: none;">
                                <button type="button" class="action-btn upload-photo-btn" data-empid="${empId}">업로드</button>
                                <p style="font-size: 0.85em; color: #666; margin-top: 8px;">(JPEG, PNG, GIF, JFIF 형식, 최대 5MB 파일만 업로드 가능합니다)</p>
                            </div>
                        </div>
                        <div class="detail-info-area">
                            <table style="width:100%; border-collapse: collapse;">
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
                                <tr><th>등록일</th><td>${createdDate}</td></tr>
                                <tr><th>퇴사 여부</th><td>${retiredYn === 'Y' ? '<span style="color: red; font-weight: bold;">퇴사</span>' : '<span>재직</span>'}</td></tr>
                                <tr><td colspan="2" style="text-align: center;" class="button-area"></td></tr>
                            </table>
                        </div>
                    </div>
                </td>
            </tr>
        `;

        const $detailRow = $(detailHTML);
        const $buttonArea = $detailRow.find('.button-area');
        const $uploadSection = $detailRow.find('#uploadSection');

        // 권한별 버튼 노출 및 사진 업로드 섹션 표시(총지배인일 경우 수정, 삭제 버튼 표시)
        if (userRole === 'GR_01') {
            $buttonArea.append('<button class="action-btn btn-update">수정</button> ');
            $buttonArea.append('<button class="action-btn btn-delete">삭제</button> ');

            // 총지배인만 퇴사처리 버튼을 볼 수 있고 해당 직원이 재직 중일 때만 활성화
            if (retiredYn === 'N') {
                $buttonArea.append(`<button class="action-btn btn-retire" data-empid="${empId}">퇴사처리</button> `);
            } else {
                // 이미 퇴사한 직원은 퇴사처리 버튼 대신 비활성화된 메시지 표시
                $buttonArea.append('<button class="action-btn" disabled style="background-color: #ccc;">이미 퇴사 처리됨</button> ');
            }

            // 총지배인은 항상 사진 업로드 섹션 표시
            $uploadSection.show();
        }

        // 팀장은 같은 부서의 직원에게만 수정 가능(퇴사하지 않은 경우) 및 사진 업로드 섹션 표시
        else if (userRole === 'GR_02') {
            // 팀장은 재직 중인 같은 부서 직원만 수정 가능
            if (userDept === dept && retiredYn === 'N') {
                $buttonArea.append('<button class="action-btn btn-update">수정</button> ');
                $uploadSection.show();
            } else {
                // 퇴사했거나 다른 부서 직원은 수정 버튼 비활성화
                $buttonArea.append('<button class="action-btn" disabled style="background-color: #ccc;">수정 권한 없음</button> ');
            }
        }

        // 닫기 버튼 권한 상관 없이 모두 표시
        $buttonArea.append('<button class="action-btn btn-close">닫기</button>');

        $clickedRow.after($detailRow);
    });

    // 사진 선택 버튼 클릭 시 실제 파일 입력 필드 트리거
    // 동적으로 생성되는 요소에 이벤트를 바인딩하므로 $(document).on 사용
    $(document).on('click', '.select-photo-button', function() {
        // 클릭된 버튼과 연관된 input[type="file"]의 ID를 동적으로 찾아 클릭
        // 현재 버튼의 부모인 .upload-section 내에서 .photo-upload-input을 찾음
        $(this).closest('.upload-section').find('.photo-upload-input').trigger('click');
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

        const formData = new FormData(this);
        formData.append('retiredYn', 'N');

        $.ajax({
            url: contextPath + '/employee/register',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
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

    // 상세 폼 수정 버튼 제출 (AJAX) - 일반 정보 수정
    $(document).on('click', '.btn-update', function () {
        const $detailTable = $(this).closest('table');
        const $detailSlide = $(this).closest('tr.detail-slide');
        const emplId = $detailTable.find('input[name="emplId"]').val();

        // 현재 직원의 퇴사 여부 가져오기
        const retiredYn = $detailSlide.prev('.employee-row').data('retired-yn');

        // 퇴사된 직원은 수정 불가 (클라이언트 단 유효성 검사)
        if (retiredYn === 'Y') {
            alert('퇴사된 직원은 정보를 수정할 수 없습니다.');
            return;
        }

        const $employeeRow = $detailSlide.prev('.employee-row');
        const photoName = $employeeRow.data("photo-name") || "";
        const photoPath = $employeeRow.data("photo-path") || "employee_photos";

        const formData = {
            emplId: emplId,
            photoName: photoName,
            photoPath: photoPath,
            retiredYn: retiredYn
        };

        $detailTable.find('input:not([name="emplId"]), select, textarea').each(function() {
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

    // 사진 선택 시 미리보기
    $(document).on('change', '.photo-upload-input', function (event) {
        const file = event.target.files[0];
        const $photoContainer = $(this).closest('.detail-photo-area').find('#detailPhotoContainer');

        if (file) {
            // 최대 5MB
            const maxFileSizeMB = 5;
            // 5MB를 바이트로 변환
            const maxFileSizeBytes = maxFileSizeMB * 1024 * 1024;
            // 허용할 파일 타입 목록
            const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/jfif'];

            // 파일 크기 검사
            if (file.size > maxFileSizeBytes) {
                alert(`사진 파일 크기가 너무 큽니다. 최대 ${maxFileSizeMB}MB까지 업로드 가능합니다.`);
                event.target.value = ''; // 파일 선택 취소
                $photoContainer.empty(); // 미리보기 초기화
                return;
            }

            // 파일 타입 검사
            if (!allowedTypes.includes(file.type)) {
                alert('지원하지 않는 사진 파일 형식입니다. JPEG, PNG, GIF, jfif 파일만 업로드 가능합니다.');
                event.target.value = ''; // 파일 선택 취소
                $photoContainer.empty(); // 미리보기 초기화
                return; // 이후 로직 실행 중단
            }

            const reader = new FileReader();
            reader.onload = function (e) {
                $photoContainer.html(`<img src="${e.target.result}" alt="미리보기">`);
            };

            reader.readAsDataURL(file);
        } else {
            // 파일 선택 취소 시 원래 이미지 복원 또는 빈 상태로
            const $clickedRow = $(this).closest('.detail-slide').prev('.employee-row');
            const photoName = $clickedRow.data('photo-name');
            const photoPath = $clickedRow.data('photo-path');

            if (photoName) {
                $photoContainer.html(`<img src="${contextPath}/${photoPath}/${photoName}" alt="사진">`);
            } else {
                $photoContainer.empty();
            }

        }
    });


    // 사진 업로드 버튼 클릭
    $(document).on('click', '.upload-photo-btn', function () {
        const emplId = $(this).data('empid');
        const fileInput = $(`#photoUploadInput_${emplId}`)[0]; // 해당 직원의 파일 input 가져오기
        const file = fileInput.files[0];
        const retiredYn = $(this).closest('.detail-slide').prev('.employee-row').data('retired-yn'); // 퇴사 여부 확인

        // 퇴사된 직원은 사진 수정 불가
        if (retiredYn === 'Y') {
            alert('퇴사된 직원의 사진은 수정할 수 없습니다.');
            return;
        }

        if (!file) {
            alert('업로드할 사진 파일을 선택해주세요.');
            return;
        }

        const maxFileSizeMB = 5;

        if (file.size > maxFileSizeMB * 1024 * 1024) {
            alert(`파일 크기가 너무 큽니다. 최대 ${maxFileSizeMB}MB까지 업로드 가능합니다.`);
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/jfif'];

        if (!allowedTypes.includes(file.type)) {
            alert('지원하지 않는 파일 형식입니다. JPEG, PNG, GIF, JFIF 파일만 업로드 가능합니다.');
            return;
        }

        const formData = new FormData();
        formData.append('emplId', emplId);
        formData.append('photo', file);

        $.ajax({
            url: contextPath + '/employee/uploadPhoto',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: (response) => {
                const parts = response.split('::');
                const message = parts[0];
                const newPhotoName = parts[1];
                const newPhotoPath = parts[2];

                alert(message);

                // UI 업데이트: 상세보기의 사진을 바로 변경
                const $detailPhotoContainer = $(this).closest('.detail-photo-area').find('#detailPhotoContainer');
                $detailPhotoContainer.html(`<img src="${contextPath}/${newPhotoPath}/${newPhotoName}" alt="직원 사진">`);

                // 목록의 해당 직원의 사진도 업데이트 (새로고침 없이)
                $(`.employee-row[data-id="${emplId}"]`).data('photo-name', newPhotoName);
                $(`.employee-row[data-id="${emplId}"]`).data('photo-path', newPhotoPath);
                $(`.employee-row[data-id="${emplId}"] .photo-container`).html(`<img src="${contextPath}/${newPhotoPath}/${newPhotoName}" alt="사진" />`);

                // 파일 input 초기화 (같은 파일 다시 선택 가능하도록)
                fileInput.value = "";
            },

            error: (xhr, status, error) => {
                let errorMessage = '사진 업로드 중 오류가 발생했습니다.';

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
        const retiredYn = $(this).closest('.detail-slide').prev('.employee-row').data('retired-yn'); // 퇴사 여부 확인

        // 총지배인만 퇴사된 직원 삭제 가능
        if (userRole !== 'GR_01' && retiredYn === 'Y') {
            alert('퇴사된 직원은 총지배인만 영구 삭제할 수 있습니다.');
            return;
        }

        if (confirm(`직원 ID: ${emplIdToDelete} 을(를) 정말 삭제하시겠습니까?`)) {
            $.ajax({
                url: contextPath + '/employee/delete',
                type: 'POST',
                data: { emplId: emplIdToDelete },
                success: (response) => {
                    alert(response);
                    location.reload();
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

    // 퇴사처리
    $(document).on('click', '.btn-retire', function () {

        // 버튼의 data-empid 속성에서 직원 ID 가져오기
        const emplIdToRetire = $(this).data('empid');

        if (confirm(`직원 ID: ${emplIdToRetire} 을(를) 정말 퇴사 처리하시겠습니까? (이 작업은 되돌릴 수 없습니다.)`)) {
            $.ajax({
                url: contextPath + '/employee/retire',
                type: 'POST',
                data: { emplId: emplIdToRetire }, // 퇴사시킬 직원 ID 전송
                success: (response) => {
                    alert(response);
                    location.reload();
                },
                error: (xhr, status, error) => {
                    let errorMessage = '직원 퇴사 처리 중 오류가 발생했습니다.';

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

const modal = document.querySelector('.modal');

const modalClose = document.querySelector('.close_btn');

modalClose.addEventListener('click', function () {
    modal.classList.remove('on');
    document.getElementById("qrcode").innerHTML = "";
});

document.querySelectorAll('.printQR').forEach(function (btn) {
    btn.addEventListener('click', function (e) {
        const row = e.target.closest('tr');
        const emplId = row.getAttribute('data-id');

        const qrContainer = document.getElementById("qrcode");

        qrContainer.innerHTML = "";

        // QRCode 라이브러리
        new QRCode(qrContainer, {
            text: emplId,
            width: 300,
            height: 300
        });

        modal.classList.add('on');
    });
});

const printBtn = document.querySelector('.print_btn');

printBtn.addEventListener('click', function () {
    const qrContainer = document.getElementById("qrcode");
    const qrHtml = qrContainer.innerHTML;

    const printWindow = window.open('', '', 'width=400,height=500');

    printWindow.document.write(`
        <html>
        <head>
            <title>QR 코드 출력</title>
            <style>
                body { text-align: center; margin-top: 50px; }
                canvas, img { width: 300px; height: 300px; }
            </style>
        </head>
        <body>
        <body>
            ${qrHtml}
            <script>
                window.onload = function () {
                    window.print();
                    window.onafterprint = function () { window.close(); };
                }
            <\/script>
        </body>
        </html>
    `);

    printWindow.document.close();
});