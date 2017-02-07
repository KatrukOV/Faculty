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
    <title><fmt:message key="teacher.evaluation.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="teacher.evaluation.welcome"/> ${title}</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b><fmt:message key="teacher.evaluation.table.student"/></b></th>
            <c:if test="${periodStatus == 'DISTRIBUTION'}">
                <th><b><fmt:message key="teacher.evaluation.table.status"/></b></th>
                <th><b><fmt:message key="teacher.evaluation.table.confirm-reject"/></b></th>
            </c:if>
            <c:if test="${periodStatus == 'LEARNING'}">
                <th><b><fmt:message key="teacher.evaluation.table.evaluate"/></b></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="evaluation" items="${evaluationList}">
            <tr>
                <td>
                    <label><b>${evaluation.lastName}</b></label>
                    <label><b>${evaluation.name}</b></label>
                    <label><b>${evaluation.patronymic}</b></label>
                </td>
                <c:if test="${periodStatus == 'DISTRIBUTION'}">
                    <td>
                        <label><b>${evaluation.status}</b></label>
                    </td>
                    <td>
                        <form action="/dispatcher" method="POST">
                            <select name="status">
                                <option value="CONFIRMED" ${evaluation.status.equals('CONFIRMED') ? 'selected="selected"' : ''}>
                                    <fmt:message key="teacher.evaluation.confirm"/>
                                </option>
                                <option value="REJECTED" ${evaluation.status.equals('REJECTED') ? 'selected="selected"' : ''}>
                                    <fmt:message key="teacher.evaluation.reject"/>
                                </option>
                            </select>
                            <input type="hidden" name="evaluationId"
                                   value="${evaluation.evaluationId}"/>
                            <input type="hidden" name="command" value="setConfirmOrReject"/>
                            <input type="submit"
                                   value="<fmt:message key="teacher.evaluation.button.go"/>"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${periodStatus == 'LEARNING'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="evaluationId"
                                   value="${evaluation.evaluationId}"/>
                            <input type="hidden" name="command" value="toEvaluation"/>
                            <input type="submit"
                                   value="<fmt:message key="teacher.evaluation.button.evaluate"/>"/>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>

