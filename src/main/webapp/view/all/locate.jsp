<html>
<body>
<form>
    <select id="locate" name="locate" onchange="submit()">
        <option value="en_EN" ${locate == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="ru_RU" ${locate == 'ru_RU' ? 'selected' : ''}>Russia</option>
    </select>
</form>
<hr/>
</body>
</html>
