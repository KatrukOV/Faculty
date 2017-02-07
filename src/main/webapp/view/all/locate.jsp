<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="locate"
       value="${not empty param.locate ? param.locate : not empty locate ? locate : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${locate}"/>
<fmt:setBundle basename="text"/>
<form>
    <select id="locate" name="locate" onchange="submit()">
        <option value="en_EN" ${locate == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="ru_RU" ${locate == 'ru_RU' ? 'selected' : ''}>Russia</option>
    </select>
</form>
<hr/>
