<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/tasks.css"]>
    <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>

    <div class="button">
        <button class="addTask">Задачи</button>
        <button class="addTask">Отчетность</button>
        <button class="addTask"><a class="deal" href="/tasks/addTask">Добавить задачу</a></button>
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
        </form>
    </div>
</@g.general>