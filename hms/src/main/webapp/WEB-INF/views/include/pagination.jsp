<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pagination-container">
<c:if test="${totalPages > 1}">
  <div class="pagination">

    <c:if test="${currentPage > 1}">
      <a href="?page=${currentPage - 1}" class="page-link">이전</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="page">
      <a href="?page=${page}"
         class="page-link ${page == currentPage ? 'active' : ''}">
        ${page}
      </a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
      <a href="?page=${currentPage + 1}" class="page-link">다음</a>
    </c:if>

  </div>
</c:if>
</div>
