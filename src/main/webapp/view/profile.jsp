<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>

<body style="text-align:center;">

<jsp:include page="/view/logout.jsp"/>
<jsp:include page="/view/welcome.jsp"/>

<h1> You can: </h1>
<form action="/dispatcher" method="get">
    <input type="hidden" name="command" value="getSubjects">
    <input type="submit" value="get All Subjects"/>
</form>
<br>




</body>
</html>
