<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="profile.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/locate.jsp"/>
<jsp:include page="/view/all/logout.jsp"/>
<jsp:include page="/view/all/welcome.jsp"/>
<jsp:include page="/view/all/message.jsp"/>
<h1><fmt:message key="profile.welcome"/></h1>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getSubjects">
    <input type="submit" value="<fmt:message key="profile.button.getAllSubjects"/>"/>
</form>
<br>
<c:if test="${role == 'STUDENT'}">
    <form action="/dispatcher" method="GET">
        <input type="hidden" name="command" value="getEvaluationsByStudent">
        <input type="submit" value="<fmt:message key="profile.button.getMyEvaluations"/>"/>
    </form>
</c:if>
</body>
</html>
