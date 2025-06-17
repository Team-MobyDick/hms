<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />

<!DOCTYPE html>
<html>
<head>
    <title>스케줄 관리</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>

    <style>
        #calendar {
            max-width: 1000px;
            margin: 20px auto;
        }

        #scheduleModal {
            position: fixed;
            top: 20%;
            left: 50%;
            transform: translateX(-50%);
            padding: 20px;
            border: 1px solid #ccc;
            background-color: white;
            z-index: 1000;
        }

        #scheduleModal form {
            display: flex;
            flex-direction: column;
        }

        #scheduleModal label {
            margin-top: 10px;
        }
        #emplSelect, #emplSelect option {
            color: #000 !important;
            font-size: 14px !important;
            background-color: #fff;
        }
    </style>
</head>

<body>

<!-- 조건부 등록 버튼 -->
<c:if test="${userRole eq 'GR_01' or userRole eq 'GR_02'}">
    <div style="text-align: center; margin-bottom: 10px;">
        <button id="btnOpenModal">스케줄 등록</button>
    </div>
</c:if>

<div id='calendar'></div>

<!-- 등록 모달 -->
<div id="scheduleModal" style="display:none;">
    <form id="scheduleForm">
        <label>부서</label>
        <input type="text" id="deptName" readonly />

        <label>직원명</label>
        <select id="emplSelect" name="emplId" onchange="updateDeptName()"></select>

        <label>교대</label>
        <select name="scheShift" id="scheShift">
            <option value="주간">주간</option>
            <option value="야간">야간</option>
        </select>

        <label>업무일자</label>
        <input type="date" id="startDate" required />
        ~
        <input type="date" id="endDate" required />

        <div style="margin-top: 15px;">
            <button type="submit">등록하기</button>
            <button type="button" onclick="$('#scheduleModal').hide()">취소하기</button>
        </div>
    </form>
</div>

<script>
    let calendar;
    let employeeMap = {};

    // HTML 이스케이프 함수
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
                    option.textContent = name + ' (' + dept + ')';  // 🔥 여기 중요
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
            eventColor: '#378006'
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
            scheduleList.push({
                emplId: emplId,
                scheDate: d.toISOString().slice(0, 10),
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
</script>


</body>
</html>
