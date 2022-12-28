<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/tasks.css"]>
    <form action="/tasks/save" method="post">
        <div class="container">
            <label for="name" class="form-label">Для</label>
            <input type="datetime-local" class="form-control" id="createdTime" name="createdTime" required
                   minlength="1">
            <div class="input-field">
                <select name="taskId" id="option" class="form-control">
                    <#list tasks as task>
                        <option value="${task.id}">${task.name}</option>
                    </#list>
                </select>
            </div>
        </div>
        <#if _csrf??>
            <input type="hidden"
                   name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>
        <input type="submit">
    </form>
    <button><a class="deal" href="/">Отменить</a></button>

</@g.general>