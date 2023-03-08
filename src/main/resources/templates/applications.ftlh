<#import "parts/general.ftlh" as g>

<@g.general scripts=["/crm/js/application.js"] styles=["/crm/css/application.css"]>

    <div class="application_list_class">
        <div class="application_sort_block">
            <label>
                <input type="search" class="form-control" id="search_data_input"
                       placeholder="Поиск по наименованию">
            </label>
            <div class="btn-group" role="group" id="input_group_application"
                 aria-label="Basic radio toggle button group">
                <input type="radio" class="btn-check" value="today" name="btnradio" id="btnradio_1_application"
                       autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio_1_application"
                       onclick="dataForTodayInApplication()">Сегодня</label>

                <input type="radio" class="btn-check" value="yesterday" name="btnradio" id="btnradio_2_application"
                       autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio_2_application"
                       onclick="dataForTodayInApplication()">Вчера</label>

                <input type="radio" class="btn-check" value="week" name="btnradio" id="btnradio_3_application"
                       autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio_3_application"
                       onclick="dataForTodayInApplication()">Неделя</label>

                <input type="radio" class="btn-check" value="month" name="btnradio" id="btnradio_4_application"
                       autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio_4_application"
                       onclick="dataForTodayInApplication()">Месяц</label>

                <input type="radio" class="btn-check" name="btnradio" id="btnradio_5_application"
                       autocomplete="off"/>
                <label class="btn btn-outline-primary" id="btnradio5_in_application_text"
                       for="btnradio_5_application"></label>

                <input type="radio" class="btn-check" value="all_active" name="btnradio" id="btnradio_7_application"
                       autocomplete="off"
                       checked>
                <label class="btn btn-outline-primary" for="btnradio_7_application"
                       onclick="dataForTodayInApplication()">Активные</label>

                <input type="radio" class="btn-check" value="infinity" name="btnradio" id="btnradio_6_application"
                       autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio_6_application"
                       onclick="dataForTodayInApplication()">Все</label>
            </div>
            <div class="alert alert-success alert_success_msg_for_add" id="alert_new_msg" hidden role="alert">
                Добавлено новое заявление!
            </div>
            <div class="alert alert-success alert_success_msg_for_add" id="alert_delete_msg" hidden role="alert">
                Заявление успешно удалено!
            </div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                    data-bs-target="#addNewApplication" onclick="fillNewApplication()">
                Добавить заявку
            </button>
        </div>
        <table class="table table-striped application_table_block">
            <thead>
            <tr class="table-light">
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationId()"
                            style="cursor: pointer" type="button">№ <i class="fa-solid fa-sort"></i>
                    </button>
                </th>
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationCompany()"
                            style="cursor: pointer" type="button">Наименование <i class="fa-solid fa-sort"></i>
                    </button>
                </th>
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationPrice()"
                            style="cursor: pointer" type="button">Бюджет <i class="fa-solid fa-sort"></i></button>
                </th>
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationProduct()"
                            style="cursor: pointer" type="button">Товар/услуга <i class="fa-solid fa-sort"></i>
                    </button>
                </th>
                <th scope="col">Контактное лицо</th>
                <th scope="col">Телефон</th>
                <th scope="col">Почта</th>
                <th scope="col">Адрес</th>
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationStatus()"
                            style="cursor: pointer" type="button">Статус <i class="fa-solid fa-sort"></i></button>
                </th>
                <th scope="col">
                    <button class="btnToSortByProductName" onclick="sortFunctionByApplicationEmployee()"
                            style="cursor: pointer" type="button">Сотрудник <i class="fa-solid fa-sort"></i></button>
                </th>
            </tr>
            </thead>
            <tbody id="tdsBlock" class="table-group-divider">
            </tbody>
        </table>
        <div class="d-flex spinner_in_application_pulls" id="spinner_in_application_pulls">
            <strong class="text-primary">Загрузка данных...</strong>
            <div class="spinner-border ms-auto text-primary" role="status" aria-hidden="true"></div>
        </div>
        <div id="emptyMsg" class="emptyMsg" style="display: none">
            <span>Заявления отсутствуют</span>
        </div>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="alert alert-success alert_success_msg" id="alert_msg" hidden role="alert">
            Изменения сохранены!
        </div>
        <div class="modal-dialog modal-fullscreen modal-dialog-edit">
            <div class="modal-content modal-content-edit">

                <div class="info_block">

                    <div class="modal-header modal-header-edit">
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
                                <input type="text" class="form-control" name="company" id="company" value="">
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
                                <input type="text" class="form-control" name="address" id="address" value="">
                            </div>

                            <hr>

                            <div class="information_field">
                                <label for="employee">Отв-ный</label>
                                <select class="form-select" aria-label="Select employee" id="employee"
                                        name="employeeId" onchange="getUserById(this.value)"
                                        required></select>
                            </div>

                            <div class="information_field">
                                <label for="funnelInApplication">Воронка</label>
                                <select class="form-select" aria-label="Select funnel"
                                        id="funnelInApplication" onchange="uploadFunnel(this.value)"></select>
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
                                <input type="text" class="form-control" id="departmentInApplication" disabled>
                            </div>
                            <div class="information_field">
                                <label for="createdAt">Создано</label>
                                <input type="datetime-local" class="form-control" disabled value=""
                                       name="createdAt"
                                       id="createdAt">
                            </div>

                            <div class="information_field">
                                <textarea class="form-control" name="description" id="applicationTextarea"
                                          placeholder="Примечание к заявлению"></textarea>
                            </div>

                            <input type="hidden" value="" id="hiddenApplicationId">
                            <input type="hidden" value="" name="sourceId" id="sourceId">

                        </form>
<#--                        <button type="button" class="btn btn-danger deleteBtn" value="" id="deleteBtn"-->
<#--                                data-bs-toggle="modal" data-bs-target="#deleteApplicationModal"-->
<#--                                onclick="getValueForRemoveApplication(this.value)">-->
<#--                        </button>-->
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" form="modal_edit_form">
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
                            <div class="d-flex align-items-center" id="spinner_in_application">
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
                                <label for="taskForEmployee"> Для </label>
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

    <div class="modal fade" id="addNewApplication" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                            <input type="tel" class="form-control" name="phone" id="new_phone" value=""
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
                                    name="productId" required>
                            </select>
                        </div>
                        <input type="hidden" value="9" name="sourceId">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                    <button type="submit" class="btn btn-primary" form="add_new_application"
                            id="btnForSaveInApplication" data-bs-dismiss="modal">
                        Сохранить
                    </button>
                </div>
            </div>
        </div>
    </div>
</@g.general>
