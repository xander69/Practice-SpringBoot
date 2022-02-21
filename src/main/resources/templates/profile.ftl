<#import "parts/commonPart.ftl" as c>

<@c.page "Profile">
<h1 class="mb-3">${username}</h1>
<form method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-lavel" for="loginPassword">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" id="loginPassword" value=""/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-lavel" for="loginEmail">E-Mail:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" id="loginEmail" value="${email!''}"/>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">Save</button>
</form>
</@c.page>