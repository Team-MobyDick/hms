<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="userRole" value="${sessionScope.loginUser.emplGrade}" />
<c:set var="userId" value="${sessionScope.loginUser.emplId}" />
<c:set var="userDept" value="${sessionScope.loginUser.emplDept}" />

<!DOCTYPE html>
<html>
<head>
    <title>업무 상세</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/work.css"/>

</head>
<body data-role="${userRole}">
    <div class="workDDetail">
        <form class="workDDetailForm" enctype="multipart/form-data">
            <h3 style="margin-bottom: 12px;">업무 상세보기</h3>

            <div style="display: flex; gap: 8px; margin-bottom: 12px;">
                <c:if test="${userRole != 'GR_03' || userId == 'detailWorkD.workDEmplId'}">
                <button type="submit">수정</button>
                </c:if>
                <c:if test="${userRole == 'GR_01' || (userRole == 'GR_02' && userDept == detailWorkD.workDDept)}">
                    <button type="button" class="delete_btn_D">삭제</button>
                </c:if>
                <button type="button" class="cancle_btn_D" onclick="history.back();">취소</button>
            </div>
            <input type="hidden" name="workMId" value="${detailWorkD.workMId}" >
            <input type="hidden" name="workDId" value="${detailWorkD.workDId}" >
            <div class="form-group">
                <label>지시자명</label>
                <select name="workDOrderId" disabled>
                    <c:forEach var="empl" items="${allEmplList}">
                      <option value="${empl.codeId}"
                          <c:if test="${empl.codeId == detailWorkD.orderId}">selected</c:if>>
                          ${empl.codeName}
                      </option>
                    </c:forEach>
                </select>
                <label>업무명</label>
                <input type="text" name="workDName" value="${detailWorkD.workDName}" maxlength="50" required
                        ${userRole != 'GR_01' && userRole != 'GR_02' ? 'readonly' : ''} />
            </div>

            <div class="form-group">
                <label>업무일자</label>
                <input type="date" name="workDate" value="${detailWorkD.workDDate}" disabled />
                <input type="hidden" name="workDDate" value="${detailWorkD.workDDate}"
                        ${userRole != 'GR_01' && userRole != 'GR_02' ? 'readonly' : ''} />
            </div>

            <div class="form-group">
                <label>담당자</label>
                <c:choose>
                    <c:when test="${userRole != 'GR_01' && userRole != 'GR_02'}">
                        <input type="text" name="workDEmplName" value="${detailWorkD.emplName}" readonly>
                        <select name="workDEmplId" style="display: none;">
                            <c:forEach var="empl" items="${emplList}">
                              <option value="${empl.codeId}"
                                  <c:if test="${empl.codeId == detailWorkD.workDEmplId}">selected</c:if>>
                                  ${empl.codeName}
                              </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="workDEmplId">
                            <c:forEach var="empl" items="${emplList}">
                              <option value="${empl.codeId}"
                                  <c:if test="${empl.codeId == detailWorkD.workDEmplId}">selected</c:if>>
                                  ${empl.codeName}
                              </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label>객실번호</label>
                <c:choose>
                    <c:when test="${userRole != 'GR_01' && userRole != 'GR_02'}">
                        <input type="text" name="workDRoomName" value="${detailWorkD.roomName}" readonly>
                        <select name="workDRoomId" style="display: none;" >
                            <c:forEach var="room" items="${roomList}">
                              <option value="${room.codeId}"
                                  <c:if test="${room.codeId == detailWorkD.workDRoomId}">selected</c:if>>
                                  ${room.codeName}
                              </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="workDRoomId" >
                            <option value="" <c:if test="${empty detailWorkD.workDRoomId}">selected</c:if>>선택 없음</option>
                            <c:forEach var="room" items="${roomList}">
                                <option value="${room.codeId}"
                                  <c:if test="${room.codeId == detailWorkD.workDRoomId}">selected</c:if>>
                                  ${room.codeName}
                                </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label>중요도</label>
                <c:choose>
                    <c:when test="${userRole != 'GR_01' && userRole != 'GR_02'}">
                        <input type="text" name="workDImpoName" value="${detailWorkD.workDImpoN}" readonly>
                        <select name="workDImpo" style="display: none;" >
                            <c:forEach var="impo" items="${impoList}">
                              <option value="${impo.codeId}"
                                  <c:if test="${impo.codeId == detailWorkD.workDImpo}">selected</c:if>>
                                  ${impo.codeName}
                              </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="workDImpo" >
                            <c:forEach var="impo" items="${impoList}">
                              <option value="${impo.codeId}"
                                  <c:if test="${impo.codeId == detailWorkD.workDImpo}">selected</c:if>>
                                  ${impo.codeName}
                              </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="form-group">
                <label>업무 상세내용</label>
                <textarea name="workDContext" rows="3"
                        ${userRole != 'GR_01' && userRole != 'GR_02' ? 'readonly' : ''}>${detailWorkD.workDContext}</textarea>
            </div>

            <div class="form-group" style="display: flex; gap: 12px; align-items: center;">

                <!-- 시작 부분 -->
                <div style="text-align: center;">
                    <label>시작사진</label><br />
                    <c:if test="${not empty detailWorkD.workDStartName}">
                        <img src="/work_photos/${detailWorkD.workDStartName}"
                             alt="${detailWorkD.workDStartName}"
                             style="width: 60px; height: 60px; border-radius: 50%;" /><br/>
                    </c:if>
                    <c:if test="${empty detailWorkD.workDStartName}">
                        <input type="file" name="workDStartFile" id="workDStartFile" />
                    </c:if>
                    <c:if test="${not empty detailWorkD.workDStartTime}">
                        <small>시작시간: <fmt:formatDate value="${detailWorkD.workDStartTime}" pattern="yyyy-MM-dd HH:mm:ss" /></small>
                    </c:if>
                </div>

                <!-- 종료 부분 -->
                <div id="endPhotoContainer" style="text-align: center; ${empty detailWorkD.workDStartName ? 'display:none;' : ''}">
                    <label>종료사진</label><br />
                    <c:if test="${not empty detailWorkD.workDEndName}">
                        <img src="/work_photos/${detailWorkD.workDEndName}"
                             alt="${detailWorkD.workDEndName}"
                             style="width: 60px; height: 60px; border-radius: 50%;" /><br/>
                    </c:if>
                    <c:if test="${empty detailWorkD.workDEndName}">
                        <input type="file" name="workDEndFile" id="workDEndFile" />
                    </c:if>
                    <c:if test="${not empty detailWorkD.workDEndTime}">
                        <small>종료시간: <fmt:formatDate value="${detailWorkD.workDEndTime}" pattern="yyyy-MM-dd HH:mm:ss" /></small>
                    </c:if>
                </div>
            </div>

            <div class="form-group" style="margin-top: 12px;">
                <label><input type="checkbox" name='workDIssue'
                        <c:if test="${detailWorkD.workDIssue == 'Y'}">checked</c:if> /> 문제발생</label>
            </div>

            <div class="form-group">
                <label>특이사항</label>
                <textarea name="workDExtra" rows="2">${detailWorkD.workDExtra}</textarea>
            </div>
        </form>
    </div>
</body>
<script src="/js/workDDetail.js"></script>
</html>