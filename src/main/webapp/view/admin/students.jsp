<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Students</title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3>ALL students</h3>
<div>
    <c:forEach var="student" items="${studentList}">
        <label><b>${student.lastName}</b></label>
        <label><b>${student.name}</b></label>
        <label><b>${student.patronymic}</b></label>
        <label><b>form now: ${student.form}</b></label>
        <label> set form: </label>
        <form action="/dispatcher" method="post">
            <select name="form">
                <option value="FULL_TAME" ${teacher.contract.equals('FULL_TAME') ? 'selected="selected"' : ''}>
                    FULL_TAME
                </option>
                <option value="EXTRAMURAL" ${teacher.contract.equals('EXTRAMURAL') ? 'selected="selected"' : ''}>
                    EXTRAMURAL
                </option>
                <option value="EVENING" ${teacher.contract.equals('EVENING') ? 'selected="selected"' : ''}>
                    EVENING
                </option>
                <option value="DISTANCE" ${teacher.contract.equals('DISTANCE') ? 'selected="selected"' : ''}>
                    DISTANCE
                </option>
            </select>
            <input type="hidden" name="studentId" value="${student.studentId}"/>
            <input type="hidden" name="command" value="setForm"/>
            <input type="submit" value="setForm"/>
        </form>
        <form action="/dispatcher" method="post">
            <label><b>contract now: ${student.contract}</b></label>
            <label> set contract: </label>
            <select name="contract">
                <option value="STATE_ORDER" ${teacher.contract.equals('STATE_ORDER') ? 'selected="selected"' : ''}>
                    STATE_ORDER
                </option>
                <option value="CONTRACT" ${teacher.contract.equals('CONTRACT') ? 'selected="selected"' : ''}>
                    CONTRACT
                </option>
            </select>
            <input type="hidden" name="studentId" value="${student.studentId}"/>
            <input type="hidden" name="command" value="setContract"/>
            <input type="submit" value="setContract"/>
        </form>
    </c:forEach>
</div>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
