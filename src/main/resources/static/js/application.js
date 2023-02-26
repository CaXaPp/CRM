'use strict';
const BASE_URL = "http://localhost:9000";
let date = "232323";
let years_of_100 = 36500;
let employee_id = 0;
let application_id = 0;
let department_id = 0;
let product_id = 0;
let funnel_id;
let status_id_for_body = 0;
let check = true;
let start = new Date();
let start_date_for_sort = returnModifiedStartDateInApplication(years_of_100).substring(0, returnModifiedStartDateInApplication(years_of_100).length - 1);
let end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
document.getElementById('modal_edit_form').addEventListener('submit', updateEditedApplication);
document.getElementById('updateAddedTask').addEventListener('submit', updateAddedTask);

document.addEventListener("DOMContentLoaded", function () {
    getMainRoleToUser();
    document.getElementById('btnradio5_in_application_text').innerText = start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear() + "-" + start.getDate() + "." + start.getMonth() + 1 + "." + start.getFullYear();
})

// Getter functions

function getMainRoleToUser() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role.value === 'ROLE_EMPLOYEE') {
            getAllApplicationForUser();
        } else {
            getAllApplication();
        }
    })
}

function getAllApplicationForUser() {
    axios.get(BASE_URL + "/application/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBodyForUser(response.data[i]);
        }
    })
}

function getAllApplication() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
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
            if (response.data[i].id !== id) {
                switchOptionEmployeeSelect(response.data[i]);
            } else if (response.data[i].id === 0) {
                switchOptionEmployeeSelect(response.data[i]);
            }
        }
    })
}

function getUserById(id) {
    axios.get(BASE_URL + "/users/user/" + id).then(function (response) {
        deleteItems_v_2();
        getProducts(response.data.department.id);
        getStatuses(response.data.department.id);
        getFunnels(response.data.department.id);
    })
}

function getProducts(id) {
    axios.get(BASE_URL + "/products/product/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== product_id) {
                switchOptionProductSelect(response.data[i]);
            } else if (response.data[i].id === 0) {
                switchOptionProductSelect(response.data[i]);
            }
        }
    })
}

function getStatuses(id) {
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== status_id_for_body) {
                switchOptionStatusSelect(response.data[i]);
            } else if (response.data[i].id === 0) {
                switchOptionStatusSelect(response.data[i]);
            }
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

function getNameByEmail(email) {
    if (email === "anonymousUser") {
        return "AnonymousUser";
    }
    axios.get(BASE_URL + "/users/email/" + email).then(function (response) {
        return response.data;
    })
}

function getMessage() {
    let msg = document.getElementById('alert_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function applicationEditModalWindow(id) {
    axios
        .get(BASE_URL + '/application/edit/' + id).then(function (response) {
        console.log(response.data);
        application_id = id;
        cleanTextareaValue();
        deleteItems();

        fillFields(response);

        getLogsForApplication(id);
        getTaskType();
        getTasks(id);
        getProducts(response.data.product.department.id);
        getStatuses(response.data.product.department.id);
        getFunnels(response.data.product.department.id);
    });
}

function getAllApplicationByDate(date_start, date_end) {
    start_date_for_sort = date_start;
    end_date_for_sort = date_end;
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-date", {
        params: {
            startDate: date_start,
            endDate: date_end
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
    })
}

// Update functions

function updateEditedApplication(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/edit", {
        method: 'PUT',
        body: data
    })
        .then((response) => {
            deleteAllApplication();
            getAllApplication();
            getLogsForApplication(application_id);
            console.log('Success:', response.json())
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
        .then((response) => {
            getTasks(application_id);
            console.log('Success:', response.json())
        })
        .catch((error) => {
            console.log('Error:', error);
        })
}

function updateContentForApplication() {
    getUserById(document.getElementById('employee').value);
}

//Sort functions

function sortFunctionByApplicationCompany() {
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/by-company", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort
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
            end: end_date_for_sort
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
            end: end_date_for_sort
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
            end: end_date_for_sort
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
            end: end_date_for_sort
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })
}

$('#search_data_input').on("input",function(ev){
    deleteAllApplication();
    axios.get(BASE_URL + "/application/sort/find-by-company", {
        params: {
            start: start_date_for_sort,
            end: end_date_for_sort,
            text: ($(ev.target).val())
        }
    }).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
        (check === false) ? check = true : check = false;
    })

});

// Creation functions

