<#include "base.ftl">
<#macro title>
    Main page
</#macro>
<#macro main_content>
    <div id ="content">
        <p> MY RECOMENDATIONS </p>
        <br> </br>
        <img src="music.png"></img>

        <audio controls>
            <source src="audio/music.ogg" type="audio/ogg; codecs=vorbis"  controls="controls">
            <source src="audio/music.mp3" type="audio/mpeg">

            <a href="audio/music.mp3">Скачайте музыку</a>.
        </audio>
        <button> <img  src="icon.png" class="icon" ></img></button> <p> 1 like </p>
        <p>User 100 </p>
        <br></br>
        <p> User 10 : Good song! </p>

        <p> <input class="inputnew" type="text" placeholder="comment"/>  <button class="send"> SEND </button> </p>


    </div>

</#macro>

<@display_page/>