'use strict';
let date = "55555"; // default value
let years_of_100 = 36500;
let application_id = 0;
let check = true;
let start = new Date();
let userFullName;
let start_date_for_sort = returnModifiedStartDateInApplication(years_of_100).substring(0, returnModifiedStartDateInApplication(years_of_100).length - 1);
let end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
let status_value;

document.getElementById('modal_edit_form').addEventListener('submit', updateEditedApplication);
document.getElementById('updateAddedTask').addEventListener('submit', updateAddedTask);
document.getElementById('add_new_application').addEventListener('submit', addApplication);

document.addEventListener("DOMContentLoaded", function () {
    getAllActiveApplicationAllTime();
    document.getElementById('btnradio5_in_application_text').innerText = start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear() + "-" + start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear();
})

function getAllApplication() {
    document.getElementById('search_data_input').value = "";
    document.getElementById('emptyMsg').style.display = "none";
    document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:flex !important');
    deleteAllApplication();
    axios.get(BASE_URL + "/application/all").then(function (response) {
        if (response.data.length === 0) {
            document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:none !important');
            document.getElementById('emptyMsg').style.display = "block";
        } else {
            document.getElementById('emptyMsg').style.display = "none";
            for (let i = 0; i < response.data.length; i++) {
                createTdElemOnBody(response.data[i]);
            }
        }
    })
}

function getAllActiveApplicationAllTime() {
    status_value = "all_active";
    document.getElementById('search_data_input').value = "";
    document.getElementById('emptyMsg').style.display = "none";
    document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:flex !important');
    deleteAllApplication();
    axios.get(BASE_URL + "/application/all-active").then(function (response) {
        if (response.data.length === 0) {
            document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:none !important');
            document.getElementById('emptyMsg').style.display = "block";
        } else {
            document.getElementById('emptyMsg').style.display = "none";
            for (let i = 0; i < response.data.length; i++) {
                createTdElemOnBody(response.data[i]);
            }
        }
    })
}

function getLogsForApplication(id) {
    cleanBlockInner();
    axios.get(BASE_URL + '/logs/' + id).then(function (response) {
        response.data.reverse();
        for (let i = 0; i < response.data.length; i++) {
            createLogsBlock(response.data[i]);
        }
    })
}

function getUsers(id) {
    axios.get(BASE_URL + "/users/not-admin").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionEmployeeSelect(response.data[i], id);
        }
    })
}

function uploadFunnel(e) {
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    getStatuses(e);
}

function getProducts(id, productId) {
    axios.get(BASE_URL + "/products/product/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionProductSelect(response.data[i], productId);
        }
    })
}

function getProductsForNewApplication() {
    deleteItems_v_4();
    axios.get(BASE_URL + "/products/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionAllProductSelectForNewApplication(response.data[i]);
        }
    })
}

function getStatuses(id, statusId) {
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionStatusSelect(response.data[i], statusId);
        }
    })
}

function getSources(id) {
    axios.get(BASE_URL + "/source/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionSourceSelect(response.data[i], id);
        }
    })
}

function getTasks(id) {
    deleteAllTaskElement();
    axios.get(BASE_URL + "/tasks/tasks-by-id/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTasksInApplication(response.data[i]);
        }
    })
}

function getTaskType() {
    axios.get(BASE_URL + "/task-type/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTaskTypeOption(response.data[i]);
        }
    })
}

function getFunnels(id) {
    axios.get(BASE_URL + "/funnels/funnel/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionFunnelsSelect(response.data[i]);
        }
    })
}

