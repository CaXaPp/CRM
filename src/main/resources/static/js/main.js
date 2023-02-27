'use strict';

let start = new Date();
let main_role;
let department_id;
let valueForDB;

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('btnradio5_text').innerText = start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear() + "-" + start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear();
    getRoleFromUser();
    setTimeout(function () {
        dataForToday();
    }, 300)
    getActiveDealForToday();
})

function getRoleFromUser() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        main_role = response.data.role;
        createCategoryInDashboard(response.data);
        department_id = response.data.department.id;
    })
}

function clearCategoryValue() {
    document.getElementById('input_group_2').innerHTML = "";
}

function createCategoryInDashboard(user) {
    if (user.role === "Сотрудник") {
        clearCategoryValue();
        document.getElementById('input_group_2').insertAdjacentHTML('beforeend',
            '<input type="radio" class="btn-check" value="' + user.department.id + '" name="btnradio1" id="btnradio6" autoComplete="off" checked>' +
            '<label class="btn btn-outline-primary" for="btnradio6" onclick="dataForToday()">Все</label>' +

            '<input type="radio" class="btn-check" value="my" name="btnradio1" id="btnradio7" autoComplete="off">' +
            '<label class="btn btn-outline-primary" for="btnradio7" onclick="dataForToday()">Мои</label>'
        )
    } else {
        clearCategoryValue();
        document.getElementById('input_group_2').insertAdjacentHTML('beforeend',
            '<input type="radio" class="btn-check" value="all" name="btnradio1" id="btnradio6" autoComplete="off" checked>' +
            '<label class="btn btn-outline-primary" for="btnradio6" id="btnradio100" onClick="getValueFromInputForAll()">Все</label>' +
            // onchange="changeValue()"
            '<select class="form-select" aria-label="Select status" onClick="getValueFromSelect()" id="selectDepartment" required>' +
            '<option selected disabled>По отделам</option>' +
            '</select>'
        )
        getDepartments();
    }
}

// фукнции используются внутри JS (НЕ УДАЛЯТЬ!)
function getValueFromInputForAll() {
    dataForToday();
    document.getElementById('selectDepartment').querySelectorAll('option').item(0).selected = true;
}

function getValueFromSelect() {
    dataForToday();
    document.getElementById('btnradio6').checked = false;
}

function getAllSumAndCount(date_start, date_end, value) {
    axios.get(BASE_URL + "/application/all/date", {
        params: {
            startDate: date_start,
            endDate: date_end,
            parameter: value
        }
    }).then(function (response) {
        console.log(response.data);
        clear();
        createNewStatements(response.data[0]);
        createNegotiation(response.data[1]);
        createMakingDecisions(response.data[2]);
        createUnderMaintenance(response.data[3]);
        createSuccessfull(response.data[4]);
    })
}


function getDepartments() {
    axios.get(BASE_URL + "/departments/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionDepartmentsSelect(response.data[i]);
        }
    })
}

function switchOptionDepartmentsSelect(department) {
    document.getElementById('selectDepartment').insertAdjacentHTML('beforeend',
        '<option value="' + department.id + '" onClick="dataForToday()">' + department.name + '</option>')
}

function createNewStatements(response) {
    document.getElementById('new_statements_count').innerText = "+" + response.count.toString();
    document.getElementById('new_statements_total').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
    document.getElementById('new_statements_total_count').innerText = response.count.toString();
    document.getElementById('new_statements_total_amount').innerText = response.sum.toFixed(2).toString();
    document.getElementById('new_statements_total_extra_info').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
}

function createNegotiation(response) {
    document.getElementById('negotiation_total').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
    document.getElementById('negotiation_count').innerText = response.count.toString();
    document.getElementById('negotiation_amount').innerText = response.sum.toFixed(2).toString();
    document.getElementById('negotiation_extra_info').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
}

function createUnderMaintenance(response) {
    document.getElementById('underMaintenance_total').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
    document.getElementById('underMaintenance_total_count').innerText = response.count.toString();
    document.getElementById('underMaintenance_total_amount').innerText = response.sum.toFixed(2).toString();
    document.getElementById('underMaintenance_total_extra_info').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
}

function createMakingDecisions(response) {
    document.getElementById('makingDecisions_total').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
    document.getElementById('makingDecisions_total_count').innerText = response.count.toString();
    document.getElementById('makingDecisions_total_amount').innerText = response.sum.toFixed(2).toString();
    document.getElementById('makingDecisions_total_extra_info').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();

}

