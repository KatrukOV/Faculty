<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>

<body style="text-align:center;">

<jsp:include page="/view/all/logout.jsp"/>
<jsp:include page="/view/all/welcome.jsp"/>
<jsp:include page="/view/all/message.jsp"/>
<h1> You can: </h1>
<form action="/dispatcher" method="GET">
    <input type="hidden" name="command" value="getSubjects">
    <input type="submit" value="get All Subjects"/>
</form>
<br>
<c:if test="${role == 'STUDENT'}">
    <form action="/dispatcher" method="GET">
        <input type="hidden" name="command" value="getEvaluationsByStudent">
        <input type="submit" value="get My evaluations"/>
    </form>
</c:if>
</body>
</html>