function getMessage() {
    let msg = document.getElementById('alert_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function getMessageForCreate() {
    let msg = document.getElementById('alert_new_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function applicationEditModalWindow(id) {
    document.getElementById('spinner_in_application').setAttribute('style', 'display:flex !important');
    axios
        .get(BASE_URL + '/application/edit/' + id).then(function (response) {
        application_id = id;
        cleanTextareaValue();
        deleteItems();

        fillFields(response.data);

        getLogsForApplication(id);
        getTaskType();
        getTasks(id);

        getFunnels(response.data.product.department.id);
        getProducts(response.data.product.department.id, response.data.product.id);
        getStatuses(response.data.product.department.id, response.data.status.id);
        getSources(response.data.source.id);
    });
}

function getAllApplicationByDate(date_start, date_end, status) {
    document.getElementById('search_data_input').value = "";
    document.getElementById('emptyMsg').style.display = "none";
    document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:flex !important');
    start_date_for_sort = date_start;
    end_date_for_sort = date_end;
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-date", {
        params: {
            startDate: date_start,
            endDate: date_end,
            status: status
        }
    }).then(function (response) {
        if (response.data.length === 0) {
            document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:none !important');
            document.getElementById('emptyMsg').style.display = "block";
        }
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
    })
}

function updateEditedApplication(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/edit", {
        method: 'PUT',
        body: data
    })
        .then(() => {
            deleteAllApplication();
            getAllApplication();
            getLogsForApplication(application_id);
            getMessage();
        })
        .catch((error) => {
            console.log('Error:', error);
        });
}

function updateAddedTask(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/tasks", {
        method: 'POST',
        body: data
    })
        .then(() => {
            getTasks(application_id);
        })
        .catch((error) => {
            console.log('Error:', error);
        })
}

async function fillNewApplication() {
    cleanNewApplicationForm();
    await getProductsForNewApplication();
}

function cleanNewApplicationForm() {
    document.getElementById('new_company').value = "";
    document.getElementById('new_price').value = "";
    document.getElementById('new_name').value = "";
    document.getElementById('new_phone').value = "";
    document.getElementById('new_email').value = "";
    document.getElementById('new_address').value = "";
}

function addApplication(e) {
    let saveBtn = document.getElementById('btnForSaveInApplication');
    saveBtn.insertAdjacentHTML('beforeend',
        '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>'
    );
    saveBtn.disabled = true;
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/save", {
        method: 'POST',
        body: data
    })
        .then(() => {
            deleteAllApplication();
            getAllActiveApplicationAllTime();
            getMessageForCreate();
            deleteItems_v_3();
            saveBtn.disabled = false;
        })
        .catch((error) => {
            console.log('Error:', error);
        });
}

function sortFunctionByApplicationId() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-id", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

function sortFunctionByApplicationCompany() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-company", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

function sortFunctionByApplicationPrice() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-price", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

function sortFunctionByApplicationProduct() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-product", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

function sortFunctionByApplicationStatus() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-status", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

function sortFunctionByApplicationEmployee() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-employee", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            status: status_value
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

$('#search_data_input').on("input", function (ev) {
    document.getElementById('emptyMsg').style.display = "none";
    document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:flex !important');
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/find-by-company", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            text: ($(ev.target).val()),
            status: status_value
        }
    }).then(function (response) {
        if (response.data.length === 0) {
            document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:none !important');
            document.getElementById('emptyMsg').style.display = "block";
        }
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })

});

function createTdElemOnBody(response) {
    document.getElementById('spinner_in_application_pulls').setAttribute('style', 'display:none !important');
    let employee = (response.employee !== null) ? response.employee.firstName + " " + response.employee.surname : "Не назначено";
    let price = (response.price !== null) ? response.price : 0;

    document.getElementById('tdsBlock').insertAdjacentHTML((check === true) ? 'beforeend' : 'afterbegin',
        '<tr>' +
        '<td>' +
        '<button type="button" class="' + createClassForBtn(response.status.name) + '"' +
        '  onclick="applicationEditModalWindow(' + response.id + ')" data-bs-toggle="modal" data-bs-target="#exampleModal">' + response.id + '</button>' +
        '</td>' +
        '<td>' + response.company + '</td>' +
        '<td>' + price + '</td>' +
        '<td>' + response.product.name + '</td>' +
        '<td>' + response.name + '</td>' +
        '<td>' + response.phone + '</td>' +
        '<td>' + response.email + '</td>' +
        '<td>' + response.address + '</td>' +
        '<td>' + response.status.name + '</td>' +
        '<td>' + employee + '</td>' +
        '</tr>'
    )
}

