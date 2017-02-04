<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="registration.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="registration.welcome"/></h3>
<hr/>
<jsp:include page="/view/all/message.jsp"/>
<form action="/dispatcher" method="POST">
    <label>Last name: <fmt:message key="registration.lastName"/></label>
    <input type="text" name="lastName" placeholder="Last Name"/>
    <br>
    <label>Name: <fmt:message key="registration.name"/></label>
    <input type="text" name="name" placeholder="Name"/>
    <br>
    <label>Patronymic: <fmt:message key="registration.patronymic"/></label>
    <input type="text" name="patronymic" placeholder="Patronymic"/>
    <br>
    <label>Username: <fmt:message key="registration.username"/> </label>
    <input type="text" name="username" placeholder="Username"/>
    <br>
    <label>Password: <fmt:message key="registration.password"/></label>
    <input type="password" name="password" placeholder="Password"/>
    <br>
    <label>Password repeat: <fmt:message key="registration.passwordRepeat"/> </label>
    <input type="password" name="confirmPassword" placeholder="Confirm Password"/>
    <br>
    <input type="hidden" name="command" value="registration"/>
    <input type="submit" value="Sign up <fmt:message key="registration.signUp"/>"/>
</form>
<hr/>
<a href="/">
    <input type="button" value="Back to login <fmt:message key="registration.back"/>"/>
</a>
</body>
</html>
