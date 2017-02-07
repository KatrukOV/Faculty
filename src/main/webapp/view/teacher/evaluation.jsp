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
    <title><fmt:message key="evaluation.title"/></title>
</head>
<body style="text-align:center;">
<h3><fmt:message key="evaluation.welcome"/></h3>
<h4><fmt:message key="evaluation.student"/>
    <b>${evaluation.lastName}</b>
    <b>${evaluation.name}</b>
</h4>
<b><fmt:message key="evaluation.rating"/> ${evaluation.rating}</b>
<b><fmt:message key="evaluation.feedback"/> ${evaluation.feedback}</b>
<hr/>
<jsp:include page="/view/all/message.jsp"/>
<form action="/dispatcher" method="POST">
    <select name="rating">
        <option value="A" ${evaluation.rating.equals('A') ? 'selected="selected"' : ''}>
            A
        </option>
        <option value="B" ${evaluation.rating.equals('B') ? 'selected="selected"' : ''}>
            B
        </option>
        <option value="C" ${evaluation.rating.equals('C') ? 'selected="selected"' : ''}>
            C
        </option>
        <option value="D" ${evaluation.rating.equals('D') ? 'selected="selected"' : ''}>
            D
        </option>
        <option value="E" ${evaluation.rating.equals('E') ? 'selected="selected"' : ''}>
            E
        </option>
        <option value="Fx" ${evaluation.rating.equals('Fx') ? 'selected="selected"' : ''}>
            Fx
        </option>
        <option value="F" ${evaluation.rating.equals('F') ? 'selected="selected"' : ''}>
            F
        </option>
    </select>
    <label><fmt:message key="evaluation.addFeedback"/></label>
    <input type="text" name="feedback" placeholder="feedback"/>
    <input type="hidden" name="evaluationId" value="${evaluation.evaluationId}"/>
    <input type="hidden" name="command" value="evaluate"/>
    <input type="submit" value="<fmt:message key="evaluation.button.confirm"/>"/>
</form>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
