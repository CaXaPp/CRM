<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/operation.js"] styles=["/css/main.css"]>
    <div class="wrap mt-5">
        <div class="button-group mb-5">
            <div class="dropdown">
                <button class="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Добавить операцию
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="/operations/add">Выбрать из всех заявок</a></li>
                    <li><button class="addTask dropdown-item" id="addTaskButton" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Добавить новую операцию</button></li>
                </ul>
            </div>
            <#if user.role != 'Сотрудник'>
                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Воронки
                    </button>
                    <ul class="dropdown-menu">
                        <#list funnels as funnel>
                            <li><a class="dropdown-item" href="/operations?funnel=${funnel.id}">${funnel.name}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
        <div class="operations">
            <#list operation_statuses as status>
                <div class="card">
                    <div class="card-header text-center">
                        ${status.name}
                    </div>
                    <ul class="list-group list-group-flush">
                        <#list operations as operation>
                            <#if status.id == operation.status.id>
                                <li class="list-group-item text-center" style="cursor: pointer" onclick="getOperationById(${operation.id})">Операция №${operation.id}</li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </#list>
        </div>
    </div>
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="info_block">

                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modal_title_from_js"></h1>
                    </div>
                    <div class="modal-body" id="modal_body_form">
                        <table>

                            <form id="modal_edit_form" class="modal_edit_form">
                                <input type="hidden" name="id" value="" id="id">
                                <#if _csrf??>
                                    <input type="hidden"
                                           name="${_csrf.parameterName}" value="${_csrf.token}">
                                </#if>
                                <tr>
                                    <td><label for="modal_company">Клиент</label></td>
                                    <td><input type="text" class="form-control" name="company" id="company" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="modal_deal_sum">Бюджет</label></td>
                                    <td><input type="number" class="form-control" step="any" min="0" name="price" id="price"
                                               value=""></td>
                                </tr>
                                <tr>
                                    <td>Товар/услуга</td>
                                    <td><select class="form-select" aria-label="Select product" id="productId"
                                                name="productId" required> </select></td>
                                </tr>
                                <tr>
                                    <td><label for="modal_name">Контактное лицо</label></td>
                                    <td><input type="text" class="form-control" name="name" id="name" value=""></td>
                                </tr>
                                <tr>
                                    <td>
                                        <hr>
                                        Компания:
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="modal_phone">Раб. тел</label></td>
                                    <td><input type="text" class="form-control" name="phone" id="phone" value=""></td>
                                </tr>
                                <tr>
                                    <td><label for="modal_email">Email</label></td>
                                    <td><input type="email" class="form-control" name="email" id="email" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="modal_address">Адрес</label></td>
                                    <td><input type="text" class="form-control" name="address" id="address" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td>Отв-ный</td>
                                    <td><select class="form-select" aria-label="Select employee" id="employee"
                                                name="employeeId" required></select></td>
                                </tr>
                                <tr>
                                    <td>Статус</td>
                                    <td><select class="form-select" aria-label="Select status" id="status"
                                                name="statusId" required></select></td>
                                </tr>
                                <tr>
                                    <td><label for="createdAt">Создано</label></td>
                                    <td>
                                        <input type="datetime-local" class="form-control" disabled value="" name="createdAt"
                                               id="createdAt">
                                    </td>
                                </tr>

                                <input type="hidden" value="" id="hiddenApplicationId">
                                <input type="hidden" value="" name="sourceId" id="sourceId">

                            </form>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                        <button type="submit" class="btn btn-primary" form="modal_edit_form" onclick="getMessage()">
                            Сохранить
                        </button>
                    </div>
                </div>
                <div class="log_block">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div>Блок для задач и логов</div>
                </div>
            </div>
        </div>
    </div>
</@g.general>