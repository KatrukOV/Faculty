<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body style="text-align:center;">
<div>
    <c:if test="${not empty info}">
    <p>Info :<c:out value="${info}"/><p>
    </c:if>
</div>
<div>
    <c:if test="${not empty error}">
    <p>Error :<c:out value="${error}"/><p>
    </c:if>
</div>
</body>
</html>
