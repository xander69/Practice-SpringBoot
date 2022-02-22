<#import "parts/commonPart.ftl" as c>

<@c.page "User List">
<h1 class="mb-3">User List</h1>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Name</th>
        <th>Active</th>
        <th>Roles</th>
        <th>Email</th>
        <th>Created</th>
        <th>Changed</th>
        <th>Commands</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td>
            <#if user.active>
                <i class="fas fa-check"></i>
            <#else>
                <i class="fas fa-ban"></i>
            </#if>
            </td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td>${user.email!}</td>
            <td>${user.createDateTime}</td>
            <td>${user.changeDateTime}</td>
            <td>
                <i class="fa-solid fa-pen-to-square"></i>
                <a href="/user/${user.id}">Edit</a>
                <i class="fas fa-user-times"></i>
                <a href="/user/${user.id}/del">Delete</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</@c.page>