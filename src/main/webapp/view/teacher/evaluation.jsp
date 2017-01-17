<html>
<head>
    <title>Evaluation</title>
</head>
<body style="text-align:center;">
<h3>Create evaluation</h3>
<br/>
<h4>for student: </h4>
<b>${evaluation.lastName}</b>
<b>${evaluation.name}</b>
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
    <%--<br>--%>
    <label>Feedback: </label>
    <input type="text" name="name" placeholder="feedback"/>
    <br>
    <input type="hidden" name="evaluationId" value="${evaluation.evaluationId}"/>
    <input type="hidden" name="command" value="evaluate"/>
    <input type="submit" value="Evaluate"/>
</form>
<hr/>
<jsp:include page="/view/all/toProfile.jsp"/>
</body>
</html>
