<#import "parts/commonPart.ftl" as c>
<#import "parts/scenarioEditPart.ftl" as s>
<#include "parts/securityPart.ftl"/>

<@c.page "User scenarios">

<h1 class="mb-3">Channel of '${userChannel.username}'</h1>
<#if !isCurrentUser>
    <#if isSubscriber>
    <a class="btn btn-secondary" href="/user/unsubscribe/${userChannel.id}">
        Unsubscribe <i class="fas fa-minus-circle"></i>
    </a>
    <#else>
    <a class="btn btn-primary" href="/user/subscribe/${userChannel.id}">
        Subscribe <i class="fas fa-plus-circle"></i>
    </a>
    </#if>
</#if>

<div class="container my-3">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="card-title">Subscriptions</div>
                    <div class="card-text">
                        <i class="fas fa-user"></i>
                        <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="card-title">Subscriber</div>
                    <div class="card-text">
                        <i class="far fa-user"></i>
                        <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#if isCurrentUser>
<@s.scen "/user-scenarios/${authId}" "Save Scenario"/>
</#if>
<#include "parts/scenarioListPart.ftl"/>

</@c.page>