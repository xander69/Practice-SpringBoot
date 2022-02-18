<#import "loginPart.ftl" as l>
<#include "securityPart.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Web Application</a>
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
            <#if isAuth>
            <li class="nav-item">
                <a class="nav-link" href="/sysinfo">System Info</a>
            </li>
            </#if>
            <#if !isAuth>
            <li class="nav-item">
                <a class="nav-link" href="/register">Register</a>
            </li>
            </#if>
        </ul>
        <#if isAuth>
            <@l.logout/>
        </#if>
    </div>
</nav>