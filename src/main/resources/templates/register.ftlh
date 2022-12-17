<#import "/spring.ftl" as spring>
<#import "register.ftlh" as main>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="/">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Rubik:400,700">
    <link rel="stylesheet" href="./css/login.css">
    <title>Register</title>
</head>
<body>
<div class="login-form">
    <form action="/admin/create/user" method="post" id="login-form">
        <#if _csrf??>
            <input type="hidden"
                   name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>
        <h1>Register</h1>
        <div class="content">
            <div class="input-field">
                <input type="text" name="firstName" placeholder="firstname" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <input type="text" name="surname" placeholder="surname" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <input type="text" name="middleName" placeholder="middleName" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <input type="email" name="email" placeholder="Email" autocomplete="nope" required>
            </div>
            <div class="input-field">
                <label for="password" class="form-label">Роли</label>
                <select name="roleId" id="option" class="form-control">
                    <#list roles as role>
                        <option value="${role.id}">${role.name}</option>
                    </#list>
                </select>
            </div>

            <div class="input-field">
                <label for="password" class="form-label">Отделы</label>
                <select name="departmentId" id="option" class="form-control">
                    <#list departments as d>
                        <option value="${d.id}">${d.name}</option>
                    </#list>
                </select>
            </div>
            <div class="input-field">
                <input type="password" name="password" placeholder="Password" autocomplete="new-password" required>
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
