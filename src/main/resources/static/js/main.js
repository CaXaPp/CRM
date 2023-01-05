const BASE_URL = "http://localhost:9000";
let start = new Date();

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('btnradio5_text').innerText = start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear() + "-" + start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear();
    dataForToday("today");
    activeDealOnToday();
})

function getAllSumAndCount(date_start, date_end) {
    axios.get(BASE_URL + "/application/all/date", {
        params: {
            startDate: date_start,
            endDate: date_end
        }
    }).then(function (response) {
        clear();
        for (let i = 0; i < response.data.length; i++) {
            createNewStatements(response.data[0], response.data[1]);
            createUnderMaintenance(response.data[2], response.data[3]);
            createNegotiation(response.data[4], response.data[5]);
            createMakingDecisions(response.data[6], response.data[7]);
        }
    })
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
    document.getElementById('makingDecisions_total_count_extra_info').innerText = count.toString();
    document.getElementById('makingDecisions_total_amount_extra_info').innerText = amount.toString();
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

function dataForToday(value) {
    switch (value) {
        case 'today':
            let todayDateStart = returnStartDate();
            let todayDateEnd = returnEndDate();
            getAllSumAndCount(todayDateStart.substring(0, todayDateStart.length - 1), todayDateEnd.substring(0, todayDateStart.length - 1));
            break;
        case 'yesterday':
            let yesterdayDateStart = returnModifiedStartDate(1);
            let yesterdayDateEnd = returnModifiedEndDate(1);
            getAllSumAndCount(yesterdayDateStart.substring(0, yesterdayDateStart.length - 1), yesterdayDateEnd.substring(0, yesterdayDateEnd.length - 1));
            break;
        case 'week':
            let weekDateStart = returnModifiedStartDate(7);
            let weekDateEnd = returnEndDate();
            getAllSumAndCount(weekDateStart.substring(0, weekDateStart.length - 1), weekDateEnd.substring(0, weekDateEnd.length - 1));
            break;
        case 'month':
            let monthDateStart = returnModifiedStartDate(30);
            let monthDateEnd = returnEndDate();
            getAllSumAndCount(monthDateStart.substring(0, monthDateStart.length - 1), monthDateEnd.substring(0, monthDateEnd.length - 1));
            break;
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
    getAllSumAndCount(picker.startDate.format('YYYY-MM-DDTHH:mm:ss.sss'), picker.endDate.format('YYYY-MM-DDTHH:mm:ss.sss'));
    document.getElementById('btnradio5_text').innerText = picker.startDate.format('DD.MM.YYYY') + ' - ' + picker.endDate.format('DD.MM.YYYY');
});

function activeDealOnToday() {
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Сумма</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

function completedDealToday() {
    clearInformationBlock();
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Сумма</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

function overdueTasks() {
    clearInformationBlock();
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Сумма</th>' +
        '<th scope="col">Начало задачи</th>' +
        '<th scope="col">Конец задачи</th>' +
        '<th scope="col">Результат</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

function transactionSources() {
    clearInformationBlock();
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Сумма</th>' +
        '<th scope="col">Источник</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

function tasksCompleted() {
    clearInformationBlock();
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Сумма</th>' +
        '<th scope="col">Начала задачи</th>' +
        '<th scope="col">Результат</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

function denialService() {
    clearInformationBlock();
    document.getElementById('information_inner').insertAdjacentHTML('beforeend',
        '<table class="table" id="information_table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Сотрудник</th>' +
        '<th scope="col">Наименование клиента</th>' +
        '<th scope="col">Статус обращения</th>' +
        '<th scope="col">Продукт/услуга</th>' +
        '<th scope="col">Причина отказа</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr>' +
        '<th scope="row"></th>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>'
    )
}

$('#myList a').on('click', function (e) {
    e.preventDefault()
    $(this).tab('active')
})

function clearInformationBlock() {
    let elem = document.getElementById("information_table");
    elem.remove();
}