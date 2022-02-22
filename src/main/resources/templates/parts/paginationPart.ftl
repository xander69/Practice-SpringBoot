<#macro scenarioPager urlPage scenariosPage>

<#if scenariosPage.getTotalPages() gt 7>
    <#assign
    totalPages = scenariosPage.getTotalPages()
    pageNumber = scenariosPage.getNumber() + 1

    head = (pageNumber > 4) ?then([1, -1], [1, 2, 3])
    tail = (pageNumber < totalPages - 3) ?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
    bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1) ?then([pageNumber - 2, pageNumber - 1], [])
    bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3) ?then([pageNumber + 1, pageNumber + 2], [])
    currPage = (pageNumber > 3 && pageNumber < totalPages -2) ?then([pageNumber], [])

    body = head + bodyBefore + currPage + bodyAfter + tail
    >
<#else>
    <#assign body = 1..scenariosPage.getTotalPages()>
</#if>

<div class="row mt-3">
    <div class="col">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Pages</a>
            </li>
            <#list body as p>
                <#if (p - 1) == scenariosPage.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#elseif p == -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link"
                           href="${urlPage}?page=${p - 1}&amp;size=${scenariosPage.getSize()}<#if filter??>&amp;filter=${filter}</#if>"
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
            <#list [1, 2, 3, 4, 5, 10] as c>
                <#if c == scenariosPage.getSize()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${c}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link"
                           href="${urlPage}?page=${scenariosPage.getNumber()}&amp;size=${c}<#if filter??>&amp;filter=${filter}</#if>"
                           tabindex="-1">${c}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</div>
</#macro>