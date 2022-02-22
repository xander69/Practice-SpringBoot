<#include "securityPart.ftl">

<#macro login action isRegisterForm>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<#if loginMessage??>
    <div class="alert alert-${loginMessageType}" role="alert">
        ${loginMessage}
    </div>
</#if>
<form action="${action}" method="post" class="my-3">
    <div class="form-group row mt-3">
        <label class="col-sm-2 col-form-lavel" for="loginUsername">User Name:</label>
        <div class="col-sm-6">
            <input type="text" name="username"
                   placeholder="User name"
                   value="<#if user??>${user.username}</#if>"
                   class="form-control${(usernameError??)?string(' is-invalid', '')}"
                   id="loginUsername"/>
            <#if usernameError??>
            <div class="invalid-feedback">${usernameError}</div>
            </#if>
        </div>
    </div>
    <div class="form-group row mt-3">
        <label class="col-sm-2 col-form-lavel" for="loginPassword">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
                   placeholder="Password"
                   value="<#if user??>${user.password}</#if>"
                   class="form-control${(passwordError??)?string(' is-invalid', '')}"
                   id="loginPassword"/>
            <#if passwordError??>
            <div class="invalid-feedback">${passwordError}</div>
            </#if>
        </div>
    </div>
    <#if isRegisterForm>
    <div class="form-group row mt-3">
        <label class="col-sm-2 col-form-lavel" for="loginPassword2">Confirm password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2"
                   placeholder="Retype password"
                   value="<#if user??>${user.password2}</#if>"
                   class="form-control${(password2Error??)?string(' is-invalid', '')}"
                   id="loginPassword2"/>
            <#if password2Error??>
            <div class="invalid-feedback">${password2Error}</div>
            </#if>
        </div>
    </div>
    <div class="form-group row mt-3">
        <label class="col-sm-2 col-form-lavel" for="loginEmail">E-Mail:</label>
        <div class="col-sm-6">
            <input type="email" name="email"
                   placeholder="some@some.com"
                   value="<#if user??>${user.email}</#if>"
                   class="form-control${(emailError??)?string(' is-invalid', '')}"
                   id="loginEmail"/>
            <#if emailError??>
            <div class="invalid-feedback">${emailError}</div>
            </#if>
        </div>
    </div>
    <#if recaptchaUse>
    <div class="col-sm-6 mb-3 mt-3">
        <div class="g-recaptcha" data-sitekey="6LcRHcQUAAAAAIMnBhgkcuPc8zNj7GelQR_ReSz9"></div>
        <#if captchaError??>
        <div class="alert alert-danger" role="alert">${captchaError}</div>
        </#if>
    </div>
    </#if>
    </#if>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div mt-3>
    <#if isRegisterForm>
        <button class="btn btn-primary" type="submit">Register</button>
    <#else>
        <button class="btn btn-primary" type="submit">
            Sign In
            <i class="fas fa-sign-in"></i>
        </button>
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
    <button class="btn btn-primary" type="submit">
        <i class="fas fa-sign-out"></i> Sign Out
    </button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</#macro>