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
        data: {date: dateStr.replaceAll('-', '')}, // DB 저장 포맷이 YYYYMMDD일 경우

        success: function (list) {
            if (!list || list.length === 0) {
                $('#scheduleDetailContent').html(`<p><strong>${dateStr}</strong>등록된 스케줄이 없습니다.</p>`);
                return;
            }

            let html = `<p><strong>${dateStr}</strong> 스케줄 목록</p><ul>`;
            list.forEach(item => {
                html += '<li><strong>' + escapeHtml(item.emplName) + '</strong> - '
                    + escapeHtml(item.workdName) + ' (' + escapeHtml(item.workdStateName) + ')</li>';
            });
            html += '</ul>';

            $('#scheduleDetailContent').html(html);
        },
        error: function () {
            $('#scheduleDetailContent').html('<p style="color:red;">스케줄을 불러오는 중 오류가 발생했습니다.</p>');
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
        eventColor: '#378006',
        dateClick: function (info) {
            const dateStr = info.dateStr;
            console.log("dateStr = " + dateStr);
            loadScheduleDetailByDate(dateStr);
        }
    });

    calendar.render();
});

$('#btnOpenModal').on('click', function () {
    loadEmployee();
    $('#scheduleModal').show();
});

$('#scheduleForm').on('submit', function (e) {
    e.preventDefault();

    const emplId = $('#emplSelect').val();
    const shift = $('#scheShift').val();
    const start = new Date($('#startDate').val());
    const end = new Date($('#endDate').val());

    let scheduleList = [];
    for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
       // const isoDate = d.toISOString().slice(0, 10).replaceAll('-', '');
        const isoDate = d.toISOString().slice(0, 10);
        scheduleList.push({
            emplId: emplId,
            scheDate: isoDate,
            scheShift: shift
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
            alert("등록 실패. 입력값을 확인해주세요.");
        }
    });
});