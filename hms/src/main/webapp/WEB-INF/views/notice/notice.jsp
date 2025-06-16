<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%
    // 테스트용 로그인 사용자
    String userRole = "관리자";
%>
<html>
<head>
    <title>공지사항</title>
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>공지사항</h1>
    <button id = "add_btn">공지사항 등록</button>

   <!-- <input type = "text" placeholder="검색어를 입력해 주세요."> -->

    <table>
        <thead>
            <tr>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
                <c:forEach var="notice" items="${noticeList}">
                    <tr class="ann-row"
                            data-title="${notice.noticeTitle}"
                            data-writer="${notice.emplId}"
                            data-date="${notice.createdDate}">
                            <td>${notice.noticeTitle}</td>
                            <td>${notice.emplId}</td>
                            <td$${notice.createdDate}</td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>

    <jsp:include page="/WEB-INF/views/include/pagination.jsp" />

    <!-- 공지사항 등록 폼 -->
    <form id="newNoticeForm" hidden>
    <table>
    <tr>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>버튼</th>
    <tr>
    <tr>
      <td><input type="text" name="noticeTitle" placeholder="제목"></td>
      <td><input type="text" name="emplId" placeholder="작성자"></td>
      <td><input type="text" name="createdDate" placeholder="작성일"></td>
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
</script>
<script>
        $(document).ready(function () {
            $('.ann-row').on('click', function () {
                const $clickedRow = $(this);
                const $nextRow = $clickedRow.next('.detail-slide');

                if ($nextRow.length > 0) {
                    // 이미 열려 있는 경우 닫기
                    $nextRow.remove();
                    return;
                }

                // 다른 열려있는 슬라이드 닫기
                $('.detail-slide').remove();

                const title = $clickedRow.data('title');
                const writer = $clickedRow.data('writer');
                const date = $clickedRow.data('date');

                const $detailRow = $('<tr class="detail-slide"><td colspan="4"></td></tr>');
                const $slideContent = $('<div class="slide-content"></div>');

                $slideContent.append('<strong>제목: <input type="text" value="' + title + '"></strong><br>');
                $slideContent.append('작성자: <input type="text" value="' + writer + '"><br>');
                $slideContent.append('작성일: <input type="text" value="' + date + '"><br>');
                $slideContent.append(getButtonsByRole(userRole));
                $slideContent.append('<button class="action-btn close-btn">닫기</button>');

                $detailRow.find('td').append($slideContent);
                $clickedRow.after($detailRow);
            });

            // 닫기 버튼 클릭 시
            $(document).on('click', '.close-btn', function () {
                $(this).closest('tr.detail-slide').remove();
            });
        });
    </script>

    <script>
        $("#add_btn").click(function () {
            $("#newNoticeForm").toggle(500);
        });


        $("#add_cancle").click(function () {
            $("#newNoticeForm").toggle(500);
        });

    </script>

    <script>
        $("#newNoticeForm").on("submit", function (e) {
            e.preventDefault();

            const newNotice = {
                noticeTitle: $('input[name="noticeTitle"]').val(),
                emplId: $('input[name="emplId"]').val(),
                createdDate: $('input[name="createdDate"]').val(),
                noticeContent: "내용 없음"  <!-- 필수 필드 대응 -->
            };

            $.ajax({
                url: '/ann/notice',
                type: 'POST',
                contentType: 'application/json',
                data: JSON,stringify(newNotice),
                success:  function() {
                    alert("공지 등록 성공!");
                    location.reload();
                },
                error: function () {
                    alert("공지 등록 실패");
                }
            });
        });
    </script>
</body>
</html>
