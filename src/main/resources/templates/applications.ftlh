<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/application.js"] styles=["/css/main.css"]>

    <div class="application_list_class">
        <div>
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

                    <input type="radio" class="btn-check" value="infinity" name="btnradio" id="btnradio_6_application"
                           autocomplete="off"
                           checked>
                    <label class="btn btn-outline-primary" for="btnradio_6_application"
                           onclick="dataForTodayInApplication()">Все</label>
                </div>
                <a href="/application/save">Добавить заявку</a>
<#--                <button type="button" class="btn btn-primary" data-bs-toggle="modal"-->
<#--                        data-bs-target="#addNewApplication">-->
<#--                    Добавить заявку-->
<#--                </button>-->
            </div>
            <table class="table table-striped application_table_block">
                <thead>
                <tr>
                    <th scope="col">#</th>
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
                                style="cursor: pointer" type="button">Ответственный сотрудник <i
                                    class="fa-solid fa-sort"></i></button>
                    </th>
                    <th></th>
                </tr>
                </thead>
                <tbody id="tdsBlock">
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="alert alert-success alert_success_msg" id="alert_msg" hidden role="alert">
            Изменения сохранены!
        </div>
        <div class="modal-dialog modal-fullscreen">
            <div class="modal-content">

                <div class="info_block">

                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modal_title_from_js"></h1>
                    </div>
                    <div class="modal-body modal_edit_form" id="modal_body_form">
                        <table>
                            <form id="modal_edit_form">
                                <input type="hidden" name="id" value="" id="id">
                                <#if _csrf??>
                                    <input type="hidden"
                                           name="${_csrf.parameterName}" value="${_csrf.token}">
                                </#if>
                                <tr>
                                    <td>
                                        Компания:
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="company">Клиент</label></td>
                                    <td><input type="text" class="form-control" name="company" id="company" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="price">Бюджет</label></td>
                                    <td><input type="number" class="form-control" step="any" min="0" name="price"
                                               id="price"
                                               value=""></td>
                                </tr>
                                <tr>
                                    <td><label for="name">Контактное лицо</label></td>
                                    <td><input type="text" class="form-control" name="name" id="name" value=""></td>
                                </tr>
                                <tr>
                                    <td><label for="phone">Раб. тел</label></td>
                                    <td><input type="text" class="form-control" name="phone" id="phone" value=""></td>
                                </tr>
                                <tr>
                                    <td><label for="email">Email</label></td>
                                    <td><input type="email" class="form-control" name="email" id="email" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="address">Адрес</label></td>
                                    <td><input type="text" class="form-control" name="address" id="address" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <hr>
                                        Дополнительная информация:
                                    </td>
                                </tr>
                                <tr>
                                    <td>Отв-ный</td>
                                    <td><select class="form-select" aria-label="Select employee" id="employee"
                                                name="employeeId" onchange="updateContentForApplication()"
                                                required></select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Воронка</td>
                                    <td><select class="form-select" aria-label="Select funnel"
                                                id="funnelInApplication"></select></td>
                                </tr>
                                <tr>
                                    <td>Товар/услуга</td>
                                    <td><select class="form-select" aria-label="Select product" id="productId"
                                                name="productId" required></select></td>
                                </tr>

                                <tr>
                                    <td>Статус</td>
                                    <td><select class="form-select" aria-label="Select status" id="status"
                                                name="statusId" required></select></td>
                                </tr>
                                <tr>
                                    <td><label for="departmentInApplication">Департамент</label></td>
                                    <td><input type="text" class="form-control" id="departmentInApplication" disabled>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="createdAt">Создано</label></td>
                                    <td>
                                        <input type="datetime-local" class="form-control" disabled value=""
                                               name="createdAt"
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
                            <div>
                                <button type="submit" class="btn btn-primary">Поставить</button>
                                <button type="button" class="btn btn-secondary">Отменить</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<#--    <div class="modal fade" id="addNewApplication" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">-->
<#--        <div class="modal-dialog modal-dialog-centered">-->
<#--            <div class="modal-content">-->
<#--                <div class="modal-header">-->
<#--                    <h5 class="modal-title" id="exampleModalLabel">Новое заявление</h5>-->
<#--                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<#--                </div>-->
<#--                <div class="modal-body">-->
<#--                    <div>-->
<#--                        <table>-->