function createClassForBtn(status) {
    if (status === "Новое") {
        return "btn btn-warning";
    } else if (status === "Отказ") {
        return "btn btn-danger";
    } else if (status === "Успешно") {
        return "btn btn-success";
    } else {
        return "btn btn-primary"
    }
}

function createLogsBlock(response) {
    if (date !== parseDateToLocalDate(response.date)) {
        document.getElementById('spinner_in_application').setAttribute('style', 'display:none !important');
        document.getElementById('blockForLogsInApplication').insertAdjacentHTML('beforeend',
            '<p class="blockForLogsInApplication_main_date"><span>' + parseDateToLocalDate(response.date) + '</span></p>');
        elementCreationCycle(response);
        date = parseDateToLocalDate(response.date);
    } else {
        document.getElementById('spinner_in_application').setAttribute('style', 'display:none !important');
        elementCreationCycle(response);
        date = parseDateToLocalDate(response.date);
    }
}

function fillFields(response) {
    document.getElementById('modal_title_from_js').innerText = "Заявление # " + response.id;
    document.getElementById('id').value = response.id;
    document.getElementById('company').value = response.company;
    document.getElementById('price').value = response.price;
    document.getElementById('name').value = response.name;
    document.getElementById('phone').value = response.phone;
    document.getElementById('email').value = response.email;
    document.getElementById('address').value = response.address;
    document.getElementById('hiddenApplicationId').value = response.id;
    document.getElementById('createdAt').value = parseDateByFormat(response.created_at);
    document.getElementById('operationId').value = response.id;
    document.getElementById('departmentInApplication').value = response.product.department.name;

    if (response.description === "null") {
        document.getElementById('applicationTextarea').innerText = "";
    } else {
        document.getElementById('applicationTextarea').innerText = response.description;
    }

    if (response.employee === null) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="">' + "--- Назначить ---" + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="">' + "--- Назначить ---" + '</option>'
        );
        getUsers(0);
    } else {
        getUsers(response.employee.id);
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.employee.id + '">' + response.employee.fio + '</option>'
        );
    }
}

function switchOptionEmployeeSelect(employee, employeeId) {
    if (employee.id === employeeId) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + employee.id + '">' + employee.fio + '</option>'
        );
    } else {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option value="' + employee.id + '">' + employee.fio + '</option>'
        );
    }

}

function switchOptionAllProductSelectForNewApplication(product) {
    document.getElementById('new_product').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
}

function switchOptionFunnelsSelect(funnel) {
    document.getElementById('funnelInApplication').insertAdjacentHTML('beforeend',
        '<option value="' + funnel.id + '">' + funnel.name + '</option>')
}

function createTaskTypeOption(response) {
    document.getElementById('taskTypeChoose').insertAdjacentHTML('beforeend',
        '<option selected value="' + response.id + '">' + response.name + '</option>'
    );
}

function dataForTodayInApplication() {
    let date = document.getElementById('input_group_application').querySelectorAll('input');
    let value = getCheckedDateFromBody(date);
    switch (value) {
        case 'today':
            getAllApplicationByDate(returnStartDateInApplication().substring(0, returnStartDateInApplication().length - 1), returnEndDateInApplication().substring(0, returnStartDateInApplication().length - 1), status_value);
            start_date_for_sort = returnStartDateInApplication().substring(0, returnStartDateInApplication().length - 1);
            end_date_for_sort = returnEndDateInApplication().substring(0, returnStartDateInApplication().length - 1);
            break;
        case 'yesterday':
            getAllApplicationByDate(returnModifiedStartDateInApplication(1).substring(0, returnModifiedStartDateInApplication(1).length - 1), returnModifiedEndDateInApplication(1).substring(0, returnModifiedEndDateInApplication(1).length - 1), status_value);
            start_date_for_sort = returnModifiedStartDateInApplication(1).substring(0, returnModifiedStartDateInApplication(1).length - 1);
            end_date_for_sort = returnModifiedEndDateInApplication(1).substring(0, returnModifiedEndDateInApplication(1).length - 1);
            break;
        case 'week':
            getAllApplicationByDate(returnModifiedStartDateInApplication(7).substring(0, returnModifiedStartDateInApplication(7).length - 1), returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1), status_value);
            start_date_for_sort = returnModifiedStartDateInApplication(7).substring(0, returnModifiedStartDateInApplication(7).length - 1);
            end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
            break;
        case 'month':
            getAllApplicationByDate(returnModifiedStartDateInApplication(30).substring(0, returnModifiedStartDateInApplication(30).length - 1), returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1), status_value);
            start_date_for_sort = returnModifiedStartDateInApplication(30).substring(0, returnModifiedStartDateInApplication(30).length - 1);
            end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
            break;
        case 'infinity':
            getAllApplication();
            start_date_for_sort = returnModifiedStartDateInApplication(years_of_100).substring(0, returnModifiedStartDateInApplication(years_of_100).length - 1);
            end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
            status_value = value;
            break;
        case 'all_active':
            getAllActiveApplicationAllTime();
            start_date_for_sort = returnModifiedStartDateInApplication(years_of_100).substring(0, returnModifiedStartDateInApplication(years_of_100).length - 1);
            end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
            status_value = value;
            break;

    }
}

