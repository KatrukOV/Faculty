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
    <title><fmt:message key="admin.teachers.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3><fmt:message key="admin.teachers.welcome"/></h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="admin.teachers.table.fullName"/></b></th>
            <th><b><fmt:message key="admin.teachers.table.position"/></b></th>
            <th><b><fmt:message key="admin.teachers.table.setPosition"/></b></th>
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
                                    ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.teachers.select.assistant"/>
                            </option>
                            <option value="ASSOCIATE_PROFESSOR" ${teacher.position.equals('ASSOCIATE_PROFESSOR')
                                    ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.teachers.select.associateProfessor"/>
                            </option>
                            <option value="PROFESSOR" ${teacher.position.equals('PROFESSOR')
                                    ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.teachers.select.professor"/>
                            </option>
                        </select>
                        <input type="hidden" name="teacherId" value="${teacher.teacherId}"/>
                        <input type="hidden" name="command" value="setPosition"/>
                        <input type="submit"
                               value="<fmt:message key="admin.teachers.button.setPosition"/>"/>
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