<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Subject</title>
</head>
<body style="text-align:center;">
<h3>Create Subject</h3>
<hr/>
<jsp:include page="/view/all/message.jsp"/>
<form action="/dispatcher" method="POST">
    <label>Title: </label>
    <input type="text" name="title" placeholder="Title of Subject"/>
    <br>
    <label>Teacher:
        <select name="teacherId">
            <c:forEach items="${teacherList}" var="teacher">
                <option value="<c:out value="${teacher.teacherId}"/>">
                    <c:out value="${teacher.lastName}"/>
                    <c:out value=" ${teacher.name}"/>
                    <c:out value=" ${teacher.patronymic}"/>
                </option>
            </c:forEach>
        </select>
    </label>
    <br>
    <input type="hidden" name="command" value="createSubject"/>
    <input type="submit" value="Create"/>
</form>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
