<#macro scen action buttonTitle>
<form action="${action}" method="post" enctype="multipart/form-data">
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
            <button type="submit" class="btn btn-primary">${buttonTitle}</button>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="<#if scenario??>${scenario.id}</#if>"/>
</form>
</#macro>