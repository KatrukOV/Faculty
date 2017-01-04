<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body style="text-align:center;">
<div>
    <h2>Hi, ${sessionScope.get("name")} ${sessionScope.get("lastName")}</h2>
    <h3>Username: ${sessionScope.get("username")}</h3>
    <h3>Role: ${sessionScope.get("role")}</h3>
    <c:if test="${role == 'TEACHER'}">
        <h4>Position: ${sessionScope.get("position")}</h4>
    </c:if>
    <c:if test="${role == 'STUDENT'}">
        <h4>Contract: ${sessionScope.get("contract")}</h4>
        <h4>Form: ${sessionScope.get("form")}</h4>
    </c:if>
</div>
</body>
</html>
