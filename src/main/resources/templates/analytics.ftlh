<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/analytics.js"] styles=["/css/analytics.css"]>
    <div>
        <div class="sort_selection_block">
            <select class="form-select" aria-label="Select product" id="analytics_product"
                    name="product" onclick="selectedCategory('/analytics/product/' + this.value)" required>По продуктам</select>

            <select class="form-select" aria-label="Select source" id="analytics_source"
                    name="status" onclick="selectedCategory('/analytics/source/' + this.value)" required>Источник обращения</select>

            <select class="form-select" aria-label="Select employee" id="analytics_employee"
                    name="employee" onclick="selectedCategory('/analytics/employee/' + this.value)" required>По сотруднику</select>

            <button class="btn btn-success" id="all_operation" onclick="selectedCategory('/analytics/application/all')">Все операции</button>
        </div>
        <div class="information_block">
            <div class="card m-b-30 information_block_inner">
                <div class="card-body">
                    <div class="row align-items-end">
                        <div class="col-6 col-lg-3">
                            <div class="border-primary border rounded text-center py-3 mb-3">
                                <h5 class="card-title text-primary mb-1" id="operation_amount">0</h5>
                                <p class="text-primary mb-0">Кол-во операций</p>
                            </div>
                            <div class="border-warning border rounded text-center py-3 mb-3">
                                <h5 class="card-title text-warning mb-1" id="operation_sum_amount">0</h5>
                                <p class="text-warning mb-0">Сумма операций</p>
                            </div>
                            <div class="border-success border rounded text-center py-3 mb-3">
                                <h5 class="card-title text-success mb-1" id="plan_amount">0</h5>
                                <p class="text-success mb-0">Сумма по плану</p>
                            </div>
                            <div class="border-danger border rounded text-center py-3">
                                <h5 class="card-title text-danger mb-1" id="failures_amount">0</h5>
                                <p class="text-danger mb-0">Сумма по отказам</p>
                            </div>
                        </div>
                        <div class="col-6 col-lg-9" style="justify-content: flex-end">
                            <p>Общая сумма операций <span class="float-right" id="total_operation_sum"></span></p>
                            <div class="progress mb-5" style="height: 5px;">
                                <div class="progress-bar" role="progressbar" id="total_operation_sum_line" aria-valuenow="55"
                                     aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <p>Запланированная сумма <span class="float-right" id="plan_operation_sum"></span></p>
                            <div class="progress mb-5" style="height: 5px;">
                                <div class="progress-bar bg-success" role="progressbar" id="plan_operation_sum_line"
                                     aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <p>Общая сумма по отказам <span class="float-right" id="sum_failures_percent_span"></span></p>
                            <div class="progress mb-5" style="height: 5px;">
                                <div class="progress-bar bg-warning" role="progressbar" id="sum_failures_percent_line"
                                     aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</@g.general>