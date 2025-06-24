$(document).ready(function () {

    // 수정버튼 폼 submit
    $('.workDDetailForm').on('submit', function (e) {
        e.preventDefault();

        let form = this;
        let formDataStart = new FormData();
        let formDataEnd = new FormData();
        let fileStart = $('#workDStartFile')[0].files[0];
        let fileEnd = $('#workDEndFile')[0].files[0];

        // 업로드 호출 함수
        function uploadFile(file) {
            return new Promise((resolve, reject) => {
                if (!file) return resolve(null); // 파일 없으면 null
                let fd = new FormData();
                fd.append('file', file);
                $.ajax({
                    url: '/work/uploadFile',
                    type: 'POST',
                    processData: false,
                    contentType: false,
                    data: fd,
                    success: function(res) {
                        resolve({ filePath: res.filePath, uploadTime: res.uploadTime });
                     },
                    error: function(xhr) { reject(xhr); }
                });
            });
        }

        // 업로드 파일들 업로드
        Promise.all([uploadFile(fileStart), uploadFile(fileEnd)]).then(results => {
            let startFileData = results[0];
            let endFileData = results[1];

            // 수정용 VO 구성
            let data = {
                workDId: $('input[name="workDId"]').val(),
                workDName: $('input[name="workDName"]').val(),
                workDEmplId: $('select[name="workDEmplId"]').val(),
                workDDate: $('input[name="workDDate"]').val(),
                workDRoomId: $('select[name="workDRoomId"]').val(),
                workDImpo: $('select[name="workDImpo"]').val(),
                workDContext: $('textarea[name="workDContext"]').val(),
                workDExtra: $('textarea[name="workDExtra"]').val(),
                workDStartName: startFileData ? startFileData.filePath : null,
                workDStartTime: startFileData ? startFileData.uploadTime : null,
                workDEndName: endFileData ? endFileData.filePath : null,
                workDEndTime: endFileData ? endFileData.uploadTime : null
            };
            $.ajax({
                url: '/work/modifyWorkD',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(res) {
                    alert('수정됨');
                },
                error: function(xhr) {
                    alert('실패: ' + xhr.statusText);
                }
            });
        })
        .catch(err => alert('파일 업로드 실패'));
    });

    // 삭제 버튼 클릭
    $(document).on('click', '.delete_btn_D', function (e) {
        e.preventDefault();
        const $form = $(this).closest('form');

        const workDId = $form.find('input[name="workDId"]').val();
        console.log(workDId);

        $.ajax({
            url: '/work/deleteWorkD',
            method: 'POST',
            data: {
                workDId : workDId,
            },
            success: function (response) {
                alert('상세 업무가 삭제되었습니다.');
                history.back();
            },
            error: function (xhr) {
                alert('삭제 실패: ' + xhr.responseText);
            }
        });
    });

    // 시작사진 등록 확인하고 종료사진 등록 열기
    $(document).on('change', '#workDStartFile', function () {
        if (this.files.length > 0) {
            $('#endPhotoContainer').slideDown(200);
        } else {
            $('#endPhotoContainer').slideUp(200);
        }
    });
});