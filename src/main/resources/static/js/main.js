'use strict';

const BASE_URL = "http://localhost:9000";
let start = new Date();
let main_role;
let department_id;
let valueForDB;
let username;

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('btnradio5_text').innerText = start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear() + "-" + start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear();
    // dataForToday("today");
    getRoleFromUser();
    setTimeout(function () {
        dataForToday();
    }, 300)
    // setTimeout(function () {
    //     getUsernameForNavbar(username);
    // },100)
    getActiveDealForToday();
})

function getRoleFromUser() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        main_role = response.data.role.name;
        createCategoryInDashboard(response.data.role.name);
        department_id = response.data.department.id;
        username = response.data.firstName;
    })
}

function getUsernameForNavbar(name) {
    console.log(name); // доделать добавление имени пользователя в навбар
    document.getElementById('userNameLogging').innerText = name;
}

function clearCategoryValue() {
    document.getElementById('input_group_2').innerHTML = "";
}

function createCategoryInDashboard(role) {
    if (role === "Сотрудник") {
        clearCategoryValue();
        document.getElementById('input_group_2').insertAdjacentHTML('beforeend',
            '<input type="radio" class="btn-check" value="" name="btnradio1" id="btnradio6" autoComplete="off" checked>' +
            '<label class="btn btn-outline-primary" for="btnradio6" onClick="dataForToday()">Все</label>' +

            '<input type="radio" class="btn-check" value="my" name="btnradio1" id="btnradio7" autoComplete="off">' +
            '<label class="btn btn-outline-primary" for="btnradio7" onClick="dataForToday()">Мои</label>'
        )
        document.getElementById('btnradio6').value = department_id;
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
        clear();
        for (const key in response.data) {
            if (key === "Новое") {
                createNewStatements(response.data[key][1], response.data[key][0]);
            } else if (key === "Переговоры") {
                createNegotiation(response.data[key][1], response.data[key][0]);
            } else if (key === "Принятие решения") {
                createMakingDecisions(response.data[key][1], response.data[key][0]);
            } else if (key === "На обслуживании") {
                createUnderMaintenance(response.data[key][1], response.data[key][0]);
            } else if (key === "Успешно") {
                createSuccessfull(response.data[key][1], response.data[key][0]);
            }
        }
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

function createNewStatements(amount, count) {
    document.getElementById('new_statements_count').innerText = "+" + count.toString();
    document.getElementById('new_statements_total').innerText = count.toString() + " сделок, " + amount.toString();
    document.getElementById('new_statements_total_count').innerText = count.toString();
    document.getElementById('new_statements_total_amount').innerText = amount.toString();
    document.getElementById('new_statements_total_extra_info').innerText = count.toString() + " сделок, " + amount.toString();
}

function createNegotiation(amount, count) {
    document.getElementById('negotiation_total').innerText = count.toString() + " сделок, " + amount.toString();
    document.getElementById('negotiation_count').innerText = count.toString();
    document.getElementById('negotiation_amount').innerText = amount.toString();
    document.getElementById('negotiation_extra_info').innerText = count.toString() + " сделок, " + amount.toString();
}

function createUnderMaintenance(amount, count) {
    document.getElementById('underMaintenance_total').innerText = count.toString() + " сделок, " + amount.toString();
    document.getElementById('underMaintenance_total_count').innerText = count.toString();
    document.getElementById('underMaintenance_total_amount').innerText = amount.toString();
    document.getElementById('underMaintenance_total_extra_info').innerText = count.toString() + " сделок, " + amount.toString();
}

function createMakingDecisions(amount, count) {
    document.getElementById('makingDecisions_total').innerText = count.toString() + " сделок, " + amount.toString();
    document.getElementById('makingDecisions_total_count').innerText = count.toString();
    document.getElementById('makingDecisions_total_amount').innerText = amount.toString();
    document.getElementById('makingDecisions_total_extra_info').innerText = count.toString() + " сделок, " + amount.toString();

}

function createSuccessfull(amount, count) {
    document.getElementById('makingDecisions_total_amount_extra_info').innerText = amount.toString();
    document.getElementById('makingDecisions_total_count_extra_info').innerText = count.toString();
    document.getElementById('makingDecisions_total_info').innerText = count.toString() + " сделок, " + amount.toString();
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
                console.log(valueForDB);
            }
        } else {
            if (getValueFromLoop(parameter) === "my") {
                valueForDB = getValueFromLoop(parameter).toString();
            } else {
                valueForDB = department_id;
                console.log(valueForDB);
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
    }, 100);
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
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/activeApplication").then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                    '<tr>' +
                    '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                    '<td>' + response.data[i][1] + '</td>' +
                    '<td>' + response.data[i][2] + '</td>' +
                    '<td>' + response.data[i][3] + '</td>' +
                    '</tr>'
                )
            }
        }
    )
}

function getCompletedDealOnToday() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/completedDeal").then(function (response) {
            for (let i = 0; i < response.data.length; i++) {
                document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                    '<tr>' +
                    '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                    '<td>' + response.data[i][1] + '</td>' +
                    '<td>' + response.data[i][2] + '</td>' +
                    '<td>' + response.data[i][3] + '</td>' +
                    '</tr>'
                )
            }
        }
    )
}

function getSourceDeal() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/sourceApplication").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_sources_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                '<td>' + response.data[i][1] + '</td>' +
                '<td>' + response.data[i][2] + '</td>' +
                '<td>' + response.data[i][3] + '</td>' +
                '<td>' + response.data[i][4] + '</td>' +
                '</tr>'
            )
        }
    })
}

function getOverdueTasks() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/tasks/task/over").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_overdue_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                '<td>' + response.data[i][1] + '</td>' +
                '<td>' + response.data[i][2] + '</td>' +
                '<td>' + response.data[i][3] + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i][4]) + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i][5]) + '</td>' +
                '<td>' + response.data[i][6] + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllActiveTask() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/tasks/task/active").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_tasks_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                '<td>' + response.data[i][1] + '</td>' +
                '<td>' + response.data[i][2] + '</td>' +
                '<td>' + response.data[i][3] + '</td>' +
                '<td>' + parseDateAndTimeByFormat(response.data[i][4]) + '</td>' +
                '<td>' + response.data[i][5] + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllDealByEmployee() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/deal-by-employee").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_active_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                '<td>' + response.data[i][1] + '</td>' +
                '<td>' + response.data[i][2] + '</td>' +
                '<td>' + response.data[i][3] + '</td>' +
                '</tr>'
            )
        }
    })
}

function getAllFailuresApplication() {
    $("#nav-tabContent td").remove();
    axios.get(BASE_URL + "/application/failureApplication").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            document.getElementById('list_failures_body').insertAdjacentHTML('beforeend',
                '<tr>' +
                '<td>' + response.data[i][0].firstName + " " + response.data[i][0].surname + '</td>' +
                '<td>' + response.data[i][1] + '</td>' +
                '<td>' + response.data[i][2] + '</td>' +
                '<td>' + response.data[i][3] + '</td>' +
                '<td>' + response.data[i][4] + '</td>' +
                '</tr>'
            )
        }
    })
}

function parseDateAndTimeByFormat(date) {
    return date[0] + "-" + zeroValue(date[1]) + "-" + zeroValue(date[2]) + "T" + zeroValue(date[3]) + ":" + zeroValue(date[4]);
}

function zeroValue(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}