function createTdElemOnBodyForUser(response) {
    let employee = (response.employee !== null) ? response.employee.firstName + " " + response.employee.surname : "Не назначено";
    let price = (response.price !== null) ? response.price : 0;

    document.getElementById('tdsBlock').insertAdjacentHTML('beforeend',
        '<tr>' +
        '<td>' +
        '<button type="button" class="' + createClassForBtn(response.status.name) + '">' + response.id + '</button>' +
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

function createTdElemOnBody(response) {
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
        document.getElementById('blockForLogsInApplication').insertAdjacentHTML('beforeend',
            '<p class="blockForLogsInApplication_main_date"><span>' + parseDateToLocalDate(response.date) + '</span></p>');
        elementCreationCycle(response);
        date = parseDateToLocalDate(response.date);
    } else {
        elementCreationCycle(response);
        date = parseDateToLocalDate(response.date);
    }
}

function elementCreationCycle(response) {
    for (let i = 0; i < response.changes.length; i++) {
        document.getElementById('blockForLogsInApplication').insertAdjacentHTML('beforeend',
            '<p>' + parseDateOnlyTime(response.date) + " " + response.author + " Для поля " + '<span class="badge rounded-pill bg-warning text-dark">' +
            '' + response.changes[i].property + '</span>' + " установлено значение " + '<span class="badge rounded-pill bg-info text-dark">' + response.changes[i].newRecord + '</span>' + '</p>'
        )
    }
}

function createTasksInApplication(response) {
    document.getElementById('log_block_footer').insertAdjacentHTML('afterbegin',
        '<div class="taskBlockInApplication">' +
        '<p>' + parseDateByFormat(response.createdAt) + " для " + response.employee.firstName + " " + response.employee.surname + '</p> ' +
        ' <p><span class="alert alert-success">' + response.type.name + '</span>' + " - " + response.application.name + " дата: " + parseDateByFormat(response.deadline) + '</p>' +
        '</div>'
    )
}

function fillFields(response) {
    document.getElementById('modal_title_from_js').innerText = "Заявление # " + response.data.id;
    document.getElementById('id').value = response.data.id;
    document.getElementById('company').value = response.data.company;
    document.getElementById('price').value = response.data.price;
    document.getElementById('name').value = response.data.name;
    document.getElementById('phone').value = response.data.phone;
    document.getElementById('email').value = response.data.email;
    document.getElementById('address').value = response.data.address;
    document.getElementById('hiddenApplicationId').value = response.data.id;
    document.getElementById('createdAt').value = parseDateByFormat(response.data.created_at);
    document.getElementById('sourceId').value = response.data.source.id;
    document.getElementById('operationId').value = response.data.id;
    document.getElementById('departmentInApplication').value = response.data.product.department.name;
    document.getElementById('productId').insertAdjacentHTML('beforeend',
        '<option selected value="' + response.data.product.id + '">' + response.data.product.name + '</option>');
    document.getElementById('status').insertAdjacentHTML('beforeend',
        '<option value="' + response.data.status.id + '" selected>' + response.data.status.name + '</option>');

    if (response.data.employee === null) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        getUsers(0);
    } else {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.data.employee.id + '">' + response.data.employee.firstName + " " + response.data.employee.surname + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.data.employee.id + '">' + response.data.employee.firstName + " " + response.data.employee.surname + '</option>'
        );
        getUsers(response.data.employee.id);
    }
}

function switchOptionEmployeeSelect(employee) {
    document.getElementById('employee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
    document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
}

function switchOptionProductSelect(product) {
    document.getElementById('productId').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
}

function switchOptionStatusSelect(status) {
    document.getElementById('status').insertAdjacentHTML('beforeend',
        '<option value="' + status.id + '">' + status.name + '</option>')
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
    setTimeout(function () {
        let date = document.getElementById('input_group_application').querySelectorAll('input');
        switch (getCheckedDateFromBody(date)) {
            case 'today':
                getAllApplicationByDate(returnStartDateInApplication().substring(0, returnStartDateInApplication().length - 1), returnEndDateInApplication().substring(0, returnStartDateInApplication().length - 1));
                break;
            case 'yesterday':
                getAllApplicationByDate(returnModifiedStartDateInApplication(1).substring(0, returnModifiedStartDateInApplication(1).length - 1), returnModifiedEndDateInApplication(1).substring(0, returnModifiedEndDateInApplication(1).length - 1));
                break;
            case 'week':
                getAllApplicationByDate(returnModifiedStartDateInApplication(7).substring(0, returnModifiedStartDateInApplication(7).length - 1), returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1));
                break;
            case 'month':
                getAllApplicationByDate(returnModifiedStartDateInApplication(30).substring(0, returnModifiedStartDateInApplication(30).length - 1), returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1));
                break;
            case 'infinity':
                getAllApplication();
                start_date_for_sort = returnModifiedStartDateInApplication(years_of_100).substring(0, returnModifiedStartDateInApplication(years_of_100).length - 1);
                end_date_for_sort = returnEndDateInApplication().substring(0, returnEndDateInApplication().length - 1);
                break;
        }
    }, 100);
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
    getAllApplicationByDate(picker.startDate.format('YYYY-MM-DDTHH:mm:ss.sss'), picker.endDate.format('YYYY-MM-DDTHH:mm:ss.sss'));
    document.getElementById('btnradio5_in_application_text').innerText = picker.startDate.format('DD.MM.YYYY') + ' - ' + picker.endDate.format('DD.MM.YYYY');
});

// parsing functions

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

// cleaning functions

function deleteAllApplication() {
    deleteElemInLoop(document.getElementById('tdsBlock').querySelectorAll('tr'));
}

function cleanBlockInner() {
    deleteElemInLoop(document.getElementById('blockForLogsInApplication').querySelectorAll('p'));
}

function deleteItems() {
    deleteElemInLoop(document.getElementById('employee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskForEmployee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskTypeChoose').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}

function deleteItems_v_2() {
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
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