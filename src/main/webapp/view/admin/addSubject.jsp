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
    <label>Teacher: </label>
    <select name="teacher">
        <c:forEach var="item" items="${teacherList}">
            <option value="${item.key}" ${item.key == teacherList ? 'selected="selected"' : ''}>
                    ${item.value}
            </option>
        </c:forEach>
    </select>
    <br>
    <select id="teacher" name="teacher">
        <c:forEach items="${teacherList}" var="teacher">
            <option value="<c:out value="${teacher}"/>">
                <c:out value="${teacher.lastName}"/>
                <c:out value=" ${teacher.name}"/>
                <c:out value=" ${teacher.patronymic}"/>
            </option>
        </c:forEach>
    </select>
    <br>
    <input type="hidden" name="command" value="createSubject"/>
    <input type="submit" value="Create"/>
</form>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
