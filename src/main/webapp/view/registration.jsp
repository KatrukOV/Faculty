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
    <title><fmt:message key="registration.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="registration.welcome"/></h3>
<hr/>
<jsp:include page="/view/all/message.jsp"/>
<form action="/dispatcher" method="POST">
    <label><fmt:message key="registration.lastName"/></label>
    <input type="text" name="lastName" placeholder="Last Name"/>
    <br>
    <label><fmt:message key="registration.name"/></label>
    <input type="text" name="name" placeholder="Name"/>
    <br>
    <label><fmt:message key="registration.patronymic"/></label>
    <input type="text" name="patronymic" placeholder="Patronymic"/>
    <br>
    <label><fmt:message key="registration.username"/> </label>
    <input type="text" name="username" placeholder="Username"/>
    <br>
    <label><fmt:message key="registration.password"/></label>
    <input type="password" name="password" placeholder="Password"/>
    <br>
    <label><fmt:message key="registration.passwordRepeat"/> </label>
    <input type="password" name="confirmPassword" placeholder="Confirm Password"/>
    <br>
    <input type="hidden" name="command" value="registration"/>
    <input type="submit" value="<fmt:message key="registration.signUp"/>"/>
</form>
<hr/>
<a href="/">
    <input type="button" value="<fmt:message key="registration.back"/>"/>
</a>
</body>
</html>
