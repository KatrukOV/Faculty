<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Subjects</title>
</head>
<body style="text-align:center;">
<h3>Subject: ${title}</h3>
<jsp:include page="/view/all/message.jsp"/>

<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Student</b></th>
            <c:if test="${status == ''}">
                <th><b>Com</b></th>
            </c:if>
            <th><b>View</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="subject" items="${subjectList}">
            <tr>
                <td>
                    <label><b>${subject.title}</b></label>
                </td>
                <td>
                    <form action="/dispatcher" method="POST">
                        <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                        <input type="hidden" name="command" value="getEvaluationsBySubject"/>
                        <input type="submit" value="View"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>

