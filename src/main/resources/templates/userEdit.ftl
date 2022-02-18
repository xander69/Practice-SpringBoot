<#import "parts/commonPart.ftl" as c>

<@c.page "User Edit '${user.username}'">
<form action="/user" method="post">
    <table>
        <tbody>
        <tr>
            <th>ID:</th>
            <td>${user.id}</td>
        </tr>
        <tr>
            <th>Username:</th>
            <td><input type="text" name="username" value="${user.username}"/></td>
        </tr>
        <tr>
            <th>Password:</th>
            <td><input type="password" name="password" value="${user.password}"/></td>
        </tr>
        <tr>
            <th>Roles:</th>
            <td>
            <#list roles as role>
                <div>
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
                </div>
            </#list>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" name="userId" value="${user.id}"/>
    <input type="submit" value="Save"/>
</form>
</@c.page>