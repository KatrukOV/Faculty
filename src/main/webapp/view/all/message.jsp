<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="text"/>
<div>
    <c:if test="${not empty info}">
    <p><fmt:message key="message.info"/> <c:out value="${info}"/><p>
    </c:if>
</div>
<div>
    <c:if test="${not empty error}">
    <p><fmt:message key="message.error"/><c:out value="${error}"/><p>
    </c:if>
</div>

