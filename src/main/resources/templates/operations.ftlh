<#import "parts/general.ftlh" as g>

<@g.general scripts=["/crm/js/operation.js"] styles=["/crm/css/operation.css"]>
    <div class="main_block_inner_in_operations">
        <div class="operations_nav" id="operations_nav">
            <div class="alert alert-success alert_success_msg_for_status" id="alert_status" hidden role="alert">
                Статус успешно изменен!
            </div>
            <div class="alert alert-success alert_success_msg_for_status" id="alert_new_msg" hidden role="alert">
                Добавлено новое заявление!
            </div>
            <button type="button" class="btn btn-outline-success" id="selectFreeApplicationBtn" data-bs-toggle="modal" data-bs-target="#selectAllFreeApplication" onclick="uploadApplicationData()">
                Выбрать
            </button>
            <select class="form-select" aria-label="Select status" id="funnels" onchange="changeFunnel(this.value)"
                    required></select>
            <div class="checkboxes_block">
                <div class="pretty p-switch p-slim">
                    <input type="radio" name="switch1" id="switch1" onclick="changeToActiveApplication()" checked/>
                    <div class="state p-success">
                        <label>Активные</label>
                    </div>
                </div>
                <div class="pretty p-switch p-slim">
                    <input type="radio" name="switch1" onclick="changeToNotActiveApplication()"/>
                    <div class="state p-success">
                        <label>Не активные</label>
                    </div>
                </div>
                <div class="pretty p-switch p-slim">
                    <input type="radio" name="switch1" onclick="changeToAllApplication()"/>
                    <div class="state p-success">
                        <label>Все</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="operations" id="operations_block">
            <span class="d-flex align-items-center spinner_in_operations" id="spinner_in_operations">
                <strong class="text-primary">Загрузка данных...</strong>
                <span class="spinner-border ms-auto text-primary" role="status" aria-hidden="true"></span>
            </span>
        </div>
    </div>


    <div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="alert alert-success alert_success_msg" id="alert_msg" hidden role="alert">
            Изменения сохранены!
        </div>
        <div class="modal-dialog modal-fullscreen modal-dialog-edit">
            <div class="modal-content modal-content-edit">

                <div class="info_block">

                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modal_title_from_js"></h1>
                    </div>
                    <div class="modal-body modal_edit_form" id="modal_body_form">
                        <form id="modal_edit_form" class="add_new_application_inner">
                            <input type="hidden" name="id" value="" id="id">
                            <#if _csrf??>
                                <input type="hidden"
                                       name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <div class="information_field">
                                <label for="company">Клиент</label>
                                <input type="text" class="form-control" name="company" id="company"
                                       value="">
                            </div>
                            <div class="information_field">
                                <label for="price">Бюджет</label>
                                <input type="number" class="form-control" step="any" min="0" name="price"
                                       id="price"
                                       value="">
                            </div>
                            <div class="information_field">
                                <label for="name">Контактное лицо</label>
                                <input type="text" class="form-control" name="name" id="name" value="">
                            </div>
                            <div class="information_field">
                                <label for="phone">Раб. тел</label>
                                <input type="text" class="form-control" name="phone" id="phone" value="">
                            </div>
                            <div class="information_field">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" name="email" id="email" value="">
                            </div>
                            <div class="information_field">
                                <label for="address">Адрес</label>
                                <input type="text" class="form-control" name="address" id="address"
                                       value="">
                            </div>
                            <hr>
                            <div class="information_field">
                                <label for="employee">Отв-ный</label>
                                <select class="form-select" aria-label="Select employee"
                                        id="employee"
                                        name="employeeId"
                                        onchange="getUserById(this.value)"
                                        required></select>
                            </div>
                            <div class="information_field">
                                <label for="funnelInApplication">Воронка</label>
                                <select class="form-select" aria-label="Select funnel"
                                        id="funnelInApplication" onchange="uploadFunnelToOperation(this.value)"></select>
                            </div>
                            <div class="information_field">
                                <label for="productId">Товар/услуга</label>
                                <select class="form-select" aria-label="Select product" id="productId"
                                        name="productId" required></select>
                            </div>
                            <div class="information_field">
                                <label for="status">Статус</label>
                                <select class="form-select" aria-label="Select status" id="status"
                                        name="statusId" required></select>
                            </div>
                            <div class="information_field">
                                <label for="departmentInApplication">Департамент</label>
                                <input type="text" class="form-control" id="departmentInApplication"
                                       disabled>
                            </div>
                            <div class="information_field">
                                <label for="createdAt">Создано</label>
                                <input type="datetime-local" class="form-control" disabled value=""
                                       name="createdAt"
                                       id="createdAt">
                            </div>
                            <div class="information_field">
                                <label for="status">Источник</label>
                                <select class="form-select" aria-label="Select source" id="sourceId"
                                        name="sourceId" required></select>
                            </div>
                            <div class="information_field">
                                <textarea class="form-control" name="description" id="applicationTextarea"
                                          placeholder="Примечания к заявлению"></textarea>
                            </div>
                            <input type="hidden" value="" id="hiddenApplicationId">
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" id="submitBtnForSave" form="modal_edit_form"
                                onclick="getMessage()">
                            Сохранить
                        </button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                    </div>
                </div>
                <div class="log_block">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="blockForLogsInApplication">
                        <div id="blockForLogsInApplication">
                            <div class="d-flex align-items-center" id="spinner_in_operation_modal">
                                <strong class="text-primary">Загрузка данных...</strong>
                                <div class="spinner-border ms-auto text-primary" role="status" aria-hidden="true"></div>
                            </div>
                        </div>
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
                                <input type="datetime-local" class="form-control w-25 d-inline-block"
                                       id="deadline_for_task_in_application" name="deadline" required>
                                <select name="typeId" id="taskTypeChoose" class="form-select d-inline-block"
                                        required></select>
                                <label for="taskForEmployee" id="label_for_employee_task"> Для </label>
                                <select class="form-select d-inline-block" name="employeeId" id="taskForEmployee"
                                        required></select>
                            </div>

                            <textarea class="form-control" name="description" id="textareaValue"
                                      placeholder="Примечания к задаче"></textarea>

                            <div class="task_footer_add">
                                <button type="submit" class="btn btn-success">Поставить задачу</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="addNewApplicationInOperationSource" tabindex="-1"
         aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content modal-new-app-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Новое заявление</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body" id="modal_body_form">
                    <form id="add_new_application" class="add_new_application_inner">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>
                        <div class="information_field">
                            <label for="new_company">Клиент</label>
                            <input type="text" class="form-control" name="company" id="new_company" value=""
                                   placeholder="Название компании" required>
                        </div>
                        <div class="information_field">
                            <label for="new_price">Бюджет</label>
                            <input type="number" class="form-control" step="any" min="0" name="price"
                                   id="new_price"
                                   value="0" placeholder="Введите бюджет">
                        </div>
                        <div class="information_field">
                            <label for="new_name">Контактное лицо</label>
                            <input type="text" class="form-control" name="name" id="new_name" value=""
                                   placeholder="Введите имя">
                        </div>
                        <div class="information_field">
                            <label for="new_phone">Раб. тел</label>
                            <input type="text" class="form-control" name="phone" id="new_phone" value=""
                                   placeholder="Введите номер">
                        </div>
                        <div class="information_field">
                            <label for="new_email">Email</label>
                            <input type="email" class="form-control" name="email" id="new_email" value=""
                                   placeholder="Введите почту">
                        </div>
                        <div class="information_field">
                            <label for="new_address">Адрес</label>
                            <input type="text" class="form-control" name="address" id="new_address" value=""
                                   placeholder="Введите адрес">
                        </div>
                        <div class="information_field">
                            <label for="new_product">Товар/услуга</label>
                            <select class="form-select" aria-label="Select product" id="new_product"
                                    name="productId" required></select>
                        </div>

                        <input type="hidden" id="employeeInNewApplication" value="" name="employeeId" required>
                        <input type="hidden" value="9" name="sourceId">
                        <input type="hidden" value="0" name="statusId" id="statusInNewApplication">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                    <button type="submit" class="btn btn-primary" form="add_new_application" data-bs-dismiss="modal">
                        Сохранить
                    </button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="selectAllFreeApplication" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable freeApplicationModalDialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Свободные заявления</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body modal-body-inner-new-application">
                    <div>
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}" id="x-csrf-token-2">
                        </#if>
                    </div>
                    <div class="text-bg-light p-3 columnTitleInFreeApplication">
                        <div class="input-inner-free-id">№</div>
                        <div class="input-inner-free-com">Наименование</div>
                        <div class="input-inner-free-price">Бюджет</div>
                        <div class="input-inner-free-name">Представитель</div>
                        <div class="">Действие</div>
                    </div>
                    <div id="freeApplicationList" class="freeApplicationList"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>
</@g.general>