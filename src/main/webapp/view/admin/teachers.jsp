<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Teachers</title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3>ALL TEACHERS</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Full Name</b></th>
            <th><b>Position</b></th>
            <th><b>Set Position</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="teacher" items="${teacherList}">
            <tr>
                <td>
                    <label> ${teacher.lastName}</label>
                    <label> ${teacher.name}</label>
                    <label> ${teacher.patronymic} </label>
                </td>
                <td>
                    <label> ${teacher.position} </label>
                </td>
                <td>
                    <form action="/dispatcher" method="POST">
                        <select name="position">
                            <option value="ASSISTANT" ${teacher.position.equals('ASSISTANT')
                                    ? 'selected="selected"' : ''}>ASSISTANT
                            </option>
                            <option value="ASSOCIATE_PROFESSOR" ${teacher.position.equals('ASSOCIATE_PROFESSOR')
                                    ? 'selected="selected"' : ''}>ASSOCIATE_PROFESSOR
                            </option>
                            <option value="PROFESSOR" ${teacher.position.equals('PROFESSOR')
                                    ? 'selected="selected"' : ''}>PROFESSOR
                            </option>
                        </select>
                        <input type="hidden" name="teacherId" value="${teacher.teacherId}"/>
                        <input type="hidden" name="command" value="setPosition"/>
                        <input type="submit" value="Submit"/>
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
