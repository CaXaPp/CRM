'use strict';
const BASE_URL = "http://localhost:9000";

document.addEventListener("DOMContentLoaded", function () {
    getUsersForAnalytics();
    getProductsForAnalytics();
    getSourceForAnalytics();
})

function selectedCategory(e) {
    getAllApplicationForAnalytics(e);
}

function getAllApplicationForAnalytics(path) {
    axios.get(BASE_URL + path).then(function (response) {
        deleteAllStatistics();
        createTdElemOnBodyForAnalytics(response.data[0][0], response.data[0][1], response.data[0][2]);
    })
}

function createTdElemOnBodyForAnalytics(count_operation, sum_operation, sumFailures) {
    document.getElementById('operation_amount').innerText = count_operation.toString();
    document.getElementById('operation_sum_amount').innerText = sum_operation.toString();
    document.getElementById('plan_amount').innerText = "20000";
    document.getElementById('failures_amount').innerText = sumFailures.toString();

        let x = (sumFailures / sum_operation) * 100;

        if (sum_operation !== 0) {
            document.getElementById('total_operation_sum').innerText = "100%";
            document.getElementById('total_operation_sum_line').style.width = "100%";
        }

        document.getElementById('plan_operation_sum').innerText = "0";
        document.getElementById('plan_operation_sum_line').style.width = "0";

        if (sumFailures !== 0) {
            document.getElementById('sum_failures_percent_span').innerText = x.toFixed(2).toString() + "%";
            document.getElementById('sum_failures_percent_line').style.width = x.toString() + "%";
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
        '<option value="' + employee[0] + '">' + employee[1] + " " + employee[2] + '</option>'
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
        '<option value="' + product[0] + '">' + product[1] + '</option>')
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

    document.getElementById('total_operation_sum').innerText = "0";
    document.getElementById('total_operation_sum_line').style.width = "0";
    document.getElementById('plan_operation_sum').innerText = "0";
    document.getElementById('plan_operation_sum_line').style.width = "0";
    document.getElementById('sum_failures_percent_span').innerText = "0";
    document.getElementById('sum_failures_percent_line').style.width = "0";
}