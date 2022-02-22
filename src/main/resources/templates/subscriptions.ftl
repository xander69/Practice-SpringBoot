<#import "parts/commonPart.ftl" as c>

<@c.page "Subscriptions of '${userChannel.username}'">

<h1 class="mb-3">${infoType} of '${userChannel.username}'</h1>
<ul class="list-group mb-3">
<#list users as user>
    <li class="list-group-item">
        <a href="/user-scenarios/${user.id}">${user.username}</a>
    </li>
<#else>
    <li class="list-group-item">
        No ${infoType}
    </li>
</#list>
</ul>
</@c.page>