function createSuccessfull(response) {
    document.getElementById('makingDecisions_total_amount_extra_info').innerText = response.sum.toFixed(2).toString();
    document.getElementById('makingDecisions_total_count_extra_info').innerText = response.count.toString();
    document.getElementById('makingDecisions_total_info').innerText = response.count.toString() + " сделок, " + response.sum.toFixed(2).toString();
}

function clear() {
    document.getElementById('new_statements_count').innerText = "+0";
    document.getElementById('new_statements_total').innerText = "0 сделок, 0";
    document.getElementById('new_statements_total_count').innerText = "0";
    document.getElementById('new_statements_total_amount').innerText = "0";
    document.getElementById('new_statements_total_extra_info').innerText = "0 сделок, 0";
    document.getElementById('negotiation_total').innerText = "0 сделок, 0";
    document.getElementById('negotiation_count').innerText = "0";
    document.getElementById('negotiation_amount').innerText = "0";
    document.getElementById('negotiation_extra_info').innerText = "0 сделок, 0";
    document.getElementById('underMaintenance_total').innerText = "0 сделок, 0";
    document.getElementById('underMaintenance_total_count').innerText = "0";
    document.getElementById('underMaintenance_total_amount').innerText = "0";
    document.getElementById('underMaintenance_total_extra_info').innerText = "0 сделок, 0";
    document.getElementById('makingDecisions_total').innerText = "0 сделок, 0";
    document.getElementById('makingDecisions_total_count').innerText = "0";
    document.getElementById('makingDecisions_total_amount').innerText = "0";
    document.getElementById('makingDecisions_total_extra_info').innerText = "0 сделок, 0";
    document.getElementById('makingDecisions_total_count_extra_info').innerText = "0";
    document.getElementById('makingDecisions_total_amount_extra_info').innerText = "0";
    document.getElementById('makingDecisions_total_info').innerText = "0 сделок, 0";
}

function dataForToday() {
    setTimeout(function () {

        let date_value = document.getElementById('input_group_1').querySelectorAll('input');
        let parameter = document.getElementById('input_group_2').querySelectorAll('input');

        if (main_role !== "Сотрудник") {
            if (getValueFromLoop(parameter) === "all") {
                valueForDB = getValueFromLoop(parameter).toString();
            } else {
                valueForDB = document.getElementById('selectDepartment').value.toString();
            }
        } else {
            if (getValueFromLoop(parameter) === "my") {
                valueForDB = getValueFromLoop(parameter).toString();
            } else {
                valueForDB = department_id;
            }
        }

        switch (getValueFromLoop(date_value)) {
            case 'today':
                let todayDateStart = returnStartDate();
                let todayDateEnd = returnEndDate();
                getAllSumAndCount(todayDateStart.substring(0, todayDateStart.length - 1), todayDateEnd.substring(0, todayDateStart.length - 1), valueForDB);
                break;
            case 'yesterday':
                let yesterdayDateStart = returnModifiedStartDate(1);
                let yesterdayDateEnd = returnModifiedEndDate(1);
                getAllSumAndCount(yesterdayDateStart.substring(0, yesterdayDateStart.length - 1), yesterdayDateEnd.substring(0, yesterdayDateEnd.length - 1), valueForDB);
                break;
            case 'week':
                let weekDateStart = returnModifiedStartDate(7);
                let weekDateEnd = returnEndDate();
                getAllSumAndCount(weekDateStart.substring(0, weekDateStart.length - 1), weekDateEnd.substring(0, weekDateEnd.length - 1), valueForDB);
                break;
            case 'month':
                let monthDateStart = returnModifiedStartDate(30);
                let monthDateEnd = returnEndDate();
                getAllSumAndCount(monthDateStart.substring(0, monthDateStart.length - 1), monthDateEnd.substring(0, monthDateEnd.length - 1), valueForDB);
                break;
        }
    }, 1000);
}

function getValueFromLoop(value) {
    for (let i = 0; i < value.length; i++) {
        if (value[i].checked) {
            return value[i].value;
        }
    }
}

function returnStartDate() {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate(), 0, 0, 1)).toISOString();
}

function returnEndDate() {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate(), 23, 59, 59)).toISOString();
}

function returnModifiedStartDate(date) {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate() - date, 0, 0, 0, 1)).toISOString();
}

