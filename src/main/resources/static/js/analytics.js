'use strict';
const BASE_URL = "http://localhost:9000";

document.addEventListener("DOMContentLoaded", function () {
    getUsersForAnalytics();
    getProductsForAnalytics();
    getStatusesForAnalytics();
    getSourceForAnalytics();
})

function selectedCategory(e) {
    getAllApplicationForAnalytics(e);
}

async function getAllApplicationForAnalytics(path) {
    var count_operation = 0;
    var sum_operation = 0;
    var sumFailures = 0;
    await axios.get(BASE_URL + path).then(function (response) {
        if (response.data.length !== 0) {
            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].employee !== null) {
                    count_operation++;
                    sum_operation += response.data[i].price;
                    if (response.data[i].status.id === 4) {
                        sumFailures += response.data[i].price;
                    }
                    createTdElemOnBodyForAnalytics(count_operation, sum_operation, sumFailures);
                }
            }
        } else {
            deleteAllStatistics();
        }
    })
}

function createTdElemOnBodyForAnalytics(count_operation, sum_operation, sumFailures) {
    document.getElementById('operation_amount').innerText = count_operation.toString();
    document.getElementById('operation_sum_amount').innerText = sum_operation.toString();
    document.getElementById('plan_amount').innerText = "20000";
    document.getElementById('failures_amount').innerText = sumFailures.toString();

    let x = (sumFailures / sum_operation) * 100;

    document.getElementById('total_operation_sum').innerText = "100%";
    document.getElementById('total_operation_sum_line').style.width = "100%";
    document.getElementById('plan_operation_sum').innerText = "0";
    document.getElementById('plan_operation_sum_line').style.width = "0";
    document.getElementById('sum_failures_percent_span').innerText = x.toFixed(2).toString() + "%";
    document.getElementById('sum_failures_percent_line').style.width = x.toString() + "%";
}

function getUsersForAnalytics() {
    axios.get(BASE_URL + "/users/all").then(function (response) {
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
    axios.get(BASE_URL + "/products/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionProductSelectForAnalytics(response.data[i]);
        }
    })
}

function switchOptionProductSelectForAnalytics(product) {
    document.getElementById('analytics_product').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '" onclick="selectedCategory(' + "/product/" + product.id + ')">' + product.name + '</option>')
}

function getStatusesForAnalytics() {
    axios.get(BASE_URL + "/statuses/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionStatusSelectForAnalytics(response.data[i]);
        }
    })
}

function switchOptionStatusSelectForAnalytics(status) {
    document.getElementById('analytics_status').insertAdjacentHTML('beforeend',
        '<option value="' + status.id + '" onclick="selectedCategory(' + "/status/" + status.id + ')">' + status.name + '</option>')
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
        '<option value="' + source.id + '" onclick="selectedCategory(' + "/source/" + source.id + ')">' + source.name + '</option>')
}

function deleteAllStatistics() {
    document.getElementById('operation_amount').innerText = "0";
    document.getElementById('operation_sum_amount').innerText = "0";
    document.getElementById('plan_amount').innerText = "0";
    document.getElementById('failures_amount').innerText = "0";

    document.getElementById('total_operation_sum').innerText = "0";
    document.getElementById('total_operation_sum_line').innerText = "0";
    document.getElementById('plan_operation_sum').innerText = "0";
    document.getElementById('plan_operation_sum_line').innerText = "0";
    document.getElementById('sum_failures_percent_span').innerText = "0";
    document.getElementById('sum_failures_percent_line').style.width = "0";
}