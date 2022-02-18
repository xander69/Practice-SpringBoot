<#import "parts/commonPart.ftl" as c>

<@c.page "User List">
<h1 class="mb-3">User List</h1>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Name</th>
        <th>Roles</th>
        <th>Commands</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td>
                <a href="/user/${user.id}">Edit</a>
                <a href="/user/${user.id}/del">Delete</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</@c.page>