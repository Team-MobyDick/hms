
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
    $('.workM-row').on('click', function (e) {
        // 버튼 클릭으로 유입되었으면 무시
        if ($(e.target).closest('button').length > 0) return;

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


    // 주 업무 등록 폼 토글
    $('#add_btn_M').click(function () {
        $('#newWorkMForm').toggle(300);
    });

    $('#add_cancle_M').click(function () {
        $('#newWorkMForm').hide(300);
    });

    // 업무 배분 폼 토글
    $('#add_btn').click(function () {
        $('#newWorkMForm').toggle(300);
    });

    $('#add_cancle').click(function () {
        $('#newWorkMForm').hide(300);
    });

    // 주 업무 등록 폼의 셀렉트 박스 채우기
    function populateDept() {
        const $deptSelect = $('select[name="workMDept"]');
        $deptSelect.empty(); // 기존 옵션을 지움

        deptList.forEach(rt => {
            $deptSelect.append(`<option value="${rt.codeId}">${rt.codeName}</option>`);
        });
    }
    function populateImpo() {
        const $impoSelect = $('select[name="workMImpo"]');
        $impoSelect.empty(); // 기존 옵션을 지움

        impoList.forEach(rt => {
            $impoSelect.append(`<option value="${rt.codeId}">${rt.codeName}</option>`);
        });

    }// 등록 폼 제출
        $('#newWorkMForm').on('submit', function (e) {
         e.preventDefault();

         const data = {
             workMName: $('input[name="workMName"]').val(),
             workMDept: $('select[name="workMDept"]').val(),
             workMImpo: $('select[name="workMImpo"]').val(),
             workMContext: $('textarea[name="workMContext"]').val(),
         };

         if (!data.workMName || !data.workMDept || !data.workMImpo) {
             alert("업무 이름과 부서, 중요도는 필수입니다.");
             return;
         }

         $.ajax({
             type: 'POST',
             url: '/work/addWorkM',
             contentType: 'application/json',
             data: JSON.stringify(data),
             success: function () {
                 alert('주 업무가 등록되었습니다.');
                 location.reload();
             },
             error: function () {
                 alert('등록 실패. 입력 값을 확인해주세요.');
             }
         });
        });
});

// #add_btn_D 클릭시 업무 배분 등록폼 열기
$(document).on('click', '#add_btn_D', function (e) {
    e.stopPropagation(); // 상위 tr 클릭 이벤트 방지

    const $button = $(this);
    const $row = $button.closest('tr');
    const workMId = $row.data('workm-id');
    const workMName = $row.data('workm-name');
    const workMDept = $row.data('workm-dept');
    const workMContext = $row.data('workm-context');


    // 이미 열려있는 등록 폼 제거
    $('.newWorkDForm').slideUp(200, function () {
        $(this).remove();
    });

    // 이미 해당 row 밑에 폼이 있다면 return
    if ($row.next().hasClass('newWorkDForm')) return;

    const formRow = $(`
        <tr class="newWorkDForm">
            <td colspan="6" style="background:#eef; padding:10px;">
                <form class="workDForm">
                    <input type="hidden" name="workMId" value="${workMId}" />
                    <label>업무명
                        <input type="text" name="workDName" value="${workMName}" />
                    </label>
                    <label>담당자
                        <select name="workDEmplId">
                            <option value="BAB">밥</option>
                            <option value="asdf">asdf</option>
                            <option value="ADMIN">최영범</option>
                        </select>
                    </label>
                    <label>업무일자
                        <input type="date" name="workDDate" />
                    </label>
                    <label>객실
                        <select name="workDRoomId">
                            <option value="001000000000067157">103</option>
                            <option value="001000000000024906">104</option>
                            <option value="001000000000046356">105</option>
                        </select>
                    </label>
                    <label>중요도
                        <select name="workDImpo">
                            <option value="IM_01">낮음</option>
                            <option value="IM_02">보통</option>
                            <option value="IM_03">높음</option>
                        </select>
                    </label>
                    <label>업무내용
                        <textarea name="workDContext">${workMContext}</textarea>
                    </label>
                    <button type="submit">등록</button>
                    <button type="button" class="add_cancle_D">취소</button>
                </form>
            </td>
        </tr>
    `);

    $row.after(formRow);
    formRow.hide().slideDown(200);
});
$(document).on('click', '.add_cancle_D', function () {
    $(this).closest('tr.newWorkDForm').slideUp(200, function () {
        $(this).remove();
    });
});

// workD-row 클릭 시 하위 상세정보 폼 토글
//$(document).on('click', '.workD-row', function () {
//    const $clickedDetailRow = $(this);
//    const workDId = $clickedDetailRow.data('workd-id');
//    const $next = $clickedDetailRow.next('.workD-detail');
//
//    // 기존 열림 닫기
//    $('.workD-detail').slideUp(200, function () {
//        $(this).remove();
//    });
//
//    // 이미 열려 있으면 닫기
//    if ($next.length > 0) {
//        $next.slideUp(200, function () {
//            $next.remove();
//        });
//        return;
//    }
//
//    const detailFormRow = $(`
//        <tr class="workD-detail" style="width: 320px; padding: 16px; border: 1px solid #ccc; border-radius: 8px; font-family: sans-serif;">
//            <td colspan="6" style="padding: 10px;">
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
//            </td>
//        </tr>
//    `);
//
//    $clickedDetailRow.after(detailFormRow);
//    detailFormRow.hide().slideDown(200);
//});

