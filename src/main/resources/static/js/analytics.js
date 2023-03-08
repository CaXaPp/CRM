'use strict';

document.addEventListener("DOMContentLoaded", function () {
    getUsersForAnalytics();
    getProductsForAnalytics();
    getSourceForAnalytics();
    getAllAnalyticsData();
})

function getAllAnalyticsData() {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
    axios.get(BASE_URL + '/analytics/application/all').then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data);
    })
}

function getAnalyticsDataByProduct(id) {
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
    axios.get(BASE_URL + '/analytics/product/' + id).then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data);
    })
}

function getAnalyticsDataBySource(id) {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
    axios.get(BASE_URL + '/analytics/source/' + id).then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data);
    })
}

function getAnalyticsDataByUser(id) {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
    axios.get(BASE_URL + '/analytics/employee/' + id).then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data);
    })
}

function createTdElemOnBodyForAnalytics(response) {
    document.getElementById("lead_all_sum").innerText = response.totalSum.toString();
    document.getElementById("lead_all_count").innerText = response.totalCount.toString();
    document.getElementById("lead_success_sum").innerText = response.successSum.toString();
    document.getElementById("lead_success_count").innerText = response.successCount.toString();
    document.getElementById("lead_last_month_sum").innerText = response.sumLastMonth.toString();
    document.getElementById("lead_fail_sum").innerText = response.failSum.toString();
    document.getElementById("lead_fail_count").innerText = response.failCount.toString();

    for (let i = 0; i < response.planSum.length; i++) {
        document.getElementById('leads_space').insertAdjacentHTML('beforeend',
            `
            <div class="col abracadabra">
                    <div class="card h-100">
                        <div class="card-header bg-transparent border-success">Сумма по плану</div>
                        <div class="card-body">
                            <h5 class="text-uppercase text-muted mb-2">${response.planSum[i].department.name}</h5>
                            <h2>${response.planSum[i].sum}</h2>
                        </div>
                    </div>
                </div>
            `
        )
    }
}

function getUsersForAnalytics() {
    axios.get(BASE_URL + "/users/employee").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionEmployeeSelectForAnalytics(response.data[i]);
        }
    })
}

function switchOptionEmployeeSelectForAnalytics(employee) {
    document.getElementById('analytics_employee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.fio + '</option>'
    );
}

function getProductsForAnalytics() {
    axios.get(BASE_URL + "/products/product-list").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionProductSelectForAnalytics(response.data[i]);
        }
    })
}

function switchOptionProductSelectForAnalytics(product) {
    document.getElementById('analytics_product').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
}

function getSourceForAnalytics() {
    axios.get(BASE_URL + "/source/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionSourceSelectForAnalytics(response.data[i]);
        }
    })
}

function switchOptionSourceSelectForAnalytics(source) {
    document.getElementById('analytics_source').insertAdjacentHTML('beforeend',
        '<option value="' + source.id + '">' + source.name + '</option>')
}

function deleteAllStatistics() {
    document.getElementById("lead_all_sum").innerText = "";
    document.getElementById("lead_all_count").innerText = "";
    document.getElementById("lead_success_sum").innerText = "";
    document.getElementById("lead_success_count").innerText = "";
    document.getElementById("lead_last_month_sum").innerText = "";
    document.getElementById("lead_fail_sum").innerText = "";
    document.getElementById("lead_fail_count").innerText = "";

    $('.abracadabra').remove();
}