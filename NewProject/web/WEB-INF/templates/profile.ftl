<#include "base.ftl">
<#macro title>
    Profile
</#macro>
<#macro import>
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>
<#macro main_content>
               <div id="content">
                   <h3>Personal information:</h3>
    <#if user.photo_path??>
        <img src="${user.photo_path}" alt="Chania" width="300" class="frame">
    </#if>
                   <p style="color: white">Name: ${user.name}</p>
                   <p style="color: white">Login: ${user.login}</p>
    <#if user.age??>
        <p style="color: white">Age: ${user.age}</p>
    </#if>
                   <p style="color: white">Year: ${user.year}</p>
               </div>

               </div>

</#macro>

<@display_page/>