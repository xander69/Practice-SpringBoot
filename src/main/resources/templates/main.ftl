<#import "parts/commonPart.ftl" as c>
<#import "parts/scenarioEditPart.ftl" as s>

<@c.page "">
<h1 class="mb-3">Scenario List</h1>

<@s.scen "/" "New Scenario"/>
<#include "parts/scenarioListPart.ftl"/>

</@c.page>