<#import "parts/commonPart.ftl" as c>

<@c.page "User Edit">
<h1 class="mb-3">Edit user '${user.username}'</h1>
<div class="form-group mt-3">
    <form action="/user" method="post">
        <div class="form-group row">
            <div class="col-sm-2">
                ID:
            </div>
            <div class="col-sm-6">
                ${user.id}
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-lavel" for="editUsername">Username:</label>
            <div class="col-sm-6">
                <input type="text" name="username"
                       value="${user.username}"
                       class="form-control" id="editUsername"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-lavel" for="editPassword">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       value="${user.password}"
                       class="form-control" id="editPassword"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-lavel" for="editEmail">Email:</label>
            <div class="col-sm-6">
                <input type="text" name="email"
                       value="<#if user.email??>${user.email}</#if>"
                       class="form-control" id="editEmail"/>
            </div>
        </div>
        <#if user.activationCode??>
        <div class="form-group row">
            <div class="col-sm-2 col-form-lavel">Activation code:</div>
            <div class="col-sm-6">${user.activationCode}</div>
        </div>
        </#if>
        <div class="form-group row">
            <label class="col-sm-2 col-form-lavel" for="switch-active">Active:</label>
            <div class="col-sm-6">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" name="active"
                            ${user.active?string("checked", "")}
                           id="switch-active">
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-lavel">Roles:</label>
            <div class="col-sm-6">
                <#list roles as role>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="${role}"
                                ${user.roles?seq_contains(role)?string("checked", "")}
                               id="switch-${role}">
                        <label class="form-check-label" for="switch-${role}">${role}</label>
                    </div>
                </#list>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="userId" value="${user.id}"/>
    </form>
</div>
</@c.page>