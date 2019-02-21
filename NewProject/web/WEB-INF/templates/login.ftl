<#include "base.ftl">
<#macro title>
    Login
</#macro>
<#macro import>
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>

<#macro main_content>
<div id="header"><h1>  WELCOME TO "MY MUSIC"</h1> </div>
          <div class="signUp-page">
            <div id="signUp">
                <form class="form" method="post">
                    <input type="text" placeholder="login" name="login" required>
                    <input type="password" placeholder="password" name="password" required>
                    <input type="checkbox" name="remembered">Remember me<br>
                    <button type="submit">Log In</button>
                    <p class="message">Not Registered? <a href="/signUp">Create Account</a></p>
                </form>
            </div>
          </div>
</#macro>

<@display_page/>



