<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3>ALL USERS</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Full Name</b></th>
            <th><b>Role</b></th>
            <th><b>Set Role</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>
                    <label> ${user.lastName}</label>
                    <label> ${user.name}</label>
                    <label> ${user.patronymic} </label>
                </td>
                <td>
                    <label> ${user.role} </label>
                </td>
                <td>
                    <form action="/dispatcher" method="post">
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
                        <input type="hidden" name="userId" value="${user.userId}"/>
                        <input type="hidden" name="command" value="setRole"/>
                        <input type="submit" value="Confirm"/>
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