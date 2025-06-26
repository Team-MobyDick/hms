let calendar;
let employeeMap = {};

// HTML 이스케이프 함수 (JavaScript 내부에서만 사용)
function escapeHtml(str) {
    return String(str ?? '')
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}

function loadEmployee() {
    $.get("/schedule/employees", function (list) {
        const select = document.getElementById('emplSelect');
        select.innerHTML = '';
        employeeMap = {};

        list.forEach(emp => {
            const id = emp.emplId?.trim?.() || '';
            const name = emp.emplName?.trim?.() || '';
            const dept = emp.deptName?.trim?.() || '';

            if (id && name && dept) {
                employeeMap[id] = dept;

                const option = document.createElement('option');
                option.value = id;
                option.textContent = name + ' (' + dept + ')';
                document.getElementById('emplSelect').appendChild(option);
            }
        });

        if (list.length > 0) {
            const defaultId = String(JSON.parse(JSON.stringify(list[0])).emplId ?? '').trim();
            $('#emplSelect').val(defaultId);
            updateDeptName();
        }
    });
}

function updateDeptName() {
    const selectedId = String($('#emplSelect').val()).trim();
    const dept = employeeMap[selectedId];
    $('#deptName').val(dept || '');
}

function loadScheduleDetailByDate(dateStr) {
    $.ajax({
        url: '/schedule/detailByDate',
        type: 'GET',
        data: {date: dateStr.replaceAll('-', '')},

        success: function (list) {
            let html = `<p><strong>${dateStr}</strong> 스케줄 목록</p>`;
            $('#btnDeleteSchedule').hide();
            $('#selectedScheId').val('');

            if (!list || list.length === 0) {
                html += `<p>등록된 스케줄이 없습니다.</p>`;
            } else {
                html += '<ul>';
                list.forEach(item => {
                    html += `<li>
                                <input type="radio" name="selectScheduleToDelete" value="${escapeHtml(item.scheId)}" id="schedule-${escapeHtml(item.scheId)}">
                                <label for="schedule-${escapeHtml(item.scheId)}">
                                    <strong>${escapeHtml(item.emplName)}</strong> -
                                    <a href="${contextPath}/work/detailWorkD?workDId=${escapeHtml(item.workDId)}&date=${escapeHtml(item.workDate)}">
                                    <span class="work-name">${escapeHtml(item.workdName || '업무명 없음')}</span> ${escapeHtml(item.workdStateName)}
                                    </a>
                                </label>
                             </li>`;
                });
                html += '</ul>';

                $('#scheduleDetailContent').off('change', 'input[name="selectScheduleToDelete"]').on('change', 'input[name="selectScheduleToDelete"]', function() {
                    $('#selectedScheId').val($(this).val());

                    if ($('#btnDeleteSchedule').length) {
                        $('#btnDeleteSchedule').show();
                    }
                });
            }

            $('#scheduleDetailContent').html(html);
        },
        error: function () {
            $('#scheduleDetailContent').html('<p class="error-message">스케줄을 불러오는 중 오류가 발생했습니다.</p>');
            $('#btnDeleteSchedule').hide();
            $('#selectedScheId').val('');
        }
    });
}


document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');

    calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        height: 'auto',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,listWeek'
        },
        events: '/schedule/display',
        dateClick: function (info) {
            const dateStr = info.dateStr;
            console.log("dateStr = " + dateStr);
            loadScheduleDetailByDate(dateStr);
        }
    });

    calendar.render();
});

$('#scheduleForm').on('submit', function (e) {
    e.preventDefault();

    const emplId = $('#emplSelect').val();
    const shift = $('#scheShift').val();
    const start = new Date($('#startDate').val());
    const end = new Date( $('#endDate').val());

    let scheduleList = [];
    for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
        const isoDate = d.toISOString().slice(0, 10);
        scheduleList.push({
            emplId: emplId,
            scheDate: isoDate,
            scheShift: shift,
            dupCheck: emplId+isoDate+shift,
        });
    }

    $.ajax({
        url: '/schedule/insert',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(scheduleList),
        success: function () {
            alert("등록 완료!");
            $('#scheduleModal').hide();
            calendar.refetchEvents();
        },
        error: function () {
            alert("해당 직원의 스케줄이 중복으로 등록됐습니다. 다시 한 번 확인해주세요.");
        }
    });
});

// 스케줄 삭제 버튼 클릭 이벤트
$('#btnDeleteSchedule').on('click', function () {
    const scheIdToDelete = $('#selectedScheId').val();

    if (!scheIdToDelete) {
        alert("삭제할 스케줄을 선택해주세요.");
        return;
    }

    if (confirm("선택된 스케줄을 삭제하시겠습니까?")) {
        $.ajax({
            url: `/schedule/delete/${scheIdToDelete}`,
            method: 'DELETE',
            success: function (response) {
                if (response === "ok") {
                    alert("스케줄이 성공적으로 삭제되었습니다.");
                    calendar.refetchEvents();
                    $('#scheduleDetailContent').html('날짜를 클릭하면 스케줄이 표시됩니다.');
                    $('#btnDeleteSchedule').hide();
                    $('#selectedScheId').val('');
                } else {
                    alert("스케줄 삭제 실패: " + response);
                }
            },
            error: function (xhr, status, error) {
                let errorMessage = "스케줄 삭제 중 오류가 발생했습니다.";
                try {
                    const responseText = xhr.responseText;
                    if (responseText.startsWith("error:")) {
                        errorMessage = responseText;
                    } else {
                        errorMessage += " (" + status + ": " + error + ")";
                    }
                } catch (e) {
                    errorMessage += " (응답 파싱 오류)";
                }
                alert(errorMessage);
            }
        });
    }
});

// 모달 열기
$('#btnOpenModal').on('click', function () {
    loadEmployee();
    if ($('#scheduleModal').is(':visible')) {
        $('#scheduleModal').hide();  // 이미 열려 있으면 닫기
    } else {
        $('#scheduleModal').show();  // 닫혀 있으면 열기
    }
});

// 모달 닫기 버튼 클릭 시
$('#scheduleModal button[type="button"]').on('click', function() {
    $('#scheduleModal').hide();
});

// 모달 외부 클릭 시 모달 닫기
$(document).on('click', function(event) {
    if (!$(event.target).closest('#scheduleModal').length && !$(event.target).closest('#btnOpenModal').length) {
        $('#scheduleModal').hide();
    }
});

