<#import "parts/commonPart.ftl" as c>
<#import "parts/loginPart.ftl" as l>

<@c.page "Registration">
    <#if message??>
        <div class="error">${message}</div><br/>
    </#if>
    <@l.login "/register" "Register"/>
</@c.page>