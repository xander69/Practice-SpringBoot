<#import "loginPart.ftl" as l>
<#include "securityPart.ftl">

<nav id="navbarContent" class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Web Application</a>
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="/"><i class="fas fa-home"></i> Home</a>
            </li>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user"><i class="fas fa-users"></i> User List</a>
            </li>
            </#if>
            <#if isAuth>
            <li class="nav-item">
                <a class="nav-link" href="/user/profile"><i class="fas fa-address-card"></i> Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user-scenarios/${authId}"><i class="fas fa-list-alt"></i> My Channel</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/sysinfo"><i class="fas fa-desktop"></i> System Info</a>
            </li>
            </#if>
            <#if !isAuth>
            <li class="nav-item">
                <a class="nav-link" href="/register"><i class="fas fa-user-plus"></i> Register</a>
            </li>
            </#if>
        </ul>
        <#if isAuth>
            <@l.logout/>
        </#if>
    </div>
</nav>