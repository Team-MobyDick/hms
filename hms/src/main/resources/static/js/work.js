
let deptMap = [];
let impoMap = [];
let deptList = [];
let impoList = [];

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

    // 부서 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/deptTypes', // 서버에서 방 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            deptList = data;
            deptList.forEach(rt => {
                deptMap[rt.codeId] = rt.codeName; // map에 추가
            });

            // 방 타입을 등록 폼의 셀렉트 박스에 추가
            populateDept();
        },
        error: function() {
            console.error("부서 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 중요도 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/impoTypes', // 서버에서 방 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            impoList = data;
            impoList.forEach(rt => {
                impoMap[rt.codeId] = rt.codeName; // map에 추가
            });

            // 방 타입을 등록 폼의 셀렉트 박스에 추가
            populateImpo();
        },
        error: function() {
            console.error("중요도 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 주업무 클릭 → 상세 업무 열기
    $('.workM-row').on('click', function () {
        const $clickedRow = $(this);
        const workMId = $clickedRow.data('workm-id');
        const rawDate = $clickedRow.data('date');
        const date = rawDate.replaceAll('/', '');

        const $existingRows = $(`.workD-row[data-parent="${workMId}"]`);

        // 이미 열려있으면 닫기
        if ($existingRows.length > 0) {
            $existingRows.slideUp(200, function () {
                $(this).remove();
            });
            return;
        }

//        $(`.workD-row[data-parent="${workMId}"]`).slideUp(200, function () {
//            $(this).remove();
//        }); // 기존 열림 닫기

        $.ajax({
            url: '/work/detailWorkList',
            method: 'GET',
            data: { workMId, date },
            dataType: 'json',
            success: function (detailWorks) {
                if (detailWorks.length === 0) {
                    const emptyRow = $(`
                        <tr class="workD-row" data-parent="${workMId}">
                            <td colspan="6" style="background:#f9f9f9;">상세업무가 없습니다.</td>
                        </tr>
                    `);
                    $clickedRow.after(emptyRow);
                } else {
                    const detailRows = [];
                    detailWorks.forEach(detail => {
                        const detailRow = $(`
                            <tr class="workD-row"
                                data-parent="${workMId}"
                                data-workd-id="${detail.workDId}"
                                style="display:none; background:#f0f0f0;">
                                <td data-label="level"></td>
                                <td data-label="업무명">${detail.workDName}</td>
                                <td data-label="부서">${detail.workDDeptN}</td>
                                <td data-label="담당자">${detail.emplName}</td>
                                <td data-label="중요도">${detail.workDImpoN}</td>
                                <td data-label="일자">${detail.workDDate}</td>
                            </tr>
                        `);
                        detailRows.push(detailRow);
                    });

                    // 역순으로 삽입 (순서 보존)
                    for (let i = detailRows.length - 1; i >= 0; i--) {
                        $clickedRow.after(detailRows[i]);
                        detailRows[i].slideDown(200);
                    }
                }
            },
            error: function () {
                alert('상세업무를 불러오는 데 실패했습니다.');
            }
        });
    });

//    // 상세 업무 클릭 -> 수정/확인 폼 열기
//    $('.workD-row').on('click', function () {
//        const $clickedDRow = $(this);
//        const $nextRow = $clickedDRow.next('.detail-slide');
//
//        if ($nextRow.length > 0) {
//            $nextRow.remove();  // 다시 클릭 시 닫기
//            return;
//        }
//
//        $('.detail-slide').remove(); // 기존 열림 닫기
//
//        const $detailRow = $(`
//            <form class="detail-slide" style="width: 320px; padding: 16px; border: 1px solid #ccc; border-radius: 8px; font-family: sans-serif;">
//                <h3 style="margin-bottom: 12px;">할 일 상세보기/수정</h3>
//
//                <div style="display: flex; gap: 8px; margin-bottom: 12px;">
//                <button>수정</button>
//                <button>취소</button>
//                <button>삭제</button>
//                </div>
//
//                <div class="form-group">
//                <label>업무명</label>
//                <input type="text" value="101호 객실 청소" />
//                </div>
//
//                <div class="form-group">
//                <label>담당자</label>
//                <select>
//                  <option>최영범</option>
//                  <!-- 다른 인원 옵션 -->
//                </select>
//                </div>
//
//                <div class="form-group">
//                <label>업무일자</label>
//                <input type="date" value="2025-06-12" />
//                </div>
//
//                <div class="form-group">
//                <label>객실번호</label>
//                <input type="text" value="101호" />
//                </div>
//
//                <div class="form-group">
//                <label>중요도</label>
//                <select>
//                  <option>높음</option>
//                  <!-- 중간, 낮음 등 -->
//                </select>
//                </div>
//
//                <div class="form-group">
//                <label>업무 내용</label>
//                <textarea rows="3">아주 중요한 일임
//                팀장: 진짜임</textarea>
//                </div>
//
//                <div class="form-group" style="display: flex; gap: 12px; align-items: center;">
//                <div style="text-align: center;">
//                  <label>시작사진</label><br />
//                  <button style="border-radius: 50%; width: 60px; height: 60px;">+</button><br />
//                  <small>시작시간</small>
//                </div>
//                <div style="text-align: center;">
//                  <label>종료사진</label><br />
//                  <button style="border-radius: 50%; width: 60px; height: 60px;">+</button><br />
//                  <small>종료시간</small>
//                </div>
//                </div>
//
//                <div class="form-group" style="margin-top: 12px;">
//                <label><input type="checkbox" checked /> 문제발생</label>
//                </div>
//
//                <div class="form-group">
//                <label>특이사항</label>
//                <textarea rows="2">변기 뚫느라 시간이 오래걸렸습니다</textarea>
//                </div>
//            </form>
//        `);
//
//        $clickedDRow.after($detailRow);
//    });


    // 주 업무 등록 폼 토글
    $('#add_btn').click(function () {
        $('#newWorkMForm').toggle(300);
    });

    $('#add_cancle').click(function () {
        $('#newWorkMForm').hide(300);
    });

    // 주 업무 등록 폼의 셀렉트 박스 채우기
    function populateDept() {
        const $deptSelect = $('select[name="dept"]');
        $deptSelect.empty(); // 기존 옵션을 지움

        deptList.forEach(rt => {
            $deptSelect.append(`<option value="${rt.codeId}">${rt.codeName}</option>`);
        });
    }
    function populateImpo() {
        const $impoSelect = $('select[name="impo"]');
        $impoSelect.empty(); // 기존 옵션을 지움

        impoList.forEach(rt => {
            $impoSelect.append(`<option value="${rt.codeId}">${rt.codeName}</option>`);
        });
    }
});

// workD-row 클릭 시 하위 상세정보 폼 토글
$(document).on('click', '.workD-row', function () {
    const $clickedDetailRow = $(this);
    const workDId = $clickedDetailRow.data('workd-id');
    const $next = $clickedDetailRow.next('.workD-detail');

    // 기존 열림 닫기
    $('.workD-detail').slideUp(200, function () {
        $(this).remove();
    });

    // 이미 열려 있으면 닫기
    if ($next.length > 0) {
        $next.slideUp(200, function () {
            $next.remove();
        });
        return;
    }

    // 예: 간단한 폼을 삽입
    const detailFormRow = $(`
        <tr class="workD-detail" style="width: 320px; padding: 16px; border: 1px solid #ccc; border-radius: 8px; font-family: sans-serif;">
            <td colspan="6" style="padding: 10px;">
                <h3 style="margin-bottom: 12px;">할 일 상세보기/수정</h3>

                <div style="display: flex; gap: 8px; margin-bottom: 12px;">
                <button>수정</button>
                <button>취소</button>
                <button>삭제</button>
                </div>

                <div class="form-group">
                <label>업무명</label>
                <input type="text" value="101호 객실 청소" />
                </div>

                <div class="form-group">
                <label>담당자</label>
                <select>
                  <option>최영범</option>
                  <!-- 다른 인원 옵션 -->
                </select>
                </div>

                <div class="form-group">
                <label>업무일자</label>
                <input type="date" value="2025-06-12" />
                </div>

                <div class="form-group">
                <label>객실번호</label>
                <input type="text" value="101호" />
                </div>

                <div class="form-group">
                <label>중요도</label>
                <select>
                  <option>높음</option>
                  <!-- 중간, 낮음 등 -->
                </select>
                </div>

                <div class="form-group">
                <label>업무 내용</label>
                <textarea rows="3">아주 중요한 일임
                팀장: 진짜임</textarea>
                </div>

                <div class="form-group" style="display: flex; gap: 12px; align-items: center;">
                <div style="text-align: center;">
                  <label>시작사진</label><br />
                  <button style="border-radius: 50%; width: 60px; height: 60px;">+</button><br />
                  <small>시작시간</small>
                </div>
                <div style="text-align: center;">
                  <label>종료사진</label><br />
                  <button style="border-radius: 50%; width: 60px; height: 60px;">+</button><br />
                  <small>종료시간</small>
                </div>
                </div>

                <div class="form-group" style="margin-top: 12px;">
                <label><input type="checkbox" checked /> 문제발생</label>
                </div>

                <div class="form-group">
                <label>특이사항</label>
                <textarea rows="2">변기 뚫느라 시간이 오래걸렸습니다</textarea>
                </div>
            </td>
        </tr>
    `);

    $clickedDetailRow.after(detailFormRow);
    detailFormRow.hide().slideDown(200);
});

