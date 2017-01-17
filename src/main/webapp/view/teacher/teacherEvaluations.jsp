<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Subject</title>
</head>
<body style="text-align:center;">
<h3>Subject: ${title}</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Student</b></th>
            <c:if test="${periodStatus == 'DISTRIBUTION'}">
                <th><b>Status</b></th>
                <th><b>Confirm / Reject</b></th>
            </c:if>
            <c:if test="${periodStatus == 'LEARNING'}">
                <th><b>Evaluate</b></th>
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
                                    CONFIRMED
                                </option>
                                <option value="REJECTED" ${evaluation.status.equals('REJECTED') ? 'selected="selected"' : ''}>
                                    REJECTED
                                </option>
                            </select>
                            <input type="hidden" name="evaluationId"
                                   value="${evaluation.evaluationId}"/>
                            <input type="hidden" name="command" value="setCofirmOrReject"/>
                            <input type="submit" value="Go"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${periodStatus == 'LEARNING'}">
                    <td>
                        <form action="/dispatcher" method="POST">
                            <input type="hidden" name="evaluationId"
                                   value="${evaluation.evaluationId}"/>
                            <input type="hidden" name="command" value="evaluate"/>
                            <input type="submit" value="Evaluate"/>
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

