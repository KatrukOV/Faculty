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
    <title><fmt:message key="admin.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<jsp:include page="/view/all/welcome.jsp"/>
<jsp:include page="/view/all/message.jsp"/>
<br>
<h2><fmt:message key="admin.welcome"/></h2>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getUsers">
    <input type="submit" value="<fmt:message key="admin.button.getUsers"/>"/>
</form>
<br>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getStudents">
    <input type="submit" value="<fmt:message key="admin.button.getStudents"/>"/>
</form>
<br>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getTeachers">
    <input type="submit" value="<fmt:message key="admin.button.getTeachers"/>"/>
</form>
<br>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getSubjects">
    <input type="submit" value="<fmt:message key="admin.button.getSubjects"/>"/>
</form>
<h3><fmt:message key="admin.period"/>$ {periodStatus}</h3>
<h3><fmt:message key="admin.lastDate"/> ${periodDate}</h3>
<form action="/dispatcher" method="POST">
    <input type="hidden" name="command" value="setDistribution">
    <input type="submit" value="<fmt:message key="admin.button.setDistribution"/>"/>
</form>
<form action="/dispatcher" method="POST">
    <input type="hidden" name="command" value="setLearning">
    <input type="submit" value="<fmt:message key="admin.button.setLearning"/>"/>
</form>
</body>
</html>