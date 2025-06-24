<%@ page contentType="text/html;charset=UTF-8" language="java" %>

 <!-- footer.jsp -->

<div class="footer">
  <span>© 2025 MobyDick</span>
    <span id="termsLink" style="cursor: pointer; color: #007bff;">이용 약관</span>
</div>

<!-- 이용약관 팝업 -->
<div id="termsModal" class="modal">
    <div class="modal_popup">
        <h2>이용 약관</h2>
        <p>본 이용 약관은 MobyDick 서비스의 이용 조건을 규정합니다.</p>

        <h3>1. 서비스 이용</h3>
        <p>서비스에 대한 이용자는 본 약관을 준수해야 합니다. 서비스는 무료이며, 일부 유료 서비스가 있을 수 있습니다.</p>

        <h3>2. 개인정보 보호</h3>
        <p>회사는 이용자의 개인정보를 보호하기 위해 최선의 노력을 다합니다. 이용자의 개인정보는 회사의 개인정보처리방침에 따라 처리됩니다.</p>

        <h3>3. 책임 한계</h3>
        <p>서비스의 이용에 있어 발생한 문제에 대해서 회사는 책임을 지지 않으며, 서비스의 제공이나 이용 중 발생할 수 있는 문제는 이용자가 해결해야 합니다.</p>

        <h3>4. 서비스의 변경 및 종료</h3>
        <p>회사는 사전 공지 없이 서비스를 변경하거나 종료할 수 있습니다. 서비스 종료 시 이용자에게 미리 안내됩니다.</p>

        <h3>5. 부정행위 및 규제</h3>
        <p>불법적이거나 부정한 방법으로 서비스를 이용하는 행위는 금지되며, 이를 위반할 경우 이용 제한이 있을 수 있습니다.</p>

        <h3>6. 기타</h3>
        <p>본 약관은 관련 법령에 따라 개정될 수 있으며, 변경된 사항은 홈페이지에 공지됩니다.</p>

        <button type="button" id="close_btn" class="close_btn">닫기</button>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/footer.js" defer></script>