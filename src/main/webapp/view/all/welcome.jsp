<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="text"/>
<div>
    <h2><fmt:message
            key="welcome.hi"/> ${sessionScope.get("name")} ${sessionScope.get("lastName")}</h2>
    <h3><fmt:message key="welcome.username"/> ${sessionScope.get("username")}</h3>
    <h3><fmt:message key="welcome.role"/> ${sessionScope.get("role")}</h3>
    <c:if test="${role == 'TEACHER'}">
        <h2><fmt:message key="welcome.position"/> ${sessionScope.get("position")}</h2>
    </c:if>
    <c:if test="${role == 'STUDENT'}">
        <h3><fmt:message key="welcome.contract"/> ${sessionScope.get("contract")}</h3>
        <h3><fmt:message key="welcome.form"/>${sessionScope.get("form")}</h3>
    </c:if>
</div>