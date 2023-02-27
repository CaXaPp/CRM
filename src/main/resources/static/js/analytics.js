'use strict';
const default_sum = 1000000;

document.addEventListener("DOMContentLoaded", function () {
    getUsersForAnalytics();
    getProductsForAnalytics();
    getSourceForAnalytics();
    selectedCategory('/analytics/application/all');
})

function selectedCategory(e) {
    getAllApplicationForAnalytics(e);
}

function getAllApplicationForAnalytics(path) {
    axios.get(BASE_URL + path).then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data);
    })
}

function createTdElemOnBodyForAnalytics(response) {
    document.getElementById('operation_amount').innerText = response.totalCount.toString();
    document.getElementById('operation_sum_amount').innerText = response.totalSum.toFixed(2).toString();
    document.getElementById('plan_amount').innerText = default_sum.toString();
    document.getElementById('failures_amount').innerText = response.failSum.toFixed(2).toString();
    document.getElementById('success_amount').innerText = response.successSum.toFixed(2).toString();

    let fail = (response.failSum / response.totalSum) * 100;
    let success = (response.successSum / default_sum) * 100;

    if (response.totalSum !== 0) {
        document.getElementById('total_operation_sum').innerText = "100%";
        document.getElementById('total_operation_sum_line').style.width = "100%";
    }

    document.getElementById('plan_operation_sum').innerText = "100%";
    document.getElementById('plan_operation_sum_line').style.width = "100%";

    if (response.failSum !== 0) {
        document.getElementById('sum_failures_percent_span').innerText = fail.toFixed(2).toString() + "%";
        document.getElementById('sum_failures_percent_line').style.width = fail.toString() + "%";
    }
    if(response.successSum !== 0) {
        document.getElementById('sum_success_percent_span').innerText = success.toFixed(2).toString() + "%";
        document.getElementById('sum_success_percent_line').style.width = success.toString() + "%";
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
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
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
    document.getElementById('operation_amount').innerText = "0";
    document.getElementById('operation_sum_amount').innerText = "0";
    document.getElementById('plan_amount').innerText = "0";
    document.getElementById('failures_amount').innerText = "0";
    document.getElementById('success_amount').innerText = "0";

    document.getElementById('total_operation_sum').innerText = "0";
    document.getElementById('total_operation_sum_line').style.width = "0";
    document.getElementById('plan_operation_sum').innerText = "0";
    document.getElementById('plan_operation_sum_line').style.width = "0";
    document.getElementById('sum_failures_percent_span').innerText = "0";
    document.getElementById('sum_failures_percent_line').style.width = "0";
    document.getElementById('sum_success_percent_span').innerText = "0";
    document.getElementById('sum_success_percent_line').style.width = "0";
}

function defaultValueForSourceAndEmployee() {
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
}

function defaultValueForProductAndEmployee() {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
}

function defaultValueForProductAndSource() {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
}

$('all_operation').click(function () {
    document.getElementById('analytics_product').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_source').querySelectorAll('option').item(0).selected = true;
    document.getElementById('analytics_employee').querySelectorAll('option').item(0).selected = true;
})