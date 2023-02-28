<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/main.js"] styles=["/css/main.css"]>

    <div class="dashboard_block">

        <div class="dashboard_block_inner">
            <div class="dashboard_info">
                <div class="dashboard_info_sort_block" id="dashboard_info_sort_block">
                    <div class="btn-group" role="group" id="input_group_1" aria-label="Basic radio toggle button group" onchange="dataForToday()">
                        <input type="radio" class="btn-check" value="today" name="btnradio" id="btnradio1"
                               autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio1"
                               >Сегодня</label>

                        <input type="radio" class="btn-check" value="yesterday" name="btnradio" id="btnradio2"
                               autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2"
                               >Вчера</label>

                        <input type="radio" class="btn-check" value="week" name="btnradio" id="btnradio3"
                               autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3"
                               >Неделя</label>

                        <input type="radio" class="btn-check" value="month" name="btnradio" id="btnradio4"
                               autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio4"
                               >Месяц</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio5" autocomplete="off"/>
                        <label class="btn btn-outline-primary" id="btnradio5_text" for="btnradio5"></label>
                    </div>

                    <div class="btn-group btn_group_right" role="group" id="input_group_2"
                         aria-label="Basic radio toggle button group">
                    </div>
                </div>

                <div>
                    <div class="dashboard_statistic">

                        <div class="dashboard_statistic_left">
                            <div class="text-center py-3 mb-3">
                                <h5 class="card-title text-primary mb-1" id="new_statements_count">+ 0</h5>
                                <p class="text-primary mb-0">Новые</p>
                            </div>
                        </div>

                        <div class="dashboard_statistic_center">

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-primary title_stat_block">
                                    <p class="text-primary mb-0">Новое</p>
                                    <p class="text-primary mb-0" id="new_statements_total">Сделки: 0</p>
                                </div>
                                <div class="border-primary border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="new_statements_total_count">0</h5>
                                </div>
                                <p class="text-primary mb-0" id="new_statements_total_extra_info">Сумма: 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-warning title_stat_block">
                                    <p class="text-primary mb-0">Переговоры</p>
                                    <p class="text-primary mb-0" id="negotiation_total">Сделки: 0</p>
                                </div>
                                <div class="border-warning border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="negotiation_count">0</h5>
                                </div>
                                <p class="text-primary mb-0" id="negotiation_extra_info">Сумма: 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-success title_stat_block">
                                    <p class="text-primary mb-0">Принятие решения</p>
                                    <p class="text-primary mb-0" id="makingDecisions_total">Сделки: 0</p>
                                </div>
                                <div class="border-success border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="makingDecisions_total_count">0</h5>
                                </div>
                                <p class="text-primary mb-0" id="makingDecisions_total_extra_info">Сумма: 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-danger title_stat_block">
                                    <p class="text-primary mb-0">На обслуживании</p>
                                    <p class="text-primary mb-0" id="underMaintenance_total">Сделки: 0</p>
                                </div>
                                <div class="border-danger border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="underMaintenance_total_count">0</h5>
                                </div>
                                <p class="text-primary mb-0" id="underMaintenance_total_extra_info">Сумма: 0</p>
                            </div>
                        </div>

                        <div class="dashboard_statistic_right">
                            <div class="text-center py-3 mb-3">
                                <h5 class="card-title text-primary mb-1" id="makingDecisions_total_count_extra_info">
                                    0</h5>
                                <p class="text-primary mb-0">Успешно</p>
                            </div>
                            <p class="text-primary mb-0" id="makingDecisions_total_info">Сумма: 0</p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="section_info">
            <div class="row section_info_inner_row">
                <div class="col-3">
                    <div class="list-group" id="list-tab" role="tablist">
                        <a class="list-group-item list-group-item-action active" id="list_active_list"
                           data-bs-toggle="list" href="#list_active" onclick="getActiveDealForToday()" role="tab"
                           aria-controls="home">Активные сделки на сегодня</a>
                        <a class="list-group-item list-group-item-action" id="list_done_list" data-bs-toggle="list"
                           href="#list_active" onclick="getCompletedDealOnToday()" role="tab"
                           aria-controls="profile">Выполненные сделки на сегодня</a>
                        <a class="list-group-item list-group-item-action" id="list_overdue_list"
                           data-bs-toggle="list" href="#list_overdue" onclick="getOverdueTasks()" role="tab"
                           aria-controls="messages">Просроченные задачи</a>
                        <a class="list-group-item list-group-item-action" id="list_sources_list"
                           data-bs-toggle="list" href="#list_sources" onclick="getSourceDeal()" role="tab"
                           aria-controls="settings">Источники сделок</a>
                        <a class="list-group-item list-group-item-action" id="list_employee_list"
                           data-bs-toggle="list" href="#list_active" onclick="getAllDealByEmployee()" role="tab"
                           aria-controls="settings">Сделки по сотрудникам</a>
                        <a class="list-group-item list-group-item-action" id="list_tasks_list"
                           data-bs-toggle="list" href="#list_tasks" onclick="getAllActiveTask()" role="tab"
                           aria-controls="settings">Задачи к выполнению</a>
                        <a class="list-group-item list-group-item-action" id="list_failures_list"
                           data-bs-toggle="list" href="#list_failures" onclick="getAllFailuresApplication()" role="tab"
                           aria-controls="settings">Отказы в обслуживании</a>
                    </div>
                </div>
                <div class="col-9 information_block_inner_footer">
                    <div class="d-flex justify-content-center" id="spinner_in_main">
                        <div class="spinner-border text-primary" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                    <div class="tab-content" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="list_active" role="tabpanel"
                             aria-labelledby="list_active_list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Сотрудник</th>
                                    <th scope="col">Наименование клиента</th>
                                    <th scope="col">Статус обращения</th>
                                    <th scope="col">Сумма</th>
                                    <th scope="col">Дата обращения</th>
                                </tr>
                                </thead>
                                <tbody id="list_active_body">
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane fade" id="list_overdue" role="tabpanel"
                             aria-labelledby="list_overdue_list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Сотрудник</th>
                                    <th scope="col">Наименование клиента</th>
                                    <th scope="col">Статус обращения</th>
                                    <th scope="col">Сумма</th>
                                    <th scope="col">Начало задачи</th>
                                    <th scope="col">Конец задачи</th>
                                    <th scope="col">Результат</th>
                                </tr>
                                </thead>
                                <tbody id="list_overdue_body">
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane fade" id="list_sources" role="tabpanel"
                             aria-labelledby="list_sources_list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Сотрудник</th>
                                    <th scope="col">Наименование клиента</th>
                                    <th scope="col">Статус обращения</th>
                                    <th scope="col">Сумма</th>
                                    <th scope="col">Источник</th>
                                </tr>
                                </thead>
                                <tbody id="list_sources_body">
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane fade" id="list_tasks" role="tabpanel"
                             aria-labelledby="list_tasks_list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Сотрудник</th>
                                    <th scope="col">Наименование клиента</th>
                                    <th scope="col">Статус обращения</th>
                                    <th scope="col">Сумма</th>
                                    <th scope="col">Начала задачи</th>
                                    <th scope="col">Результат</th>
                                </tr>
                                </thead>
                                <tbody id="list_tasks_body">
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane fade" id="list_failures" role="tabpanel"
                             aria-labelledby="list_failures_list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Сотрудник</th>
                                    <th scope="col">Наименование клиента</th>
                                    <th scope="col">Статус обращения</th>
                                    <th scope="col">Продукт/услуга</th>
                                    <th scope="col">Причина отказа</th>
                                </tr>
                                </thead>
                                <tbody id="list_failures_body">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@g.general>