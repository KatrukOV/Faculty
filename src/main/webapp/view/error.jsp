<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<h3><fmt:message key="error.sorry"/> ${error}</h3>
<a href="/"><input type="button" value="<fmt:message key="error.button.back"/>"/></a>
</body>
</html>