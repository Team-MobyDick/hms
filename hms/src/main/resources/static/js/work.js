
let deptMap = [];
let impoMap = [];
let roomMap = [];
let emplMap = [];
let deptList = [];
let impoList = [];
let roomList = [];
let emplList = [];

document.addEventListener("DOMContentLoaded", function () {
    // 서버에서 넘긴 날짜
    const dateStr = document.getElementById("serverDate").value;
    let currentDate = new Date(dateStr);

    function renderWorkTable(res, formattedDate) {
        const $tbody = $('#workTableBody').empty();

        if (res.workMList && res.workMList.length) {
            res.workMList.forEach(m => {
                let addButton = '';
                if (userRole === 'GR_01' || userRole === 'GR_02') {
                    addButton = '<button class="add_btn_D">업무 배분</button>';
                }

                $tbody.append(`
                    <tr class="workM-row"
                        data-workm-id="${m.workMId}"
                        data-workm-name="${m.workMName}"
                        data-workm-dept="${m.workMDept}"
                        data-workm-deptname="${res.codeMap[m.workMDept]}"
                        data-workm-impo="${m.workMImpo}"
                        data-workm-imponame="${res.codeMap[m.workMImpo]}"
                        data-workm-id="${m.workMId}"
                        data-userrole="${userRole}"
                        data-date="${formattedDate}"
                        data-workm-context="${m.workMContext}">
                        <td>${res.codeMap[m.workMDept]}</td>
                        <td>${m.workMName}</td>
                        <td></td>
                        <td>${res.codeMap[m.workMImpo]}</td>
                        <td>${addButton}<button class="detail_btn_M">업무 상세</button></td>
                    </tr>
                `);
            });
        } else if (res.workDList && res.workDList.length) {
            res.workDList.forEach(d => {
                $tbody.append(`
                    <tr class="workD-row" data-workd-id="${d.workDId}">
                        <td>${d.workDName}</td>
                        <td>${d.emplName}</td>
                        <td>${res.codeMap[d.workDImpo]}</td>
                        <td><button class="detail_btn_D">업무 상세</button></td>
                    </tr>
                `);
            });
        } else {
            $tbody.append('<tr><td colspan="5">업무 없음</td></tr>');
        }
    }

    function renderWorkTableHead(mode) {
        const $thead = $('#workTableHead').empty();

        if (mode === 'ALL') {
            $thead.append(`
                <tr>
                    <th>부서</th>
                    <th>업무명</th>
                    <th>담당자</th>
                    <th>중요도</th>
                    <th>업무배분/상세</th>
                </tr>
            `);
        } else if (mode === 'MINE') {
            $thead.append(`
                <tr>
                    <th>업무명</th>
                    <th>담당자</th>
                    <th>중요도</th>
                    <th>업무배분/상세</th>
                </tr>
            `);
        }
    }

    function loadWorkList() {
        const mode = $('input[name="mode"]:checked').val();
        const date = currentDate.toISOString().split('T')[0].replaceAll('-', '');
        const formattedDate = currentDate.toISOString().split('T')[0];
        $.get('/work/listData', { date, mode })
          .done(res => {
              renderWorkTableHead(mode);
              renderWorkTable(res, formattedDate);
          })
          .fail(() => alert('업무 목록 불러오기 실패'));
    }

    function updateDateDisplay() {
        const yyyy = currentDate.getFullYear();
        const mm = String(currentDate.getMonth() + 1).padStart(2, '0');
        const dd = String(currentDate.getDate()).padStart(2, '0');
        const formatted = `${yyyy}-${mm}-${dd}`;
        document.getElementById("dateText").textContent = formatted;
        document.getElementById("datePicker").value = formatted;
        loadWorkList();
    }
    // 라디오버튼 모드 체인지
    $('input[name="mode"]').on('change', loadWorkList);

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
//    updateDateDisplay();
});

