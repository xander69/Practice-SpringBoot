<#macro scenarioPager urlPage scenariosPage>
<div class="row mt-3">
    <div class="col">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Pages</a>
            </li>
            <#list 1..scenariosPage.getTotalPages() as p>
                <#if (p - 1) == scenariosPage.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link"
                           href="${urlPage}?page=${p - 1}&size=${scenariosPage.getSize()}<#if filter??>&filter=${filter}</#if>"
                           tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
    <div class="col">
        <ul class="pagination float-end">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Scenarios Per Page</a>
            </li>
            <#list [1, 5, 10, 25, 50] as c>
                <#if c == scenariosPage.getSize()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${c}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link"
                           href="${urlPage}?page=${scenariosPage.getNumber()}&size=${c}<#if filter??>&filter=${filter}</#if>"
                           tabindex="-1">${c}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</div>
</#macro>