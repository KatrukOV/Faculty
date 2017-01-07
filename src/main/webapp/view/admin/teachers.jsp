<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Teachers</title>
</head>

<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3>ALL TEACHERS</h3>
<div>
    <c:forEach var="teacher" items="${teacherList}">
        <form action="/dispatcher" method="post">
            <label><b>${teacher.lastName}</b></label>
            <label><b>${teacher.name}</b></label>
            <label><b>${teacher.patronymic}</b></label>
            <label><b> position now ${teacher.position}</b></label>
            <label> set position: </label>
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
    </c:forEach>
</div>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
