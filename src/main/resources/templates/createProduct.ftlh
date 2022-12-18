<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="/admin">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Rubik:400,700">
    <link rel="stylesheet" href="./css/login.css">
    <title>Create Product</title>
</head>
<body>
<div class="login-form">
    <form action="/admin/create/product" method="post" id="product-form">
        <#if _csrf??>
            <input type="hidden"
                   name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>
        <h1>Create Product</h1>
        <div class="content">
            <div class="input-field">
                <input type="text" name="name" placeholder="name" autocomplete="nope" required>
            </div>
        </div>
        <div class="action">
            <button><a href="/admin" style="text-decoration: none; color: black">Back</a></button>
            <button type="submit">Register</button>
        </div>
    </form>
</div>
</body>
</html>
