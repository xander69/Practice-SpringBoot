<#import "parts/commonPart.ftl" as c>

<@c.page "Scenarios">
<div id="scenario-form">
    <strong>New Scenario:</strong>
    <form action="/" method="POST" enctype="multipart/form-data">
        <input type="text" name="name" placeholder="Name:"/>
        <input type="text" name="descr" placeholder="Description:"/>
        <input type="file" name="icon" placeholder="Icon:"/>
        <input type="submit" value="Add"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</div>
<div id="scenario-list">
    <strong>Scenario List:</strong>
    <div id="scenario-list-filter">
        <form action="/" method="GET">
            Filter:
            <input type="text" name="filter" placeholder="Name of scenario" value="${filter!}"/>
            <input type="submit" value="Find"/>
            <span class="comment">(found ${scenarios?size} scenarios)</span>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
    <ul>
    <#list scenarios as scenario>
        <li class="list-item">
            <#if scenario.iconFilename??>
                <img class="scenario-icon" src="/img/${scenario.iconFilename}""/>
            </#if>
            <b>${scenario.name}</b> (ID: ${scenario.id})
            <#if scenario.createDateTime??>Created: ${scenario.createDateTime}<br/></#if>
            <#if scenario.changeDateTime??>Changed: ${scenario.changeDateTime}<br/></#if>
            <#if scenario.description??><span class="description">${scenario.description}</span><br/></#if>
            <#if scenario.creator??><i>(created by ${scenario.creator.username})</i></#if>
        </li>
    <#else>
        <div>
            <i>No scenarios</i>
        </div>
    </#list>
    </ul>
</div>
</@c.page>