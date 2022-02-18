<#import "loginPart.ftl" as l>
<#import "menuPart.ftl" as m>

<#macro page title>
<!DOCTYPE HTML>
<html lang="ru">
<head>
    <title>Web Application - ${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="/static/style.css" type="text/css"/>
    <link rel="icon" href="/static/favicon.ico" type="image/ico"/>
</head>
<body>
<div id="content">
<#if username??>
    <div style="float: right">
        <@l.logout username/>
    </div>
</#if>
    <div class="header">
        ${title}
    </div>
<#if username??>
    <div id="main-menu">
        <@m.mainMenu/>
    </div>
</#if>
    <#nested>
    <div class="footer">
        Xander.org (c) 2022
    </div>
</div>
</body>
</html>
</#macro>