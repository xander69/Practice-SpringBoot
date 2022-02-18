<#import "parts/commonPart.ftl" as c>
<#import "parts/loginPart.ftl" as l>

<@c.page "Registration">
    <h1 class="mb-3">Registration</h1>
    <@l.login "/register" true/>
</@c.page>