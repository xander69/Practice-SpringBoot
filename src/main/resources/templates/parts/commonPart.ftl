<#import "loginPart.ftl" as l>
<#import "navbarPart.ftl" as m>

<#macro page title>
<!DOCTYPE HTML>
<html lang="ru">
<head>
    <title>Web Application<#if title??> - ${title}</#if></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous"/>
    <script defer src="/static/jquery-3.6.0.min.js"
            crossorigin="anonymous"></script>
    <script defer src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
            integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
            crossorigin="anonymous"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <script defer src="https://kit.fontawesome.com/32043e9b0d.js"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>
    <link rel="icon" href="/static/favicon.ico" type="image/ico"/>
    <style>
        .turbolinks-progress-bar {
            height: 5px;
            background-color: green;
        }
    </style>
</head>
<body>
<#include "navbarPart.ftl"/>
<div class="container mt-5">
    <#nested>
</div>
<#include "footerPart.ftl"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"></script>
</body>
</html>
</#macro>