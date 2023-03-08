<#import "parts/general.ftlh" as g>

<@g.general scripts=["/crm/js/analytics.js"] styles=["/crm/css/analytics.css"]>
    <div class="analytics-block">
        <div class="sort_selection_block">
            <select class="form-select" aria-label="Select product" id="analytics_product"
                    name="product" onclick="getAnalyticsDataByProduct(this.value)">
                <option selected disabled>Выбрать продукт</option>
            </select>

            <select class="form-select" aria-label="Select source" id="analytics_source"
                    name="status" onclick="getAnalyticsDataBySource(this.value)">
                <option selected disabled>Выбрать источник</option>
            </select>

            <select class="form-select" aria-label="Select employee" id="analytics_employee"
                    name="employee" onclick="getAnalyticsDataByUser(this.value)">
                <option selected disabled>Выбрать сотрудника</option>
            </select>

            <button class="btn btn-success" id="all_operation" onclick="getAllAnalyticsData()">
                Все операции
            </button>
        </div>
        <div class="information_block">

            <div class="row row-cols-1 row-cols-md-3 g-4" id="leads_space">
                <div class="col">
                    <div class="card h-100">
                        <div class="card-header bg-transparent border-success">Лиды</div>
                        <div class="card-body">
                            <h5 class="text-uppercase text-muted mb-2">Всего, сом</h5>
                            <h2 id="lead_all_sum"></h2>
                        </div>
                        <div class="card-footer">
                                <small class="text-muted">Лиды, шт </small>
                                <p id="lead_all_count"></p>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card h-100">
                        <div class="card-header bg-transparent border-success">Выручка</div>
                        <div class="card-body">
                            <h5 class="text-uppercase text-muted mb-2">Доход, сом</h5>
                            <h2 id="lead_success_sum"></h2>
                        </div>
                        <div class="card-footer card-footer-inner-for-sum">
                            <div class="col-auto">
                                <small class="text-muted">Лиды, шт</small>
                                <p id="lead_success_count"></p>
                            </div>
                            <div>
                                <small class="text-muted">За прошлый месяц, сом: </small>
                                <p id="lead_last_month_sum"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card h-100">
                        <div class="card-header bg-transparent border-success">Не реализовано</div>
                        <div class="card-body">
                            <h5 class="text-uppercase text-muted mb-2">Всего, сом</h5>
                            <h2 id="lead_fail_sum"></h2>
                        </div>
                        <div class="card-footer">
                                <small class="text-muted">Лиды, шт</small>
                                <p id="lead_fail_count"></p>
                        </div>
                    </div>
                </div>
            </div>


<#--            <div class="card m-b-30 information_block_inner">-->
<#--                <div class="card-body">-->
<#--                    <div class="row align-items-end">-->
<#--                        <div class="col-6 col-lg-3">-->
<#--                            <div class="border-primary border rounded text-center py-3 mb-3">-->
<#--                                <h5 class="card-title text-primary mb-1" id="operation_amount">0</h5>-->
<#--                                <p class="text-primary mb-0">Кол-во операций</p>-->
<#--                            </div>-->
<#--                            <div class="border-info border rounded text-center py-3 mb-3">-->
<#--                                <h5 class="card-title text-info mb-1" id="operation_sum_amount">0</h5>-->
<#--                                <p class="text-info mb-0">Сумма всех операций</p>-->
<#--                            </div>-->
<#--                            <div class="border-success border rounded text-center py-3 mb-3">-->
<#--                                <h5 class="card-title text-success mb-1" id="success_amount">0</h5>-->
<#--                                <p class="text-success mb-0">Сумма успешных операций</p>-->
<#--                            </div>-->
<#--                            <div class="border-danger border rounded text-center py-3 mb-3">-->
<#--                                <h5 class="card-title text-danger mb-1" id="failures_amount">0</h5>-->
<#--                                <p class="text-danger mb-0">Сумма по отказам</p>-->
<#--                            </div>-->
<#--                            <div class="border-warning border rounded text-center py-3 mb-3">-->
<#--                                <h5 class="card-title text-warning mb-1" id="plan_amount">0</h5>-->
<#--                                <p class="text-warning mb-0">Сумма по плану</p>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                        <div class="col-6 col-lg-9" style="justify-content: flex-end">-->
<#--                            <p>Общая сумма операций <span class="float-right" id="total_operation_sum"></span></p>-->
<#--                            <div class="progress mb-5" style="height: 5px;">-->
<#--                                <div class="progress-bar bg-info" role="progressbar" id="total_operation_sum_line"-->
<#--                                     aria-valuenow="55"-->
<#--                                     aria-valuemin="0" aria-valuemax="100"></div>-->
<#--                            </div>-->
<#--                            <p>Общая сумма по успешным <span class="float-right" id="sum_success_percent_span"></span>-->
<#--                            </p>-->
<#--                            <div class="progress mb-5" style="height: 5px;">-->
<#--                                <div class="progress-bar bg-success" role="progressbar" id="sum_success_percent_line"-->
<#--                                     aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>-->
<#--                            </div>-->
<#--                            <p>Общая сумма по отказам <span class="float-right" id="sum_failures_percent_span"></span>-->
<#--                            </p>-->
<#--                            <div class="progress mb-5" style="height: 5px;">-->
<#--                                <div class="progress-bar bg-danger" role="progressbar" id="sum_failures_percent_line"-->
<#--                                     aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>-->
<#--                            </div>-->
<#--                            <p>Запланированная сумма <span class="float-right" id="plan_operation_sum"></span></p>-->
<#--                            <div class="progress mb-5" style="height: 5px;">-->
<#--                                <div class="progress-bar bg-warning" role="progressbar" id="plan_operation_sum_line"-->
<#--                                     aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->

        </div>
    </div>

</@g.general>