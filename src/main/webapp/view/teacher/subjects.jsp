<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Subjects</title>
</head>
<body style="text-align:center;">
<h3>Teacher subjects</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Title Subjects</b></th>
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
                    <label> ${subject.lastName}</label>
                    <label> ${subject.name}</label>
                    <label> ${subject.patronymic} </label>
                    <br/>
                    <label> ${subject.position} </label>
                </td>
                <c:if test="${role == 'STUDENT'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                            <input type="hidden" name="command" value="declare"/>
                            <input type="submit" value="Declare"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${role == 'ADMIN'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                            <input type="hidden" name="command" value="removeSubject"/>
                            <input type="submit" value="Remove"/>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>
<c:if test="${role == 'ADMIN'}">
    <form action="/dispatcher" method="GET">
        <input type="hidden" name="command" value="addSubject"/>
        <input type="submit" value="addSubject"/>
    </form>
</c:if>
<br/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>

