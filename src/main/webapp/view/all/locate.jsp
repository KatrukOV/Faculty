<html>
<body>
<form>
    <select id="locate" name="locate" onchange="submit()">
        <option value="en" ${locate == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${locate == 'ru' ? 'selected' : ''}>Russia</option>
    </select>
</form>
<hr/>
</body>
</html>
