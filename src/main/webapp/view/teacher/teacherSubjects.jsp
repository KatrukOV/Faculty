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
    <title><fmt:message key="teacher.subjects.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="teacher.subjects.welcome"/></h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="teacher.subjects.table.title"/></b></th>
            <th><b><fmt:message key="teacher.subjects.table.view"/></b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="subject" items="${subjectList}">
            <tr>
                <td>
                    <label><b>${subject.title}</b></label>
                </td>
                <td>
                    <form action="/dispatcher" method="POST">
                        <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                        <input type="hidden" name="command" value="getEvaluationsBySubject"/>
                        <input type="submit"
                               value="<fmt:message key="teacher.subjects.button.view"/>"/>
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

