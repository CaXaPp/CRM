<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="/">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Rubik:400,700">
    <link rel="stylesheet" href="./css/login.css">
    <title>Login</title>
</head>
<body>
<div class="login-form">
    <form action="/login" method="post" id="login-form">
        <#if _csrf??>
            <input type="hidden"
                   name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>
        <h1>Login</h1>
        <div class="content">
            <div class="input-field">
                <input type="email" name="username" placeholder="email" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <input type="password" name="password" placeholder="Password" autocomplete="new-password" required>
            </div>
        </div>
        <div class="action">
            <button type="submit">Login</button>
        </div>
    </form>
</div>
</body>
</html>
