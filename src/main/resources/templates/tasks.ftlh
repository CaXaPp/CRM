<#import "parts/general.ftlh" as g>

<@g.general scripts=["/crm/js/task.js"] styles=["/crm/css/tasks.css"]>
    <div class="task_header_block">
        <button type="button" class="btn btn-success" id="addTaskButton" data-bs-toggle="modal"
                data-bs-target="#staticBackdrop">Добавить задачу
        </button>
    </div>

    <div class="task_main_block">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">№ операции</th>
                <th scope="col">Ответственный</th>
                <th scope="col">Компания</th>
                <th scope="col">Представитель</th>
                <th scope="col">Задача</th>
                <th scope="col">Описание</th>
                <th scope="col">Результат</th>
                <th scope="col">Дата исполнения</th>
            </tr>
            </thead>
            <tbody id="tbody">
            <#list tasks as task>
                <tr data-id="${task.id}" id="task${task.id}" class="task" data-bs-toggle="modal"
                    data-bs-target="#editTask">
                    <td>${(task.application.id)!''}</td>
                    <td>${(task.employee.getFIO())!''}</td>
                    <td>${(task.application.company)!''}</td>
                    <td>${(task.application.name)!''}</td>
                    <td>${(task.type.name)!''}</td>
                    <td>${(task.description)!''}</td>
                    <td>${(task.result)!'-'}</td>
                    <td>${(task.deadline)!''}</td>
                </tr>
            </#list>
            </tbody>
            <#if _csrf??>
                <input type="hidden" id="x-csrf-token-task"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
        </table>

        <div class="modal fade" id="staticBackdrop" tabindex="-1" aria-labelledby="staticBackdropLabel"
             aria-hidden="true">
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
                                <input class="form-control" type="datetime-local" name="deadline"
                                       placeholder="Дата и время" required>
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
                                <textarea class="form-control description" name="description"
                                          placeholder="Примечание к задаче"></textarea>
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
                                        <option value="${operation.id}">
                                            <#if operation.name?has_content>
                                                ${operation.name}
                                                <#else>
                                                ${operation.company}
                                            </#if>
                                        </option>
                                    </#list>
                                </select>
                            </div>
                            <div class="task_body_information">
                                <input class="form-control" type="datetime-local" name="deadline" id="deadline"
                                       placeholder="Дата и время" required>
                                <label for="employee">Для</label>
                                <select class="form-select" name="employeeId" id="employee" required>
                                    <#list employees as employee>
                                        <option value="${employee.id}">${employee.getFIO()}</option>
                                    </#list>
                                </select>
                                <select class="form-select" name="typeId" id="typeId" required>
                                    <#list types as type>
                                        <#if type.id == 2>
                                            <option selected value="${type.id}">${type.name}</option></#if>
                                        <option value="${type.id}">${type.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="form-additional">
                                <label for="description">Примечание:
                                    <textarea class="form-control" id="description" name="description"
                                              placeholder="Введите текст"></textarea>
                                </label>
                            </div>
                            <div class="form-additional">
                                <label for="result">Результат:
                                    <textarea class="form-control" name="result" id="result"
                                              placeholder="Введите результат выполнения задачи"></textarea>
                                </label>
                            </div>
                        </div>
                    </form>

                    <div class="modal-footer modal_footer_btns">
                        <div>
                            <button type="button" class="btn btn-danger" id="deleteTask" value=""
                                    data-bs-dismiss="modal" onclick="deleteTask(this.value)" data-toggle="tooltip" data-placement="right" title="Задача будет удалена без подтверждения">Удалить <i
                                        class="fa-solid fa-trash"></i></button>
                        </div>
                        <div>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                            <button type="submit" class="btn btn-success" data-bs-dismiss="modal" form="task-edit-form">
                                Поставить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@g.general>