<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="page"/>

<!DOCTYPE html>
<html lang="${locate}">
<head>
    <title><fmt:message key="index.title"/></title>
</head>

<h3><fmt:message key="index.welcome"/></h3>
<h3><fmt:message key="index.do"/></h3>

<body style="text-align:center;">
<form>
    <select id="locate" name="locate" onchange="submit()">
        <option value="en" ${locate == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${locate == 'ru' ? 'selected' : ''}>Russia</option>
    </select>
</form>
<hr/>
<form method="POST">
    <label for="username"><fmt:message key="index.username"/>: </label>
    <input type="text" id="username" name="username">
    <br>
    <label for="password"><fmt:message key="index.password"/>: </label>
    <input type="password" id="password" name="password">
    <br>
    <input type="hidden" name="command" value="login"/>
    <fmt:message key="index.button.submit" var="buttonValue"/>
    <input type="submit" name="submit" value="${buttonValue}">
</form>
<hr/>
<%--<form action="/dispatcher" method="GET">--%>
<%--<input type="hidden" name="command" value="registration"/>--%>
<%--<input type="submit" value="<fmt:message key="index.registration"/>"/>--%>
<%--</form>--%>
<a href="view/registration.jsp">
    <input type="button" value="<fmt:message key="index.registration"/>"/>
</a>

</body>
</html>
