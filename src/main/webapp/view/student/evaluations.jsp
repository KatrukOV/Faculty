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
    <title><fmt:message key="evaluations.title"/></title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/locate.jsp"/>
<h3><fmt:message key="evaluations.welcome"/>
    ${sessionScope.get("name")} ${sessionScope.get("lastName")}</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="evaluations.table.subject"/></b></th>
            <th><b><fmt:message key="evaluations.table.rating"/></b></th>
            <th><b><fmt:message key="evaluations.table.feedback"/></b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="evaluation" items="${evaluationList}">
            <tr>
                <td><label>${evaluation.title}</label></td>
                <td><label>${evaluation.rating}</label></td>
                <td><label>${evaluation.feedback}</label></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