function returnModifiedEndDate(date) {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate() - date, 23, 59, 59, 59)).toISOString();
}

$(function () {
    $('input[id="btnradio5"]').daterangepicker({
        opens: 'left',
        startDate: moment().startOf(start),
        endDate: moment().startOf(start),
        locale: {
            format: 'DD-MM-YYYY'
        }
    });
});

$('input[id="btnradio5"]').on('apply.daterangepicker', function (ev, picker) {
    getAllSumAndCount(picker.startDate.format('YYYY-MM-DDTHH:mm:ss.sss'), picker.endDate.format('YYYY-MM-DDTHH:mm:ss.sss'), valueForDB);
    document.getElementById('btnradio5_text').innerText = picker.startDate.format('DD.MM.YYYY') + ' - ' + picker.endDate.format('DD.MM.YYYY');
});

$('#myList a').on('click', function (e) {
    e.preventDefault()
    $(this).tab('active')
})

function clearInformationBlock() {
    let elem = document.getElementById("information_table");
    elem.remove();
}

function getActiveDealForToday() {
    spinnerDisplayFlex()
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/section/active").then(function (response) {
        spinnerDisplayNone();
            for (let i = 0; i < response.data.length; i++) {
                document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                    '<tr>' +
                    '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                    '<td>' + response.data[i].company + '</td>' +
                    '<td>' + response.data[i].status.name + '</td>' +
                    '<td>' + response.data[i].price + '</td>' +
                    '<td>' + parseDateToDate(response.data[i].created_at) + '</td>' +
                    '</tr>'
                )
            }
        }
    )
}
function spinnerDisplayNone(){
    document.getElementById('spinner_in_main').setAttribute('style', 'display:none !important');

}
function spinnerDisplayFlex(){
    document.getElementById('spinner_in_main').setAttribute('style', 'display:flex !important');
}

function getCompletedDealOnToday() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/section/complete").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
                document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                    '<tr>' +
                    '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                    '<td>' + response.data[i].company + '</td>' +
                    '<td>' + response.data[i].status.name + '</td>' +
                    '<td>' + response.data[i].price + '</td>' +
                    '<td>' + parseDateToDate(response.data[i].created_at) + '</td>' +
                    '</tr>'
                )
            }
        }
    )
}

function getSourceDeal() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/section/source").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_sources_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                '<td>' + response.data[i].company + '</td>' +
                '<td>' + response.data[i].status.name + '</td>' +
                '<td>' + response.data[i].price + '</td>' +
                '<td>' + response.data[i].source.name + '</td>' +
                '</tr>'
            )
        }
    })
}

function getOverdueTasks() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/tasks/task/over").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_overdue_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                '<td>' + response.data[i].application.company + '</td>' +
                '<td>' + response.data[i].application.status.name + '</td>' +
                '<td>' + response.data[i].application.price + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i].createdAt) + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i].deadline) + '</td>' +
                '<td>' + response.data[i].result + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllActiveTask() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/tasks/task/active").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_tasks_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                '<td>' + response.data[i].application.company + '</td>' +
                '<td>' + response.data[i].application.status.name + '</td>' +
                '<td>' + response.data[i].application.price + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i].createdAt) + '</td>' +
                '<td>' + response.data[i].result + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllDealByEmployee() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/section/deal").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                '<td>' + response.data[i].company + '</td>' +
                '<td>' + response.data[i].status.name + '</td>' +
                '<td>' + response.data[i].price + '</td>' +
                '<td>' + parseDateToDate(response.data[i].created_at) + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllFailuresApplication() {
    spinnerDisplayFlex();
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/section/failure").then(function (response) {
        spinnerDisplayNone();
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_failures_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i].employee.firstName + " " + response.data[i].employee.surname + '</td>' +
                '<td>' + response.data[i].company + '</td>' +
                '<td>' + response.data[i].price + '</td>' +
                '<td>' + response.data[i].product.name + '</td>' +
                '<td>' + response.data[i].description + '</td>' +
                '</tr>'
            )
        }
    })
}

function parseDateAndTimeByFormat(date) {
    return date[0] + "-" + zeroValue(date[1]) + "-" + zeroValue(date[2]) + "T" + zeroValue(date[3]) + ":" + zeroValue(date[4]);
}

function parseDateToDate(date) {
    return calcTimeInLoop(date[2]) + "." + calcTimeInLoop(date[1]) + "." + date[0];
}

function zeroValue(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}

function calcTimeInLoop(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}