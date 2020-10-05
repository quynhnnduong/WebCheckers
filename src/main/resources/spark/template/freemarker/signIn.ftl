<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

    <title>Sign-in | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>Web Checkers | ${title}</h1>

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl" />

        <form id="username" action="./signin" method="POST">
            <input name="text_field" required>
            <button type="submit">Sign-In</button>
        </form>

    </div>
</div>
</body>