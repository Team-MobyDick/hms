<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="pagination-container">
  <div class="pagination">
    <c:choose>
      <c:when test="${currentPage > 1}">
        <a href="?page=${currentPage - 1}" class="page-link">이전</a>
      </c:when>
      <c:otherwise>
        <span class="page-link invisible">이전</span> <!-- ← 자리 채우기 -->
      </c:otherwise>
    </c:choose>

    <c:forEach begin="1" end="${totalPages}" var="page">
      <a href="?page=${page}"
         class="page-link ${page == currentPage ? 'active' : ''}">
          ${page}
      </a>
    </c:forEach>

    <c:choose>
      <c:when test="${currentPage < totalPages}">
        <a href="?page=${currentPage + 1}" class="page-link">다음</a>
      </c:when>
      <c:otherwise>
        <span class="page-link invisible">다음</span> <!-- ← 자리 채우기 -->
      </c:otherwise>
    </c:choose>
  </div>
</div>
