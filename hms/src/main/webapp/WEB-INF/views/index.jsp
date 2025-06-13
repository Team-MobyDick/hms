<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>호텔 관리 시스템</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sample.css">
  <style>
    body {
      margin: 0;
      font-family: Arial, sans-serif;
      background-color: #f9f9f9;
    }

    .sidebar {
      width: 150px;
      background-color: #e0e0e0;
      padding-top: 1em;
      display: flex;
      flex-direction: column;
      border-right: 1px solid #888;
      transition: all 0.3s ease;
    }

    /* 사이드바 접힘 */
    .sidebar.collapsed {
      width: 0;
      padding: 0;
      overflow: hidden;
    }

    .sidebar.collapsed ul,
    .sidebar.collapsed .user-name {
      display: none;
    }

    /* 메인 콘텐츠 확장 */
    .main-content.expanded {
      margin-left: 0;
    }

    /* === 📱 반응형 === */
    @media screen and (max-width: 768px) {
      .sidebar {
        position: absolute;
        z-index: 1000;
        top: 50px; /* 헤더 높이와 맞춰줌 */
        left: 0;
        width: 150px;
        height: 100%;
        background-color: #e0e0e0;
        border-right: 1px solid #888;
        transition: all 0.3s ease;
      }

      .sidebar.collapsed {
        transform: translateX(-100%);
      }

      .main-content {
        width: 100%;
        margin-left: 0;
      }
    }
  </style>
</head>
<body>

      <c:if test="${not empty sessionScope.loginUser}">

        <jsp:include page="include/header.jsp" />

        <div class="container">
          <jsp:include page="include/sidebar.jsp" />

          <div class="main-content" id="mainContent">
            <jsp:include page="${bodyPage}" />
          </div>
        </div>

        <jsp:include page="include/footer.jsp" />

        <script>
          window.addEventListener("DOMContentLoaded", function () {
            const toggleBtn = document.getElementById('menuToggle');
            const sidebar = document.getElementById('sidebar');
            const mainContent = document.getElementById('mainContent');

            if (toggleBtn && sidebar && mainContent) {
              toggleBtn.addEventListener('click', function () {
                sidebar.classList.toggle('collapsed');
                mainContent.classList.toggle('expanded');
              });
            } else {
              console.warn("엘리먼트를 찾을 수 없습니다.");
            }
          });
        </script>
      </c:if>

      <c:if test="${empty sessionScope.loginUser}">
        <jsp:include page="login/login.jsp" />
      </c:if>
</body>
</html>
