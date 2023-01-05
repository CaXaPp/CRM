<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/main.js"] styles=["/css/analytics.css"]>
    <div class="dashboard_block">

        <div class="dashboard_block_inner">
            <div class="dashboard_info">
                <div class="dashboard_info_sort_block">
                    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off"
                               checked>
                        <label class="btn btn-outline-primary" for="btnradio1"
                               onclick="dataForToday('today')">Сегодня</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2"
                               onclick="dataForToday('yesterday')">Вчера</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3"
                               onclick="dataForToday('week')">Неделя</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio4"
                               onclick="dataForToday('month')">Месяц</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio5" autocomplete="off"/>
                        <label class="btn btn-outline-primary" id="btnradio5_text" for="btnradio5"></label>
                    </div>

                    <div class="btn-group btn_group_right" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio6" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio6">Все</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio7" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio7">Мои</label>
                    </div>
                </div>

                <div>
                    <div class="dashboard_statistic">

                        <div class="dashboard_statistic_left">
                            <div class="text-center py-3 mb-3">
                                <h5 class="card-title text-primary mb-1" id="new_statements_count">+ 0</h5>
                                <p class="text-primary mb-0">Новые</p>
                            </div>
                            <p class="text-primary mb-0">Потерянные</p>
                        </div>

                        <div class="dashboard_statistic_center">

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-primary title_stat_block">
                                    <p class="text-primary mb-0">Новое</p>
                                    <p class="text-primary mb-0" id="new_statements_total">0 сделок, 0</p>
                                </div>
                                <div class="border-primary border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="new_statements_total_count">0</h5>
                                    <p class="text-primary mb-0" id="new_statements_total_amount">0</p>
                                </div>
                                <p class="text-primary mb-0" id="new_statements_total_extra_info">0 сделок, 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-warning title_stat_block">
                                    <p class="text-primary mb-0">Переговоры</p>
                                    <p class="text-primary mb-0" id="negotiation_total">0 сделок, 0</p>
                                </div>
                                <div class="border-warning border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="negotiation_count">0</h5>
                                    <p class="text-primary mb-0" id="negotiation_amount">0</p>
                                </div>
                                <p class="text-primary mb-0" id="negotiation_extra_info">0 сделок, 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-success title_stat_block">
                                    <p class="text-primary mb-0">Принятие решения</p>
                                    <p class="text-primary mb-0" id="makingDecisions_total">0 сделок, 0</p>
                                </div>
                                <div class="border-success border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="makingDecisions_total_count">0</h5>
                                    <p class="text-primary mb-0" id="makingDecisions_total_amount">0</p>
                                </div>
                                <p class="text-primary mb-0" id="makingDecisions_total_extra_info">0 сделок, 0</p>
                            </div>

                            <div class="dashboard_statistic_inner">
                                <div class="border-2 border-bottom border-danger title_stat_block">
                                    <p class="text-primary mb-0">На обслуживании</p>
                                    <p class="text-primary mb-0" id="underMaintenance_total">0 сделок, 0</p>
                                </div>
                                <div class="border-danger border rounded text-center py-3 mb-3">
                                    <h5 class="card-title text-primary mb-1" id="underMaintenance_total_count">0</h5>
                                    <p class="text-primary mb-0" id="underMaintenance_total_amount">0</p>
                                </div>
                                <p class="text-primary mb-0" id="underMaintenance_total_extra_info">0 сделок, 0</p>
                            </div>
                        </div>

                        <div class="dashboard_statistic_right">
                            <div class="text-center py-3 mb-3">
                                <h5 class="card-title text-primary mb-1" id="makingDecisions_total_count_extra_info">
                                    0</h5>
                                <p class="text-primary mb-0" id="makingDecisions_total_amount_extra_info">0</p>
                            </div>
                            <p class="text-primary mb-0" id="makingDecisions_total_info">0 сделок, 0</p>
                        </div>

                    </div>
                </div>
            </div>
            <div class="section_info">
                <div class="sections">
                    <h2>Разделы</h2>
                    <div class="btn-group sections_inner" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio8" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio8" onclick="completedDealToday()">Активные сделки за сегодня</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio9" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio9" onclick="completedDealToday()">Выполненные сделки за сегодня</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio10" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio10" onclick="overdueTasks()">Просроченные задачи</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio11" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio11" onclick="transactionSources()">Источники сделок</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio12" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio12" onclick="completedDealToday()">Сделки по сотрудникам</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio13" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio13" onclick="tasksCompleted()">Задачи к выполнению</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio14" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio14" onclick="denialService()">Отказы в обслуживании</label>
                    </div>

                </div>
                <div class="information">
                    <h2>Информация</h2>
                    <div id="information_inner" class="information_inner"></div>
                </div>

            </div>
        </div>
    </div>

</@g.general>