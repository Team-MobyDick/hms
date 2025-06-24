// 이용약관 클릭 시 모달 열기
document.getElementById("termsLink").addEventListener("click", function() {
    document.getElementById("termsModal").style.display = "block";
});

// 모달 닫기 버튼 클릭 시
document.getElementById("close_btn").addEventListener("click", function() {
    document.getElementById("termsModal").style.display = "none";
});

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target == document.getElementById("termsModal")) {
        document.getElementById("termsModal").style.display = "none";
    }
}