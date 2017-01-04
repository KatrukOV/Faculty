<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body style="text-align:center;">
<h3>ALL USERS</h3>
<div>
    <c:forEach var="user" items="${userList}">
        <form action="/dispatcher" method="post">
            <label><b>${user.lastName}</b></label>
            <label><b>${user.name}</b></label>
            <label><b> ${user.patronymic}</b></label>
            <label><b> role now: ${user.role}</b></label>
            <br>
            <label> set Role: </label>
            <select name="role">
                <option value="STUDENT" ${user.role.equals('STUDENT') ? 'selected="selected"' : ''}>
                    STUDENT
                </option>
                <option value="TEACHER" ${user.role.equals('TEACHER') ? 'selected="selected"' : ''}>
                    TEACHER
                </option>
                <option value="ADMIN" ${user.role.equals('ADMIN') ? 'selected="selected"' : ''}>
                    ADMIN
                </option>
            </select>
            <input type="hidden" name="login" value="${user.userId}"/>
            <input type="hidden" name="command" value="setRole"/>
            <input type="submit" value="Submit"/>
        </form>
    </c:forEach>
</div>
</body>
</html>
