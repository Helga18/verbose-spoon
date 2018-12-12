<#macro nav_bar>

 <nav role='navigation'>
     <ul>
         <li><a href="/index">Home</a></li>
    <#if user??>
         <li><a href="/profile">My Profile</>
    </#if>
    <#if user??>
             <ul>
                 <li><a href="#"> My Subscriptions</a></li>
             </ul></li>
    </#if>
         <li><a href="/search">Search</a></li>
         <li><a href="playlist.html"> My PlayList </a></li>
         <li><a href="/about">About</a></li>
             <ul>
                 <li><a href="">Our team</a></li>

             </ul>
         </li>
    <#if user??>
             <li><form method="post" action="/logout"><button type="submit">Log out</button></form></li>
    <#else>
         <li><a href="/auth" > Log In </li></a>
         <li><a href="/registration"> Sign up </li></a>
    </#if>
     </ul>

 </nav>
</#macro>