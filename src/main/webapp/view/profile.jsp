<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>

<body style="text-align:center;">

<div>
    <h2>Hi, ${sessionScope.get("name")} ${sessionScope.get("lastName")}</h2>
    <h3>Username: ${sessionScope.get("username")}</h3>
    <h3>Role: ${sessionScope.get("role")}</h3>
    <c:if test="${role == 'TEACHER'}">
        <h4>Position: ${sessionScope.get("position")}</h4>
    </c:if>
    <c:if test="${role == 'STUDENT'}">
        <h4>Contract: ${sessionScope.get("contract")}</h4>
        <h4>Form: ${sessionScope.get("form")}</h4>
    </c:if>
    <br>
</div>
<br>
<form style="text-align:right;" action="/dispatcher" method="post">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="Log out"/>
</form>
<h1> You can: </h1>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllHumans">
    <input type="submit" value="get All Humans"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllStudents">
    <input type="submit" value="get All Students"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllTeachers">
    <input type="submit" value="get All Teachers"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllDisciplines">
    <input type="submit" value="get All Disciplines"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="redirectToProfile"/>
    <input type="submit" value="go to profile "/>
</form>
</body>
</html>