function getCheckedDateFromBody(value) {
    for (let i = 0; i < value.length; i++) {
        if (value[i].checked) {
            return value[i].value;
        }
    }
}

function returnStartDateInApplication() {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate(), 0, 0, 1)).toISOString();
}

function returnEndDateInApplication() {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate(), 23, 59, 59)).toISOString();
}

function returnModifiedStartDateInApplication(date) {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate() - date, 0, 0, 0, 1)).toISOString();
}

function returnModifiedEndDateInApplication(date) {
    return new Date(Date.UTC(start.getFullYear(), start.getMonth(), start.getDate() - date, 23, 59, 59, 59)).toISOString();
}

$(function () {
    $('input[id="btnradio_5_application"]').daterangepicker({
        opens: 'left',
        startDate: moment().startOf(start),
        endDate: moment().startOf(start),
        locale: {
            format: 'DD-MM-YYYY'
        }
    });
});

$('input[id="btnradio_5_application"]').on('apply.daterangepicker', function (ev, picker) {
    getAllApplicationByDate(picker.startDate.format('YYYY-MM-DDTHH:mm:ss.sss'), picker.endDate.format('YYYY-MM-DDTHH:mm:ss.sss'), status_value);
    document.getElementById('btnradio5_in_application_text').innerText = picker.startDate.format('DD.MM.YYYY') + ' - ' + picker.endDate.format('DD.MM.YYYY');
});

function parseDateByFormat(date) {
    return date[0] + "-" + calcTimeInLoop(date[1]) + "-" + calcTimeInLoop(date[2]) + "T" + calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function parseDateOnlyTime(date) {
    return calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function parseDateToLocalDate(date) {
    return date[0] + "-" + calcTimeInLoop(date[1]) + "-" + calcTimeInLoop(date[2]);
}

function calcTimeInLoop(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}

function deleteAllApplication() {
    deleteElemInLoop(document.getElementById('tdsBlock').querySelectorAll('tr'));
}

function cleanBlockInner() {
    deleteElemInLoop(document.getElementById('blockForLogsInApplication').querySelectorAll('p'));
}

function deleteItems_v_2() {
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}

function deleteItems_v_3() {
    deleteElemInLoop(document.getElementById('btnForSaveInApplication').querySelectorAll('span'));
}

function deleteItems_v_4() {
    deleteElemInLoop(document.getElementById('new_product').querySelectorAll('option'));
}

function cleanTextareaValue() {
    document.getElementById('textareaValue').value = "";
}

function deleteAllTaskElement() {
    document.querySelectorAll(".taskBlockInApplication").forEach(item => item.remove());
    document.getElementById('deadline_for_task_in_application').value = "";
    document.getElementById("taskTypeChoose").value = 3;
    document.getElementById("textareaValue").value = "";
}

function deleteElemInLoop(a) {
    for (let i = 0; i < a.length; i++) {
        a[i].remove();
    }
}