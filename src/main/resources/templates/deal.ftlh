<#import "/spring.ftl" as spring>
<#import "deal.ftlh" as main>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>deal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/css/deal.css">
</head>
<body>
<#if _csrf??>
    <input type="hidden"
           name="${_csrf.parameterName}" value="${_csrf.token}">
</#if>
<form action="/tasks/addTask" method="post">
    <div class="container">
    <label for="name" class="form-label">Для</label>
        <#if user??>
            ${user.firstName}
        </#if>
        <input type="datetime-local" class="form-control" id="createdTime" name="createdTime" required minlength="1">
        <div class="input-field">
        <select name="taskId" id="option" class="form-control">
            <#list tasks as task>
                <option value="${task.id}">${task.name}</option>
            </#list>
        </select>
        </div>
    </div>
    <input type="submit">
    <button><a class="deal" href="/tasks/save">Поставить</a></button>
    <button ><a class="deal" href="/">Отменить</a></button>

</body>
</html>