$(document).ready(function () {


    // 부서 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/deptTypes', // 서버에서 부서 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            deptList = data;
            deptList.forEach(rt => {
                deptMap[rt.codeId] = rt.codeName; // map에 추가
            });

            // 부서 타입을 등록 폼의 셀렉트 박스에 추가
            populateDept();
        },
        error: function() {
            console.error("부서 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 중요도 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/impoTypes', // 서버에서 중요도 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            impoList = data;
            impoList.forEach(rt => {
                impoMap[rt.codeId] = rt.codeName; // map에 추가
            });

            // 중요도 타입을 등록 폼의 셀렉트 박스에 추가
            populateImpo();
        },
        error: function() {
            console.error("중요도 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 방 목록 전체 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/roomTypes', // 서버에서 방 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            roomList = data;
            roomList.forEach(rt => {
                roomMap[rt.codeId] = rt.codeName; // map에 추가
            });
        },
        error: function() {
            console.error("중요도 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 부서별 사원 목록 조회 (서버에서 가져오기)
    $.ajax({
        url: '/work/emplTypes', // 서버에서 방 타입 목록을 가져옵니다.
        type: 'GET',
        success: function(data) {
            emplList = data;
            emplList.forEach(rt => {
                emplMap[rt.codeId] = rt.codeName; // map에 추가
            });
        },
        error: function() {
            console.error("중요도 목록을 불러오는 데 실패했습니다.");
        }
    });

    // 주업무 클릭 → 상세 업무 열기
    $(document).on('click', '.workM-row', function (e) {
        // 버튼 클릭으로 유입되었으면 무시
        if ($(e.target).closest('button').length > 0) return;

        const $clickedRow = $(this);
        const workMId = $clickedRow.data('workm-id');
        const rawDate = String($clickedRow.data('date'));
        const date = rawDate.replaceAll('-', '');

        const $existingRows = $(`.workD-row[data-parent="${workMId}"]`);

        // 이미 열려있으면 닫기
        if ($existingRows.length > 0) {
            $existingRows.slideUp(200, function () {
                $(this).remove();
            });
            return;
        }

        $.ajax({
            url: '/work/detailWorkList',
            method: 'GET',
            data: { workMId, date },
            dataType: 'json',
            success: function (detailWorks) {
                if (detailWorks.length === 0) {
                    const emptyRow = $(`
                        <tr class="workD-row" data-parent="${workMId}">
                            <td colspan="5" style="background:#f0f0f0;">상세업무가 없습니다.</td>
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
                                <td data-label="부서"></td>
                                <td data-label="업무명">${detail.workDName}</td>
                                <td data-label="담당자">${detail.emplName}</td>
                                <td data-label="중요도">${detail.workDImpoN}</td>
                                <td data-label="업무상세"><button id="detail_btn_D">업무 상세</button></td>
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

    // 상세업무 상세페이지로 이동
    $(document).on('click', '#detail_btn_D', function (e) {
        e.preventDefault();

        const workDId = $(this).closest('tr').data('workd-id');

        if (!workDId) {
            alert('업무 ID를 찾을 수 없습니다.');
            return;
        }

        window.location.href = `${contextPath}/work/detailWorkD?workDId=${workDId}`;
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

    // #detail_btn_M 클릭시 업무 상세 모달 열기
    $(document).on('click', '.detail_btn_M', function (e) {
        e.stopPropagation(); // 상위 tr 클릭 이벤트 방지

        const $btn = $(this);
        const $row = $btn.closest('tr');
        const userRole = $row.data('userrole');
        const workMId = $row.data('workm-id');
        const workMName = $row.data('workm-name');
        const workMDept = $row.data('workm-dept');
        const workMImpo = $row.data('workm-impo');
        const workMDeptName = $row.data('workm-deptname');
        const workMImpoName = $row.data('workm-imponame');
        const workMContext = $row.data('workm-context');

        // 값 채워주기
        const $modal = $('.workMDetail');
        $modal.find('[name="workMId"]').val(workMId);
        $modal.find('[name="workMName"]').val(workMName);
        $modal.find('[name="workMContext"]').val(workMContext);

        if (userRole==='GR_01') {
        $modal.find('[name="workMDept"]').val(workMDept);
        $modal.find('[name="workMImpo"]').val(workMImpo);
        } else {
        $modal.find('[name="workMDept"]').val(workMDeptName);
        $modal.find('[name="workMImpo"]').val(workMImpoName);
        };

        $modal.fadeIn(200);
    });

    // #cancle_btn_M 클릭시 업무 상세 모달 닫기
    $(document).on('click', '.cancle_btn_M', function (e) {
        e.stopPropagation(); // 상위 tr 클릭 이벤트 방지
        $('.workMDetail').fadeOut(200);
    });

    // 수정 버튼 클릭
    $(document).on('submit', '.workMDetailForm', function (e) {
        e.preventDefault();
        const $form = $(this);
        const $formDiv = $form.closest('div');

        const data = {
         workMId: $form.find('input[name="workMId"]').val(),
         workMName: $form.find('input[name="workMName"]').val(),
         workMDept: $form.find('select[name="workMDept"]').val(),
         workMImpo: $form.find('select[name="workMImpo"]').val(),
         workMContext: $form.find('textarea[name="workMContext"]').val(),
        };

        if (!data.workMName || !data.workMDept || !data.workMImpo) {
         alert("업무 이름과 부서, 중요도는 필수입니다.");
         return;
        }

        $.ajax({
            url: '/work/modifyWorkM',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                alert('주 업무 내용이 수정되었습니다.');
                // 성공 후 필요한 작업
                $formDiv.fadeOut(200);
            },
            error: function (xhr) {
                alert('수정 실패: ' + xhr.responseText);
            }
        });
    });

    // 삭제 버튼 클릭
    $(document).on('click', '.delete_btn_M', function (e) {
        e.preventDefault();
        const $form = $(this).closest('form');
        const $formDiv = $form.closest('div');

        const workMId = $form.find('input[name="workMId"]').val();

        $.ajax({
            url: '/work/deleteWorkM',
            method: 'POST',
            data: {
                workMId : workMId,
            },
            success: function (response) {
                alert('주 업무 내용이 삭제되었습니다.');
                // 성공 후 필요한 작업
                $formDiv.fadeOut(200);
            },
            error: function (xhr) {
                alert('삭제 실패: ' + xhr.responseText);
            }
        });
    });

});


// #add_btn_D 클릭시 업무 배분 등록폼 열기
$(document).on('click', '.add_btn_D', function (e) {
    e.stopPropagation(); // 상위 tr 클릭 이벤트 방지

    const $button = $(this);
    const $row = $button.closest('tr');
    const workMId = $row.data('workm-id');
    const workMName = $row.data('workm-name');
    const workMDept = $row.data('workm-dept');
    const workMImpo = $row.data('workm-impo');
    const date = $row.data('date');
    const workMContext = $row.data('workm-context');


    // 이미 열려있는 등록 폼 제거
    $('.newWorkDForm').slideUp(200, function () {
        $(this).remove();
    });

    // 이미 해당 row 밑에 폼이 있다면 return
    if ($row.next().hasClass('newWorkDForm')) return;

    const formRow = $(`
        <tr class="newWorkDForm">
            <td colspan="5" style="background:#eef; padding:10px;">
                <form class="workDForm">
                    <input type="hidden" name="workMId" value="${workMId}" />
                    <input type="hidden" name="workDDept" value="${workMDept}" />
                    <label>업무명
                        <input type="text" name="workDName" value="${workMName}" />
                    </label>
                    <label>담당자
                        <select name="workDEmplId"></select>
                    </label>
                    <label>업무일자
                        <input type="date" name="workDDate" value="${date}" />
                    </label>
                    <label>객실
                        <select name="workDRoomId"></select>
                    </label>
                    <label>중요도
                        <select name="workDImpo" value="${workMImpo}"></select>
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

    // 중요도 옵션 추가
    const $impoSelect = formRow.find('select[name="workDImpo"]');
    impoList.forEach(i => {
        $impoSelect.append(`<option value="${i.codeId}">${i.codeName}</option>`);
    });
    // 중요도 기본값 설정
    if (workMImpo) {
        $impoSelect.val(workMImpo);
    }

    // 방 목록 추가
    const $roomSelect = formRow.find('select[name="workDRoomId"]');
    roomList.forEach(i => {
        $roomSelect.append(`<option value="${i.codeId}">${i.codeName}</option>`);
    });

    // 직원 목록 추가
    const $emplSelect = formRow.find('select[name="workDEmplId"]');
    emplList.forEach(i => {
        $emplSelect.append(`<option value="${i.codeId}">${i.codeName}</option>`);
    });
});
$(document).on('click', '.add_cancle_D', function () {
    $(this).closest('tr.newWorkDForm').slideUp(200, function () {
        $(this).remove();
    });
});

// workDForm 등록 폼 제출
$(document).on('submit', '.workDForm', function (e) {
    e.preventDefault();
    const $form = $(this);

    const data = {
     workMId: $form.find('input[name="workMId"]').val(),
     workDDept: $form.find('input[name="workDDept"]').val(),
     workDName: $form.find('input[name="workDName"]').val(),
     workDEmplId: $form.find('select[name="workDEmplId"]').val(),
     workDDate: $form.find('input[name="workDDate"]').val(),
     workDRoomId: $form.find('select[name="workDRoomId"]').val(),
     workDImpo: $form.find('select[name="workDImpo"]').val(),
     workDContext: $form.find('textarea[name="workDContext"]').val(),
    };

    if (!data.workDName || !data.workDDate || !data.workDImpo) {
     alert("업무 이름과 날짜, 중요도는 필수입니다.");
     return;
    }

    $.ajax({
        url: '/work/addWorkD',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            alert('상세 업무가 등록되었습니다.');
            // 성공 후 필요한 작업
            $form.closest('tr.newWorkDForm').slideUp(200, function() {
                $(this).remove();
            });
        },
        error: function (xhr) {
            alert('등록 실패: ' + xhr.responseText);
        }
    });
});


