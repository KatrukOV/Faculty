<html>
<head>
    <title>Evaluation</title>
</head>
<body style="text-align:center;">
<h3>Evaluations of , ${sessionScope.get("name")} ${sessionScope.get("lastName")}</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Subject</b></th>
            <th><b>Rating</b></th>
            <th><b>Feedback</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="evaluation" items="${evaluationList}">
            <tr>
                <td><label>${evaluation.title}</label></td>
                <td><label>${evaluation.rating}</label></td>
                <td><label>${evaluation.feedback}</label></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
