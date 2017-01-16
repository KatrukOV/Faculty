<html>
<head>
    <title>Admin</title>
</head>
<body style="text-align:center;">

<jsp:include page="/view/all/logout.jsp"/>
<jsp:include page="/view/all/welcome.jsp"/>
<jsp:include page="/view/all/message.jsp"/>
<br>
<h2> You can: </h2>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getUsers">
    <input type="submit" value="get All Users"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getStudents">
    <input type="submit" value="get All Students"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getTeachers">
    <input type="submit" value="get All Teachers"/>
</form>
<br>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getSubjects">
    <input type="submit" value="get All Subjects"/>
</form>

<h3>
    Period: ${pSatus}
    Last date: ${pDate}
</h3>

<form action="/dispatcher" method="POST">
    <input type="hidden" name="command" value="set">
    <input type="submit" value="get All Subjects"/>
</form>


</body>
</html>
