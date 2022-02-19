<#import "parts/commonPart.ftl" as c>

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

<form action="/" method="post" enctype="multipart/form-data">
    <div class="row mt-3">
        <div class="col">
            <input type="text" name="name"
                   placeholder="Name"
                   value="<#if scenario??>${scenario.name!}</#if>"
                   class="form-control${(nameError??)?string(' is-invalid', '')}"/>
            <#if nameError??>
            <div class="invalid-feedback">${nameError}</div>
            </#if>
        </div>
        <div class="col">
            <input type="text" name="description"
                   placeholder="Description"
                   value="<#if scenario??>${scenario.description!}</#if>"
                   class="form-control${(descriptionError??)?string(' is-invalid', '')}"/>
            <#if descriptionError??>
            <div class="invalid-feedback">${descriptionError}</div>
            </#if>
        </div>
        <div class="col">
            <input type="file" name="icon" class="custom-file-input" id="customFile"/>
        </div>
        <div class="col">
            <button type="submit" class="btn btn-primary">New Scenario</button>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

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
            </div>
        </div>
    </div>
</div>
<#else>
    <div>
        No scenarios
    </div>
</#list>
</@c.page>