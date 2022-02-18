<#macro login action buttonTitle>
<#if errorMessage??>
    <div class="error">${errorMessage}</div><br/>
</#if>
<#if logoutMessage??>
    <div class="notification">${logoutMessage}</div><br/>
</#if>
<form action="${action}" method="POST">
    <table>
        <tbody>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="${buttonTitle}"/></td>
        </tr>
        </tbody>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>

<#macro logout username>
<form action="/logout" method="post">
    <span id="auth-info">Authenticated as <b>${username}</b></span>
    <input type="submit" value="Sign Out"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>