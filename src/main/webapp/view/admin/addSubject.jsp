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
    <title><fmt:message key="admin.addSubject.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="admin.addSubject.welcome"/></h3>
<hr/>
<jsp:include page="/view/all/message.jsp"/>
<form action="/dispatcher" method="POST">
    <label><fmt:message key="admin.addSubject.subject.title"/> </label>
    <input type="text" name="title" placeholder="Title of Subject"/>
    <br>
    <label><fmt:message key="admin.addSubject.subject.teacher"/>
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
    <input type="submit" value="<fmt:message key="admin.addSubject.button.createSubject"/>"/>
</form>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>