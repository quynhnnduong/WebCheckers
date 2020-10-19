<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

    <!-- Ask the user if they truly want to sign out. -->
    <#include "message.ftl" />

    <!-- Confirmation and cancel buttons. -->
    <div class="inline-buttons">
        <form method="get" action="/signin">
            <button>Yes, please!</button>
        </form>

        <form method="get" action="/">
            <button>No, thank you!</button>
        </form>
    </div>

</div>
</body>

</html>