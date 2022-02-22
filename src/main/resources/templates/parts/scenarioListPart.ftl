<#include "securityPart.ftl">
<#import "paginationPart.ftl" as p>

<div class="mt-3">
    <form action="${urlPage}" method="get" class="form-inline">
        <div class="row">
            <div class="col">
                <input type="text" name="filter" value="${filter!}"
                       class="form-control"
                       placeholder="Search by name"/>
            </div>
            <div class="col">
                <button type="submit" class="btn btn-primary c">
                    <i class="fas fa-search"></i>
                    Search
                </button>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>

<@p.scenarioPager urlPage scenarioPage/>

<div id="scenarioList">
<#list scenarioPage.content as scenario>
    <div class="card my-3" data-id="${scenario.id}">
        <div class="m-2">
            <#if scenario.iconFilename??>
                <img src="/img/${scenario.iconFilename}"
                     class="rounded float-left mr-3"
                     style="width: 50px; height: 50px;"
                     alt="${scenario.iconFilename}"/>
            </#if>
            <b>${scenario.name}</b><br/>
            <i>${scenario.description}</i>
        </div>
        <div class="card-footer text-muted">
            <div class="row">
                <div class="col-1">
                    ID: ${scenario.id}
                </div>
                <div class="col-3">
                    <#if scenario.createDateTime??> Created: ${scenario.createDateTime?datetime}</#if>
                </div>
                <div class="col-3">
                    <#if scenario.changeDateTime??> Changed: ${scenario.changeDateTime?datetime}</#if>
                </div>
                <div class="col-3">
                    <#if scenario.creator??>
                    Created by <strong><a href="/user-scenarios/${scenario.creator.id}">${scenario.creator.username}</a></strong>
                    </#if>
                </div>
                <div class="col-2 text-end">
                    <#if scenario.creator.id == authId>
                    <i class="fa-solid fa-pen-to-square"></i>
                    <a href="/user-scenarios/${scenario.creator.id}?scenario=${scenario.id}">Edit</a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
<#else>
    <div>
        No scenarios
    </div>
</#list>
</div>

<@p.scenarioPager urlPage scenarioPage/>
