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
    <title><fmt:message key="subject.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/locate.jsp"/>
<h3><fmt:message key="subject.welcome"/></h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="subject.table.title"/></b></th>
            <th><b><fmt:message key="subject.table.teacher"/></b></th>
            <c:if test="${role == 'STUDENT'}">
                <th><b><fmt:message key="subject.table.declare"/></b></th>
            </c:if>
            <c:if test="${role == 'ADMIN'}">
                <th><b><fmt:message key="subject.table.remove"/></b></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="subject" items="${subjectList}">
            <tr>
                <td>
                    <label><b>${subject.title}</b></label>
                </td>
                <td>
                    <label> ${subject.lastName}</label>
                    <label> ${subject.name}</label>
                    <label> ${subject.patronymic} </label>
                    <br/>
                    <label> ${subject.position} </label>
                </td>
                <c:if test="${role == 'STUDENT'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                            <input type="hidden" name="command" value="declare"/>
                            <input type="submit"
                                   value="<fmt:message key="subject.button.declare"/>"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${role == 'ADMIN'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                            <input type="hidden" name="command" value="removeSubject"/>
                            <input type="submit"
                                   value="<fmt:message key="subject.button.remove"/>"/>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>
<c:if test="${role == 'TEACHER'}">
    <form action="/dispatcher" method="GET">
        <input type="hidden" name="command" value="getTeacherSubjects"/>
        <input type="submit" value="<fmt:message key="subject.button.mySubjects"/>"/>
    </form>
</c:if>
<br/>
<c:if test="${role == 'ADMIN'}">
    <form action="/dispatcher" method="GET">
        <input type="hidden" name="command" value="addSubject"/>
        <input type="submit" value="<fmt:message key="subject.button.addSubject"/>"/>
    </form>
</c:if>
<br/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>