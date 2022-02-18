<#include "securityPart.ftl">

<#macro login action isRegisterForm>
<#if errorMessage??>
    <div class="invalid-feedback d-block mb-3">
        ${errorMessage}
    </div>
</#if>
<#if logoutMessage??>
    <div class="valid-feedback d-block mb-3">
        ${logoutMessage}
    </div>
</#if>
<form action="${action}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-lavel" for="loginUsername">User Name:</label>
        <div class="col-sm-6">
            <input type="text" name="username"
                   class="form-control"
                   placeholder="User name"
                   id="loginUsername"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-lavel" for="loginPassword">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control"
                   placeholder="Password"
                   id="loginPassword"/>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div>
    <#if isRegisterForm>
        <button class="btn btn-primary" type="submit">Register</button>
    <#else>
        <button class="btn btn-primary" type="submit">Sign In</button>
        <div class="mt-3">
            <a href="/register">Sign Up</a>
        </div>
    </#if>
    </div>
</form>
</#macro>

<#macro logout>
<form class="d-flex" action="/logout" method="post">
    <div class="navbar-text" style="width: 150px;">Welcome, ${authName}!</div>
    <button class="btn btn-primary" type="submit">Sign Out</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</#macro>