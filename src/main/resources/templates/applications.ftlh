<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/application.js"] styles=["/css/main.css"]>
<div class="application_list_class">
    <div>
        <div>
            <a class="addRequest" href="/application/save">Добавить заявку</a>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Наименование</th>
                <th scope="col">Бюджет</th>
                <th scope="col">Товар/услуга</th>
                <th scope="col">Контактное лицо</th>
                <th scope="col">Телефон</th>
                <th scope="col">Почта</th>
                <th scope="col">Адрес</th>
                <th scope="col">Статус</th>
                <th scope="col">Ответственный сотрудник</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="tdsBlock">
            </tbody>
        </table>
    </div>
    <br>
    <div id="sort_block_footer">
        Блок для сортировки
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="alert alert-success alert_success_msg" id="alert_msg" hidden role="alert">
        Изменения сохранены!
    </div>
    <div class="modal-dialog">
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
                <div class="blockForLogsInApplication">
                    <div id="blockForLogsInApplication"></div>
                </div>
                <div class="log_block_footer" id="log_block_footer">
                    <form class="log_block_footer_inner" id="updateAddedTask">
                        <div>
                            <input id="operationId" name="operationId" value="" type="hidden">
                            <#if _csrf??>
                                <input type="hidden"
                                       name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <a>Задача на </a>
                            <input type="datetime-local" class="form-control w-25 d-inline-block" id="deadline_for_task_in_application" name="deadline" required>
                            <select name="typeId" id="taskTypeChoose" class="form-select d-inline-block" required></select>
                            <label for="taskForEmployee"> Для </label>
                            <select class="form-select d-inline-block" name="employeeId" id="taskForEmployee" required></select>
                        </div>

                        <textarea class="form-control" name="description" id="textareaValue" placeholder="Примечания к задаче"></textarea>

                        <div>
                            <button type="submit" class="btn btn-primary">Поставить</button>
                            <button type="button" class="btn btn-secondary">Отменить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    </@g.general>
