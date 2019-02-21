<#include "navbar.ftl">
<#macro page_head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</#macro>
<#macro import>
  <link rel="stylesheet" type="text/css" href="/static/css/style.css">
</#macro>
<#macro title></#macro>


<#macro main_content></#macro>

<#macro display_page>
<html>

<head>
    <title><@title/></title>
    <@page_head/>
    <@import/>
</head>

<body>
    <@nav_bar/>
    <@main_content/>
</body>

</html>
</#macro>