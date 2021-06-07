<html>
<head>
    <style>
    </style>
</head>
<body>
<ul>
    <li><a href="${springMacroRequestContext.contextPath}">Главная</a></li>
</ul>
<br>

<h1>Перечень запросов:</h1>
<form>
    <label>Выберите ИД УСД:</label><br>
    <select name="usdId">
        <option value=""></option>
        <#list onlineControllersIds as controllerId>
            <option value=${controllerId}>УСД id = ${controllerId}</option>
        </#list>
    </select>
    <br>
    <button formaction="${springMacroRequestContext.contextPath}/private/controller-info" formmethod="get">Получить информацию</button>
</form>

</body>
</html>