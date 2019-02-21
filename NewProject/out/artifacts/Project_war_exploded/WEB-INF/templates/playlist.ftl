<#-- @ftlvariable name="comments" type="java.util.List" -->
<#include "base.ftl">
<#macro title>
    Main page
</#macro>
<#macro import>
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>
<#macro main_content>
    <div id ="content">
                         <#if user??>
                             <form>
                                 <button class="submit"><a href="/profile">Profile</a></button>
                        <#if admin??>
                            <button class="submit"><a href="/addCar">Add Track</a></button>
                        </#if>
                             </form>
                         <#else>
                         </#if>
    </div>
       <div class="info">

           <h5 style="color:#fff">${track1.name} ${track1.type}</h5>
       </div>



        <textarea title="new comment" id="comment" class="text" cols="60"
                  rows="2"></textarea>
        <button class="send" onclick='newComment();'> Write </button>

    </div>


</#macro>

<@display_page/>