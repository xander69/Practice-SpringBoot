<#include "securityPart.ftl">

<#list scenarios as scenario>
<div class="card my-3">
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
    <div class="card-footer text-muted" style="font-size:0.7rem;">
        <div class="row">
            <div class="col">
                ID: ${scenario.id}
            </div>
            <div class="col">
                <#if scenario.createDateTime??> Created: ${scenario.createDateTime?datetime}</#if>
            </div>
            <div class="col">
                <#if scenario.changeDateTime??> Changed: ${scenario.changeDateTime?datetime}</#if>
            </div>
            <div class="col">
                <#if scenario.creator??> Created by ${scenario.creator.username}</#if>
                <#if scenario.creator.id == authId>
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