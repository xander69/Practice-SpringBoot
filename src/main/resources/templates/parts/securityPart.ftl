<#assign known = Session.SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    authName = user.getUsername()
    authId = user.getId()
    isAuth = true
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    authName = "unknown"
    authId = -1
    isAuth = false
    isAdmin = false
    >
</#if>