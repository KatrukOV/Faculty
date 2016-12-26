<html>
<head>
    <title>Profile</title>
</head>

<%--<jsp:include page="/jsp/all/logout.jsp"/>--%>
<%--<jsp:include page="/jsp/all/welcome.jsp"/>--%>

<body style="text-align:center;">
<br>

<h1> You can: </h1>


<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllHumans">
    <input type="submit" value="get All Humans"/>
</form>

<br>
<br>

<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllStudents">
    <input type="submit" value="get All Students"/>
</form>

<br>
<br>

<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllTeachers">
    <input type="submit" value="get All Teachers"/>
</form>

<br>
<br>

<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getAllDisciplines">
    <input type="submit" value="get All Disciplines"/>
</form>


</body>
</html>
