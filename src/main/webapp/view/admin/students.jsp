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
    <title><fmt:message key="admin.students.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3><fmt:message key="admin.students.welcome"/></h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="admin.students.table.fullName"/></b></th>
            <th><b><fmt:message key="admin.students.table.form"/></b></th>
            <th><b><fmt:message key="admin.students.table.setForm"/></b></th>
            <th><b><fmt:message key="admin.students.table.contract"/></b></th>
            <th><b><fmt:message key="admin.students.table.setContract"/></b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="student" items="${studentList}">
            <tr>
                <td>
                    <label> ${student.lastName}</label>
                    <label> ${student.name}</label>
                    <label> ${student.patronymic} </label>
                </td>
                <td>
                    <label>${student.form}</label>
                </td>
                <td>
                    <form action="/dispatcher" method="POST">
                        <select name="form">
                            <option value="FULL_TIME" ${student.contract.equals('FULL_TIME') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.fullTime"/>
                            </option>
                            <option value="EXTRAMURAL" ${student.contract.equals('EXTRAMURAL') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.extramural"/>
                            </option>
                            <option value="EVENING" ${student.contract.equals('EVENING') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.evening"/>
                            </option>
                            <option value="DISTANCE" ${student.contract.equals('DISTANCE') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.distance"/>
                            </option>
                        </select>
                        <input type="hidden" name="studentId" value="${student.studentId}"/>
                        <input type="hidden" name="command" value="setForm"/>
                        <input type="submit"
                               value="setForm <fmt:message key="admin.students.button.setForm"/>"/>
                    </form>
                </td>
                <td>
                    <label><b>${student.contract}</b></label>
                </td>
                <td>
                    <form action="/dispatcher" method="post">
                        <select name="contract">
                            <option value="STATE_ORDER" ${student.contract.equals('STATE_ORDER') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.stateOrder"/>
                            </option>
                            <option value="CONTRACT" ${student.contract.equals('CONTRACT') ? 'selected="selected"' : ''}>
                                <fmt:message key="admin.students.select.contract"/>
                            </option>
                        </select>
                        <input type="hidden" name="studentId" value="${student.studentId}"/>
                        <input type="hidden" name="command" value="setContract"/>
                        <input type="submit"
                               value="setContract <fmt:message key="admin.students.button.setContract"/>"/>
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