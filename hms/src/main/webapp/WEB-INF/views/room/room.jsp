<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<%
    // 테스트용 로그인 사용자
    String userRole = "관리자";
%>
<!DOCTYPE html>
<html>
<head>
    <title>객실 관리</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>

        h1 {
            font-size: 28px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #f3f3f3;
        }

        .detail-slide .slide-content {
            padding: 15px;
            background: #f9f9f9;
            border: 1px solid #ccc;
            animation: slideDown 0.3s ease;
            text-align: left;
        }

        .action-btn {
            margin: 5px;
            padding: 6px 12px;
            font-size: 14px;
            cursor: pointer;
        }

        @keyframes slideDown {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<h1>객실 관리</h1>
<button id = "add_btn">공지사항 등록</button>

<table>
    <thead>
        <tr>
            <th>번호</th>
            <th>객실 종류</th>
            <th>예약 상태</th>
            <th>청소 상태</th>
        </tr>
    </thead>
    <tbody>
        <tr class="room-row" data-room="101" data-type="Standard" data-reserve="Reserved">
            <td>101</td><td>Standard</td><td>Reserved</td><td>Cleaning</td>
        </tr>
        <tr class="room-row" data-room="102" data-type="Deluxe" data-reserve="Available">
            <td>102</td><td>Deluxe</td><td>Available</td><td>Done</td>
        </tr>
        <tr class="room-row" data-room="103" data-type="Suite" data-reserve="Occupied">
            <td>103</td><td>Suite</td><td>Occupied</td><td>Cleaning</td>
        </tr>
    </tbody>
</table>

    <form id="newRoomForm" hidden>
    <table>
    <tr>
        <th>번호</th>
        <th>객실 종류</th>
        <th>예약 상태</th>
        <th>청소 상태</th>
    <tr>
    <tr>
      <td><input type="text" name="roomNum" placeholder="번호"></td>
      <td><input type="text" name="roomType" placeholder="객실 종류"></td>
      <td><input type="text" name="res" placeholder="예약 상태"></td>
      <td><input type="text" name="date" placeholder="청소 상태"></td>
      <td>
        <button type="submit">등록</button>
        <button id = "add_cancle">취소</button>
      </td>
     </tr>
      </table>
    </form>

<script>
    const userRole = '<%= userRole %>';
</script>

<script>
    function getButtonsByRole(role) {
        let btns = '';
        if (role === '관리자') {
            btns += '<button class="action-btn">수정</button>';
            btns += '<button class="action-btn">삭제</button>';
        } else if (role === '팀장') {
            btns += '<button class="action-btn">수정</button>';
        }
        return btns;
    }

   $(document).ready(function () {
       // 객실 클릭 시 상세 열기
       $('.room-row').on('click', function (event) {

           const $clickedRow = $(this);
           const $nextRow = $clickedRow.next('.detail-slide');

           if ($nextRow.length > 0) {
               // 이미 열려 있는 경우 닫기
               $nextRow.remove();
               return;
           }

           // 다른 열려있는 슬라이드 닫기
           $('.detail-slide').remove()

           $('.detail-slide').remove(); // 기존 열려 있는 거 닫기

           const room = $(this).data('room');
           const type = $(this).data('type');
           const reserve = $(this).data('reserve');

           const $detailRow = $('<tr class="detail-slide"><td colspan="4"></td></tr>');
           const $slideContent = $('<div class="slide-content"></div>');

           $slideContent.append('<strong>객실 번호: <input type="text" value="' + room + '"></strong><br>');
           $slideContent.append('종류: <input type="text" value="' + type + '"><br>');
           $slideContent.append('예약 상태: <input type="text" value="' + reserve + '"><br>');
           $slideContent.append(getButtonsByRole(userRole));
           $slideContent.append('<button class="action-btn close-btn">닫기</button>');

           $detailRow.find('td').append($slideContent);
           $(this).after($detailRow);
       });

       // 닫기 버튼 클릭 시
       $(document).on('click', '.close-btn', function () {
           $(this).closest('tr.detail-slide').remove();
       });

   });
</script>
    <script>
        $("#add_btn").click(function () {
            $("#newRoomForm").toggle(500);
        });


        $("#add_cancle").click(function () {
            $("#newRoomForm").toggle(500);
        });
</script>

</body>
</html>
