<#import "parts/commonPart.ftl" as c>

<@c.page "System Info">
<h1 class="mb-3">System Info</h1>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">Param</th>
        <th scope="col">Value</th>
    </tr>
    </thead>
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