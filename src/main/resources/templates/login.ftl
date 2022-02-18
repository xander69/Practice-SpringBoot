<#import "parts/commonPart.ftl" as c>
<#import "parts/loginPart.ftl" as l>

<@c.page "Login">
    <@l.login "/login" "Sign In"/>
    <br/>
    <div>
        <a href="/register">Register new user</a>
    </div>
</@c.page>