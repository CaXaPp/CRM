'use strict'

const BASE_URL = 'http://localhost:9000';
let funnel_id = 1;
let application_id;

document.addEventListener("DOMContentLoaded", function () {
    getRoleForFunnels();
    getStatusesByFunnelId(funnel_id);
    getAllActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
})

let date = "232323";
let employee;
let product;
let statusId;
let statusIdForPut;
let outputStatusId;
let statusName;

function changeApplicationAll() {
    getRoleForFunnels();
    getStatusesByFunnelId(funnel_id);
    getAllActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function changeApplication() {
    getRoleForFunnels();
    getStatusesByFunnelId(funnel_id);
    getAllApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function changeApplication2() {
    getRoleForFunnels();
    getStatusesByFunnelId(funnel_id);
    getAllNotActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function getStatusesByFunnelId(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createStatusBlock(response.data[i]);
        }
    })
}

function createStatusBlock(response) {
    document.getElementById('operations_block').insertAdjacentHTML('beforeend',
        '<div class="block_list_to_drag" id="block_list_to_drag">' +
        '<div class="block_list_to_drag_inner_status">' +
        '<p style="font-weight: bold">' + response.name + '</p>' +
        '<div class="statistic_inner_status_block">' +
        '<div>' +
        '<p id="count_application_in_column' + response.id + '">0 </p>' +
        '<p id="count_application_in_column_text' + response.id + '">сделок: </p>' +
        '</div>' +
        '<div>' +
        '<p id="sum_application_in_column' + response.id + '">0</p>' +
        '<p> сом</p>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '<div id="column-list-to-drag' + response.id + '" class="column-list-to-drag">' +
        '</div>' +
        '</div>'
    )
}

document.getElementById('modal_edit_form').addEventListener('submit', updateApplication);
document.getElementById('updateAddedTask').addEventListener('submit', updateAddedTask);

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

function updateApplication(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/edit", {
        method: 'PUT',
        body: data
    })
        .then(() => {
            deleteAllApplication();
            getStatusesByFunnelId(funnel_id);
            getAllActiveApplication(funnel_id);
            getLogsForApplication(application_id);
            setTimeout(function () {
                dragAndDropFunc();
            }, 1000);
        })
}

function changeFunnel(e) {
    funnel_id = e;
    getStatusesByFunnelId(e);
    getAllActiveApplication(e);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function getRoleForFunnels() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role.value === 'ROLE_EMPLOYEE') {
            getFunnelsMain(response.data.department.id);
        } else {
            getAllFunnels();
        }

    })
}

function getAllFunnels() {
    deleteElemInLoop(document.getElementById('funnels').querySelectorAll('option'));
    axios.get(BASE_URL + "/funnels/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            mainSwitchOptionFunnelsSelect(response.data[i]);
        }
    })
}

function getFunnelsMain(id) {
    deleteElemInLoop(document.getElementById('funnels').querySelectorAll('option'));
    axios.get(BASE_URL + "/funnels/funnel/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            mainSwitchOptionFunnelsSelect(response.data[i]);
        }
    })
}

function mainSwitchOptionFunnelsSelect(funnel) {
    document.getElementById('funnels').insertAdjacentHTML('beforeend',
        '<option value="' + funnel.id + '">' + funnel.name + '</option>')
}

function getAllApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/by-funnel-all/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function getAllActiveApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/by-funnel-active/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function getAllNotActiveApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/by-funnel-not-active/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function deleteAllApplication() {
    deleteElemInLoop(document.getElementById('operations_block').querySelectorAll('div'));
}

function createTableInBody(response) {
    document.getElementById('column-list-to-drag' + response.status.id).insertAdjacentHTML('beforeend',
        '<div class="item-list-to-drag" id="application' + response.id + '" draggable="true" onclick="getApplicationById(' + response.id + ')" data-bs-toggle="modal" data-bs-target="#exampleModal2">' +
        '<input type="text" value="Заявление № ' + response.id + '">' +
        '<p>' + response.company + '</p>' +
        '<p class="badge bg-success">' + response.price + '</p>' +
        '<p class="badge bg-light text-dark">' + response.employee.firstName + " " + response.employee.surname + '</p>' +
        '</div>'
    )

    changeInformationAboutDeals(response.status.id, response.price);
}

function changeInformationAboutDeals(id, price) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('sum_application_in_column' + id).textContent;

    document.getElementById('count_application_in_column' + id).innerText = (parseInt(elem1) + 1).toString();
    document.getElementById('sum_application_in_column' + id).innerText = (parseInt(elem2) + price).toString();

    createDealNames(id);
}

function changeInformationTextAboutDeal(id, price) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('sum_application_in_column' + id).textContent;

    document.getElementById('count_application_in_column' + id).innerText = (parseInt(elem1) - 1).toString();
    document.getElementById('sum_application_in_column' + id).innerText = (parseInt(elem2) - price).toString();

    createDealNames(id);
}

function createDealNames(id) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('count_application_in_column_text' + id);
    if (parseInt(elem1) === 1) {
        elem2.innerText = "сделка:";
    } else if(parseInt(elem1) >= 2 && parseInt(elem1) <= 4) {
        elem2.innerText = "сделки:";
    } else if (parseInt(elem1) >= 5) {
        elem2.innerText = "сделок:";
    }
}

function dragAndDropFunc() {
    const columns = document.querySelectorAll(".column-list-to-drag");
    columns.forEach((item) => {
        item.addEventListener("dragover", (e) => {
            const dragging = document.querySelector(".dragging");
            const applyAfter = getNewPosition(item, e.clientY);
            if (applyAfter) {
                applyAfter.insertAdjacentElement("afterend", dragging);
                statusIdForPut = item.id;
            } else {
                item.prepend(dragging);
                statusIdForPut = item.id;
            }
        });
    });
}

