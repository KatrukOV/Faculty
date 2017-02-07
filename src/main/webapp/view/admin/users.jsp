<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="admin.users.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3><fmt:message key="admin.users.welcome"/></h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="admin.users.table.fullName"/></b></th>
            <th><b><fmt:message key="admin.users.table.role"/></b></th>
            <th><b><fmt:message key="admin.users.table.setRole"/></b></th>
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
                    <form action="/dispatcher" method="POST">
                        <select name="role">
                            <option value="STUDENT" ${user.role.equals('STUDENT') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.users.select.student"/>
                            </option>
                            <option value="TEACHER" ${user.role.equals('TEACHER') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.users.select.teacher"/>
                            </option>
                            <option value="ADMIN" ${user.role.equals('ADMIN') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.users.select.admin"/>
                            </option>
                        </select>
                        <input type="hidden" name="userId" value="${user.userId}"/>
                        <input type="hidden" name="command" value="setRole"/>
                        <input type="submit"
                               value="<fmt:message key="admin.users.button.setRole"/>"/>
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