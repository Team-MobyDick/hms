const qrLoginBtn = document.getElementById('qr-login-btn');
const qrReaderElement = document.getElementById('qr-reader');
let qrScanner = null;

qrLoginBtn.addEventListener('click', function () {
    // 보이게 하고 초기화
    qrReaderElement.style.display = 'block';

    // 한 번만 초기화되도록 체크
    if (qrScanner === null) {
        qrScanner = new Html5Qrcode("qr-reader");

        qrScanner.start(
            {facingMode: "environment"}, // 뒷면 카메라
            {
                fps: 10,
                qrbox: 250
            },
            (decodedText, decodedResult) => {
                console.log("QR 인식됨:", decodedText);
                qrScanner.stop();

                // QR 코드에서 ID 추출 후 폼으로 전송
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/login';

                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'emplId';
                input.value = decodedText;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            },
            (errorMessage) => {
                // 인식 실패 시 무시 (계속 시도함)
            }
        ).catch((err) => {
            alert("카메라 시작 실패: " + err);
        });
    }
});