document.addEventListener("dragstart", (e) => {
    e.target.classList.add("dragging");
    outputStatusId = e.target.parentNode.id;
});

document.addEventListener("dragend", (e) => {
    e.target.classList.remove("dragging");
    setNewStatus(e.target.firstChild.value, statusIdForPut, e.target.children[2].textContent);
});


function getNewPosition(column, posY) {
    const cards = column.querySelectorAll(".item-list-to-drag:not(.dragging)");
    let result;

    for (let refer_card of cards) {
        const box = refer_card.getBoundingClientRect();
        const boxCenterY = box.y + box.height / 2;

        if (posY >= boxCenterY) result = refer_card;
    }

    return result;
}

function setNewStatus(applicationId, newStatusId, price) {
    let application_id = applicationId.replace(/[^+\d]/g, '');
    let status_id = newStatusId.replace(/[^+\d]/g, '');
    let output_status_id = outputStatusId.replace(/[^+\d]/g, '');
    axios.get(BASE_URL + '/application/set-status', {
        params: {
            application: application_id,
            status: status_id
        }
    }).then(function () {
        getMessageForChangeStatus();
        changeInformationAboutDeals(status_id, parseInt(price));
        changeInformationTextAboutDeal(output_status_id, parseInt(price));
    }).catch(function (error) {
        console.log(error);
    })
}

function getApplicationById(e) {
    application_id = e;
    axios
        .get(BASE_URL + '/application/edit/' + e).then(function (response) {
        cleanTextareaValue();
        deleteItems();

        fillModal(response.data);

        getLogsForApplication(e);
        getTaskType();
        getTasks(e);
        getProducts(response.data.product.department.id);
        getStatuses(response.data.status.funnel.id);
        getFunnels(response.data.product.department.id);
    });
}

function cleanTextareaValue() {
    document.getElementById('textareaValue').value = "";
}

function deleteItems() {
    deleteElemInLoop(document.getElementById('employee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskForEmployee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskTypeChoose').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}

function getProducts(id) {
    axios.get(BASE_URL + "/products/product/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== product) {
                switchOptionProductSelect(response.data[i]);
            }
        }
    })
}

function switchOptionProductSelect(product) {
    document.getElementById('productId').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
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

function parseDateOnlyTime(date) {
    return calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function parseDateToLocalDate(date) {
    return date[0] + "-" + calcTimeInLoop(date[1]) + "-" + calcTimeInLoop(date[2]);
}

function cleanBlockInner() {
    deleteElemInLoop(document.getElementById('blockForLogsInApplication').querySelectorAll('p'));
}

function deleteElemInLoop(a) {
    for (let i = 0; i < a.length; i++) {
        a[i].remove();
    }
}

function fillModal(response) {
    employee = response.employee.id;
    product = response.product.id;
    statusId = response.status.id;

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
    document.getElementById('sourceId').value = response.source.id;
    document.getElementById('operationId').value = response.id;
    document.getElementById('departmentInApplication').value = response.product.department.name;
    document.getElementById('productId').insertAdjacentHTML('beforeend',
        '<option selected value="' + response.product.id + '">' + response.product.name + '</option>');
    document.getElementById('status').insertAdjacentHTML('beforeend',
        '<option value="' + response.status.id + '" selected>' + response.status.name + '</option>');

    if (response.employee === null) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        getUsers(0);
    } else {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.employee.id + '">' + response.employee.firstName + " " + response.employee.surname + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.employee.id + '">' + response.employee.firstName + " " + response.employee.surname + '</option>'
        );
        getRole(response.employee.id);
    }
}

function getRole(id) {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role.value !== 'ROLE_EMPLOYEE') {
            getUsers(id);
        } else {
            document.getElementById('funnelInApplication').setAttribute('disabled', 'true');
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

function switchOptionEmployeeSelect(employee) {
    document.getElementById('employee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
    document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
}

function calcTimeInLoop(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}

function getStatuses(id) {
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== statusId) {
                switchOptionStatusSelect(response.data[i]);
            }
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

function getTaskType() {
    axios.get(BASE_URL + "/task-type/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTaskTypeOption(response.data[i]);
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

function createTasksInApplication(response) {
    document.getElementById('log_block_footer').insertAdjacentHTML('afterbegin',
        '<div class="taskBlockInApplication">' +
        '<p>' + parseDateByFormat(response.createdAt) + " для " + response.employee.firstName + " " + response.employee.surname + '</p> ' +
        ' <p><span class="alert alert-success">' + response.type.name + '</span>' + " - " + response.application.name + " дата: " + parseDateByFormat(response.deadline) + '</p>' +
        '</div>'
    )
}

function parseDateByFormat(date) {
    return date[0] + "-" + calcTimeInLoop(date[1]) + "-" + calcTimeInLoop(date[2]) + "T" + calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function deleteAllTaskElement() {
    document.querySelectorAll(".taskBlockInApplication").forEach(item => item.remove());
    document.getElementById('deadline_for_task_in_application').value = "";
    document.getElementById("taskTypeChoose").value = 3;
    document.getElementById("textareaValue").value = "";
}

function getMessageForChangeStatus() {
    let msg = document.getElementById('alert_status');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function getMessage() {
    let msg = document.getElementById('alert_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function updateContentForApplication() {
    getUserById(document.getElementById('employee').value);
}

function getUserById(id) {
    axios.get(BASE_URL + "/users/user/" + id).then(function (response) {
        deleteItems_v_2();
        getProducts(response.data.department.id);
        getStatuses(response.data.status.funnel.id);
        getFunnels(response.data.department.id);
    })
}

function deleteItems_v_2() {
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}