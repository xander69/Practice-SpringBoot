<#import "parts/commonPart.ftl" as c>
<#import "parts/scenarioEditPart.ftl" as s>
<#include "parts/securityPart.ftl"/>

<@c.page "User scenarios">

<h1 class="mb-3">Scenarios of '${authName}'</h1>

<#if isCurrentUser>
<@s.scen "/user-scenarios/${authId}" "Save Scenario"/>
</#if>
<#include "parts/scenarioListPart.ftl"/>

</@c.page>