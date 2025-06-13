<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

  <head>
    <meta charset="UTF-8" />
    <title>호텔 관리 시스템</title>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css"/>
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

      <script src="${pageContext.request.contextPath}/js/index.js" defer></script>

    </c:if>

    <c:if test="${empty sessionScope.loginUser}">
      <c:redirect url="/login" />
    </c:if>
  </body>

</html>
