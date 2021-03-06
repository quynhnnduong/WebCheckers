<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />


  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <#if !loggedIn>
        ${playersMessage}
    </#if>

    <#if legitOpponent == false>
      <p>That person is already playing with someone else, choose another opponent</p>

    </#if>


    <#if loggedIn>
        <h1>Player Lobby</h1>
        ${playersMessage}
       <#include "player_list.ftl"/>
      <#include "replay_button.ftl"/>
    </#if>


  </div>

</div>
</body>

</html>
