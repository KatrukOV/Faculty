<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<h3>
    Sorry
    Error: ${error}
</h3>
<a href="/index.jsp"><input type="button" value="back"/></a>
</body>
</html>