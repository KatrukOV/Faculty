<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Students</title>
</head>
<body style="text-align:center;">
<jsp:include page="/view/all/logout.jsp"/>
<h3>ALL students</h3>
<jsp:include page="/view/all/message.jsp"/>
<div align="center">
    <table border="1">
        <thead>
        <tr>
            <th><b>Full Name</b></th>
            <th><b>Form</b></th>
            <th><b>Set Form</b></th>
            <th><b>Contract</b></th>
            <th><b>Set Contract</b></th>
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
                    <form action="/dispatcher" method="post">
                        <select name="form">
                            <option value="FULL_TAME" ${student.contract.equals('FULL_TAME') ? 'selected="selected"' : ''}>
                                FULL_TAME
                            </option>
                            <option value="EXTRAMURAL" ${student.contract.equals('EXTRAMURAL') ? 'selected="selected"' : ''}>
                                EXTRAMURAL
                            </option>
                            <option value="EVENING" ${student.contract.equals('EVENING') ? 'selected="selected"' : ''}>
                                EVENING
                            </option>
                            <option value="DISTANCE" ${student.contract.equals('DISTANCE') ? 'selected="selected"' : ''}>
                                DISTANCE
                            </option>
                        </select>
                        <input type="hidden" name="studentId" value="${student.studentId}"/>
                        <input type="hidden" name="command" value="setForm"/>
                        <input type="submit" value="setForm"/>
                    </form>
                </td>
                <td>
                    <label><b>${student.contract}</b></label>
                </td>
                <td>
                    <form action="/dispatcher" method="post">
                        <select name="contract">
                            <option value="STATE_ORDER" ${student.contract.equals('STATE_ORDER') ? 'selected="selected"' : ''}>
                                STATE_ORDER
                            </option>
                            <option value="CONTRACT" ${student.contract.equals('CONTRACT') ? 'selected="selected"' : ''}>
                                CONTRACT
                            </option>
                        </select>
                        <input type="hidden" name="studentId" value="${student.studentId}"/>
                        <input type="hidden" name="command" value="setContract"/>
                        <input type="submit" value="setContract"/>
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
