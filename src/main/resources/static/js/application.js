'use strict';
const BASE_URL = "http://localhost:9000";
let date = "232323";
let employee_id = 0;
let application_id;
let department_id;
let product_id;
let funnel_id;

document.addEventListener("DOMContentLoaded", function () {
    getAllApplication();
    getMainRoleToUser();
})

function getMainRoleToUser() {
    console.log("start function");
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role.name === 'Сотрудник') {
            let btns = document.getElementById('tdsBlock').getElementsByTagName('button');
            setTimeout(function () {
                for (let i = 0; i < btns.length; i++) {
                    btns[i].disabled = true;
                }
            }, 100); // временное решение
        }
    })
}

// Getter functions

function getAllApplication() {
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
    axios.get(BASE_URL + "/users/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== id) {
                switchOptionEmployeeSelect(response.data[i]);
            }
        }
    })
}

function getUserById(id) {
    axios.get(BASE_URL + "/users/user/" + id).then(function (response) {
        deleteItems_v_2();
        // department_id = response.data.department.id;
        getProducts(response.data.department.id);
        getStatuses(response.data.department.id);
        getFunnels(response.data.department.id);
        // document.getElementById('departmentInApplication').value = response.data.department.name;
    })
}

function getProducts(id) {
    axios.get(BASE_URL + "/products/product/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== product_id) {
                switchOptionProductSelect(response.data[i]);
            }
        }
    })
}

function getStatuses(id) {
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== id) {
                switchOptionStatusSelect(response.data[i]);
            }
        }
    })
}

function getTasks(id) {
    deleteAllTaskElement();
    axios.get(BASE_URL + "/tasks/task/" + id).then(function (response) {
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

// Update functions

document.getElementById('modal_edit_form').addEventListener('submit', updateEditedApplication);
document.getElementById('updateAddedTask').addEventListener('submit', updateAddedTask);

async function applicationEditModalWindow(id) {
    await axios
        .get(BASE_URL + '/application/edit/' + id).then(function (response) {
            getLogsForApplication(id);
            application_id = id;
            cleanTextareaValue();

            deleteItems();
            fillFields(response);
            getUsers(employee_id);
            getFunnels(department_id);
            getProducts(department_id);
            getTaskType();
            getTasks(id);
            getStatuses(department_id); // Временное решение, должно быть id_funnel
        }).catch(function (error) {
            alert(error.code);
            window.location.replace(BASE_URL + "/users/login");
        });
}

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

// Creation functions

function createTdElemOnBody(response) {
    let employee_name;
    let price;
    if (response.employee !== null) {
        employee_name = response.employee.firstName + " " + response.employee.surname;
    } else {
        employee_name = "Не назначено";
    }
    if (response.price !== null) {
        price = response.price;
    } else {
        price = 0;
    }

    document.getElementById('tdsBlock').insertAdjacentHTML('beforeend',
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
        '<td>' + employee_name + '</td>' +
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
            '<p>' + parseDateOnlyTime(response.date) + " " + response.author + " Для поля " + response.changes[i].property + " установлено значение " + response.changes[i].newRecord + '</p>'
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
    document.getElementById('productId').insertAdjacentHTML('beforeend',
        '<option selected value="' + response.data.product.id + '">' + response.data.product.name + '</option>');
    document.getElementById('price').value = response.data.price;
    document.getElementById('name').value = response.data.name;
    document.getElementById('phone').value = response.data.phone;
    document.getElementById('email').value = response.data.email;
    document.getElementById('address').value = response.data.address;
    document.getElementById('hiddenApplicationId').value = response.data.id;
    document.getElementById('status').insertAdjacentHTML('beforeend',
        '<option value="' + response.data.status.id + '" selected>' + response.data.status.name + '</option>');
    document.getElementById('createdAt').value = parseDateByFormat(response.data.created_at);
    document.getElementById('sourceId').value = response.data.source.id;
    document.getElementById('operationId').value = response.data.id;
    document.getElementById('departmentInApplication').value = response.data.employee.department.name;
    // document.getElementById('funnelInApplication').insertAdjacentHTML('beforeend',
    //     '<option value="' + funnel_id + '" selected>' +  + '</option>')

    if (response.data.employee === null) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
    } else {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.data.employee.id + '">' + response.data.employee.firstName + " " + response.data.employee.surname + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.data.employee.id + '">' + response.data.employee.firstName + " " + response.data.employee.surname + '</option>'
        );
        employee_id = response.data.employee.id;
        department_id = response.data.employee.department.id;
        product_id = response.data.product.id;
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