<#import "parts/commonPart.ftl" as c>
<#import "parts/loginPart.ftl" as l>

<@c.page "Login">
    <h1 class="mb-3">Login</h1>
    <@l.login "/login" false/>
</@c.page>