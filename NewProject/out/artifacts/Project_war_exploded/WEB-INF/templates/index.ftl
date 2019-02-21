<#-- @ftlvariable name="comments" type="java.util.List" -->
<#include "base.ftl">
<#macro title>
    Main page
</#macro>
<#macro import>
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>
<#macro main_content>
    <div id="content" style="margin-left: 40%;margin-top: 10%">
                        <form>
                            <p1> WELCOME TO OUR SITE</p1>
                            <br></br>
                            <button style="background: lightgray;width: 250px"><a href="/signUp">Sign Up</a></button>
                            <br></br>
                            <button style="background: lightgray;width: 250px"><a href="/login">Log In</a></button>
                        </form>

    </div>
</#macro>

<@display_page/>