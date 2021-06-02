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
    <button formaction="${springMacroRequestContext.contextPath}/private/instant-data" formmethod="get">GET_INSTANT_METER_DATA_USPD</button>
    <br>
    <button formaction="${springMacroRequestContext.contextPath}/private/reread-tree" formmethod="get">READ_USPD_TREE</button>
</form>

</body>
</html>