<html>
<body>
<ul>
    <li><a href="${springMacroRequestContext.contextPath}">Главная</a></li>
</ul>

<ul>Статистика по обслуживаемым устройствам:</ul>

<#list controllerStatistics as controller>
    <label>УСД id: #{controller.clientId} статус: <#if (controller.connected!true)>
        онлайн
        <#else>
        офлайн
        </#if>
    </label>
    <br>

</#list>

</body>
</html>