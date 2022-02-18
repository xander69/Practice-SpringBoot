<#import "parts/commonPart.ftl" as c>

<@c.page "System Info">
<table>
    <tbody>
    <#list params as param>
    <tr>
        <td>${param.key}:</td>
        <td>${param.value}</td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>