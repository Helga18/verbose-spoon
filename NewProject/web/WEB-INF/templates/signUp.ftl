<#include "base.ftl">
<#macro title>
    Sign up
</#macro>
<#macro import>
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>

<#macro main_content>
<div id="header"><h1>  WELCOME TO "MY MUSIC"</h1> </div>
          <div class="signUp-page">
         <div id="signUp">
             <form class="form" method="post" enctype="multipart/form-data">
                 <input type="text" placeholder="name" name="name" required>
                 <input type="text" placeholder="login" name="login" required>
                 <input type="password" placeholder="password" name="password" required>
                 <input type="number" placeholder="age" name="age" required>
                 <input type="number" placeholder="year" name="year" required>
                 <label class="control-label col-sm-2" for="pwd">Photo: </label>
                 <input type="file" name="file" id="file" class="col-sm-3 photos">
                 <button type="submit">create</button>
                 <p class="message">Registered? <a href="/login">log in</a></p>
             </form>
         </div>
</div>

</#macro>

<@display_page/>