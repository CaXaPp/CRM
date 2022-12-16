<#import "/spring.ftl" as spring>
<#import "myTasks.ftlh" as main>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/css/myTasks.css">
</head>
<body>
<#if _csrf??>
    <input type="hidden"
           name="${_csrf.parameterName}" value="${_csrf.token}">
</#if>
<div class="button">
    <button class="addTask">Задачи</button>
    <button class="addTask">Отчетность</button>
   <button class="addTask"> <a class="deal" href="/tasks/addTask">Добавить задачу</a> </button>
</div>
<div class="container">
<form action="/tasks/task" method="post">
    <table class="table table-dark table-striped-columns">
        <thead>
        <tr>
        <tr>
            <th scope="col">Дата исполнения</th>
            <th scope="col">Ответственный</th>
            <th scope="col">Объект</th>
            <th scope="col">Тип задачи</th>
            <th scope="col">Текст Задачи</th>
            <th scope="col">Результат</th>
            <th scope="col">Котировки</th>
        </tr>
        </tr>
        </thead>
        <tbody>
        <tr>
        <tbody>
            <#list tasks as task>
            <tr>
                <td>${task.createdAt}</td>
                <td>${task.employee.getFIO()}</td>
<#--                <td>${task}</td>-->
                <td>${task.type.name}</td>
                <td>${task.description}</td>
<#--                <td>${task}</td>-->
                <td>${task.name}</td>
            </tr>

        </tbody>
<#--        </select>-->
        </#list>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>