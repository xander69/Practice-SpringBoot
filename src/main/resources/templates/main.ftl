<#import "parts/commonPart.ftl" as c>
<#import "parts/scenarioEditPart.ftl" as s>

<@c.page "">
<h1 class="mb-3">Scenario List</h1>

<form action="/" method="get" class="form-inline">
    <div class="row">
        <div class="col">
            <input type="text" name="filter" value="${filter!}"
                   class="form-control"
                   placeholder="Search by name"/>
        </div>
        <div class="col">
            <button type="submit" class="btn btn-primary c">Search</button>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<@s.scen "/" "New Scenario"/>
<#include "parts/scenarioListPart.ftl"/>

</@c.page>