<#--                            <form id="modal_edit_form">-->
<#--                                <input type="hidden" name="id" value="" id="id">-->
<#--                                <#if _csrf??>-->
<#--                                    <input type="hidden"-->
<#--                                           name="${_csrf.parameterName}" value="${_csrf.token}">-->
<#--                                </#if>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_company">Название компании</label></td>-->
<#--                                    <td><input type="text" class="form-control" name="company" id="add_company"-->
<#--                                               value="">-->
<#--                                    </td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_price">Бюджет</label></td>-->
<#--                                    <td><input type="number" class="form-control" step="any" min="0" name="price"-->
<#--                                               id="add_price"-->
<#--                                               value=""></td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_name">Контактное лицо</label></td>-->
<#--                                    <td><input type="text" class="form-control" name="name" id="add_name" value=""></td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_phone">Раб. тел</label></td>-->
<#--                                    <td><input type="text" class="form-control" name="phone" id="add_phone" value="">-->
<#--                                    </td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_email">Email</label></td>-->
<#--                                    <td><input type="email" class="form-control" name="email" id="add_email" value="">-->
<#--                                    </td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td><label for="add_address">Адрес</label></td>-->
<#--                                    <td><input type="text" class="form-control" name="address" id="add_address"-->
<#--                                               value="">-->
<#--                                    </td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td>Отв-ный</td>-->
<#--                                    <td><select class="form-select" aria-label="Select employee" id="add_employee"-->
<#--                                                name="employeeId" onchange="updateContentForApplication()"-->
<#--                                                required></select>-->
<#--                                    </td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td>Воронка</td>-->
<#--                                    <td><select class="form-select" aria-label="Select funnel"-->
<#--                                                id="add_funnelInApplication"></select></td>-->
<#--                                </tr>-->
<#--                                <tr>-->
<#--                                    <td>Товар/услуга</td>-->
<#--                                    <td><select class="form-select" aria-label="Select product" id="add_product"-->
<#--                                                name="productId" required></select></td>-->
<#--                                </tr>-->

<#--                                <tr>-->
<#--                                    <td>Статус</td>-->
<#--                                    <td><select class="form-select" aria-label="Select status" id="add_status"-->
<#--                                                name="statusId" required></select></td>-->
<#--                                </tr>-->
<#--                                <input type="hidden" value="" name="sourceId" id="add_source">-->
<#--                            </form>-->
<#--                        </table>-->
<#--                        <div class="modal-footer">-->
<#--                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>-->
<#--                            <button type="button" class="btn btn-primary">Сохранить</button>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="log_block">-->
<#--                        <div class="modal-header">-->
<#--                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<#--                        </div>-->
<#--                        <div class="blockForLogsInApplication">-->
<#--                            <div id="blockForLogsInApplication"></div>-->
<#--                        </div>-->
<#--                        <div class="log_block_footer" id="log_block_footer">-->
<#--                            <form class="log_block_footer_inner" id="updateAddedTask">-->
<#--                                <div>-->
<#--                                    <input id="operationId" name="operationId" value="" type="hidden">-->
<#--                                    <#if _csrf??>-->
<#--                                        <input type="hidden"-->
<#--                                               name="${_csrf.parameterName}" value="${_csrf.token}">-->
<#--                                    </#if>-->
<#--                                    <a>Задача на </a>-->
<#--                                    <input type="datetime-local" class="form-control w-25 d-inline-block"-->
<#--                                           id="deadline_for_task_in_application" name="deadline" required>-->
<#--                                    <select name="typeId" id="taskTypeChoose" class="form-select d-inline-block"-->
<#--                                            required></select>-->
<#--                                    <label for="taskForEmployee"> Для </label>-->
<#--                                    <select class="form-select d-inline-block" name="employeeId" id="taskForEmployee"-->
<#--                                            required></select>-->
<#--                                </div>-->

<#--                                <textarea class="form-control" name="description" id="textareaValue"-->
<#--                                          placeholder="Примечания к задаче"></textarea>-->
<#--                                <div>-->
<#--                                    <button type="submit" class="btn btn-primary">Поставить</button>-->
<#--                                    <button type="button" class="btn btn-secondary">Отменить</button>-->
<#--                                </div>-->
<#--                            </form>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->

</@g.general>
