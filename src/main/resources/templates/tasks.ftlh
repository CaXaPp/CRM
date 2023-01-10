<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/task.js"] styles=["/css/tasks.css"]>
    <div class="button">
        <button class="addTask">Задачи</button>
        <button class="addTask">Отчетность</button>
        <button class="addTask" id="addTaskButton" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Добавить задачу</button>
    </div>

    <div class="container position-relative">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Дата исполнения</th>
                    <th scope="col">Ответственный</th>
                    <th scope="col">Объект</th>
                    <th scope="col">Тип задачи</th>
                    <th scope="col">Описание</th>
                    <th scope="col">Результат</th>
                </tr>
            </thead>
            <tbody id="tbody">
                <#list tasks as task>
                    <tr>
                        <td>${(task.deadline)!''}</td>
                        <td>${(task.employee.getFIO())!''}</td>
                        <td>${(task.application.name)!''}</td>
                        <td>${(task.type.name)!''}</td>
                        <td>${(task.description)!''}</td>
                        <td>${(task.result)!'-'}</td>
                    </tr>
                </#list>
            </tbody>
        </table>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Добавление задачи</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form class="modal-body" id="edit-task-form">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>
                        <select name="operationId" id="operation" required>
                            <#list operations as operation>
                                <option value="${operation.id}">${operation.name}</option>
                            </#list>
                        </select>
                        <div class="form-body">
                            <input type="datetime-local" name="deadline" placeholder="Дата и время" required>
                            <label for="employee">Для</label>
                            <select name="employeeId" id="employee" required>
                                <#list employees as employee>
                                    <option value="${employee.id}">${employee.firstName}</option>
                                </#list>
                            </select>
                            <select name="typeId" required>
                                <#list types as type>
                                    <option value="${type.id}">${type.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-additional">
                            <label for="description">Примечания
                                <textarea name="description" id="description" placeholder="Примечания к задаче"></textarea>
                            </label>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-dark" data-bs-dismiss="modal">Поставить</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@g.general>