<#import "/spring.ftl" as spring>
<#import "myTasks.ftlh" as main>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks</title>
</head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
      crossorigin="anonymous">
<body>
<form action="/tasks/create" method="post">
    <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>
    <div class="mb-3">
        <label for="name" class="form-label"></label>
        <input type="text" class="form-control" id="name" name="name" required
               minlength="6">
    </div>

    <div class="mb-3">
        <label for="login" class="form-label"></label>
        <input type="datetime-local" class="form-control" id="createdTime" name="createdTime" required
               minlength="6">
    </div>
    <input type="submit">
</form>
</body>
</html>