<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/login.css">
    <title>Authorization-page</title>
</head>
<body>
<div class="login-form">
    <form action="/crm/login" method="post" id="login-form">
        <#if _csrf??>
            <input type="hidden"
                   name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>
        <h1>Авторизация</h1>
        <div class="content">
            <div class="input-field">
                <input type="email" name="username" placeholder="Почта" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <input type="password" name="password" placeholder="Пароль" autocomplete="new-password" required>
            </div>
        </div>
        <div class="action">
            <button type="submit">Зайти</button>
        </div>
    </form>
</div>
</body>
</html>