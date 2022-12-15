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
<link rel="stylesheet" href="./css/myTasks.css">

<body>
    <button class="addOperation">Добавить операцию</button>
<form action="/tasks/create" method="post">
    <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>
    <table class="table">
        <tr>
            <th scope="col">Дата исполнения</th>
            <th scope="col">Ответс</th>
            <th scope="col">Статус№3</th>
            <th scope="col">Статус№4</th>
            <th scope="col">Статус№5</th>
            <th scope="col">Статус№6</th>
            <th scope="col">Статус№7</th>
        </tr>
        <select name="task" id="option" class="form-control">
            <#list tasks as task>
            <option value="${task}">${task}</option>
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>task</td>
                <td>task</td>
                <td>task</td>
                <td>task</td>
                <td>task</td>
                <td>task</td>
                <td>task</td>
            </tr>

            </tbody>
        </select>
        </#list>
    </table>
</form>
</body>
</html>