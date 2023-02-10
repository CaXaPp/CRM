<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/task.js"] styles=["/css/tasks.css"]>
    <div class="task_header_block">
        <button type="button" class="btn btn-success" id="addTaskButton" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Добавить задачу</button>
    </div>

    <div class="task_main_block">
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
                    <tr data-id="${task.id}" class="task" data-bs-toggle="modal" data-bs-target="#editTask">
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

        <div class="modal fade" id="staticBackdrop" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
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
                        <div class="form-body">
                            <div>
                                <select class="form-select" name="operationId" id="operation" required>
                                    <#list operations as operation>
                                        <option value="${operation.id}">${operation.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="task_body_information">
                                <input class="form-control" type="datetime-local" name="deadline" placeholder="Дата и время" required>
                                <label for="employee">Для</label>
                                <select class="form-select" name="employeeId" id="employee" required>
                                    <#list employees as employee>
                                        <option value="${employee.id}">${employee.firstName}</option>
                                    </#list>
                                </select>
                                <select class="form-select" name="typeId" required>
                                    <#list types as type>
                                        <option value="${type.id}">${type.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div>
                                <textarea class="form-control description" name="description" placeholder="Примечания к задаче"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                            <button type="submit" class="btn btn-success" data-bs-dismiss="modal">Поставить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="editTask" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Редактирование задачи</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form class="modal-body" id="task-edit-form">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" name="_csrf_header" value="${_csrf.headerName}">
                        </#if>
                        <div class="form-body">
                            <div>
                                <select class="form-select" name="operationId" id="operation" required>
                                    <#list operations as operation>
                                        <option value="${operation.id}">${operation.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="task_body_information">
                                <input class="form-control" type="datetime-local" name="deadline" id="deadline" placeholder="Дата и время" required>
                                <label for="employee">Для</label>
                                <select class="form-select" name="employeeId" id="employee" required>
                                    <#list employees as employee>
                                        <option value="${employee.id}">${employee.firstName}</option>
                                    </#list>
                                </select>
                                <select class="form-select" name="typeId" id="typeId" required>
                                    <#list types as type>
                                        <#if type.id == 2><option selected value="${type.id}">${type.name}</option></#if>
                                        <option value="${type.id}">${type.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="form-additional">
                                <textarea class="form-control description" name="description" placeholder="Примечания к задаче"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                            <button type="submit" class="btn btn-success" data-bs-dismiss="modal">Поставить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@g.general>