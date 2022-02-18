<#assign known = Session.SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    authName = user.getUsername()
    isAuth = true
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    authName = "unknown"
    isAuth = false
    isAdmin = false
    >
</#if>