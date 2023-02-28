'use strict'

let funnel_id = 1;
let application_id;
let date = "232323";
let employee;
let product;
let statusIdForPut;
let outputStatusId;

document.addEventListener("DOMContentLoaded", function () {
    getRoleForFunnels();
    getStatusesByFunnelId(funnel_id);
    getAllActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
})

document.getElementById('addNewApplicationInOperationSource').addEventListener('submit', addNewApplicationInOperation);
document.getElementById('modal_edit_form').addEventListener('submit', updateApplication);
document.getElementById('updateAddedTask').addEventListener('submit', updateAddedTask);

// Getters

function getApplicationById(e) {
    application_id = e;
    axios
        .get(BASE_URL + '/application/edit/' + e).then(function (response) {
        cleanTextareaValue();
        deleteItems();

        fillFields(response.data);

        getLogsForApplication(e);
        getTaskType();
        getTasks(e);

        getFunnels(response.data.product.department.id);
        getProducts(response.data.product.department.id, response.data.product.id);
        getStatuses(response.data.product.department.id, response.data.status.id);
    });
}

function getProducts(id, productId) {
    axios.get(BASE_URL + "/products/product/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionProductSelect(response.data[i], productId);
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

function getUsers(userId) {
    axios.get(BASE_URL + "/users/not-admin").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            switchOptionEmployeeSelect(response.data[i], userId);
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

function uploadApplicationData() {
    deleteAllFreeApplication();
    axios.get(BASE_URL + "/application/all-free").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createListOfFreeApplication(response.data[i]);
            getStatusIdFromFunnel(response.data[i].id, response.data[i].status.name);
        }
    })
}

function getStatusIdFromFunnel(id, status) {
    axios.get(BASE_URL + "/statuses/funnels-status", {
        params: {
            status: status,
            funnel: document.getElementById('funnels').value
        }
    }).then(function (response) {
        setTimeout(function () {
            document.getElementById('setNewValueForStatusFromFunnel' + id).value = response.data;
        }, 300);
    })
}

function getForm(e) {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        document.getElementById('freeApplEmpl' + e).value = response.data.id;
        let token = document.getElementById('x-csrf-token-2');
        document.getElementById('freeApplToken' + e).value = token.value;
        document.getElementById('freeApplToken' + e).name = token.name;
    })
}

function getStatusesByFunnelId(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/statuses/status/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createStatusBlock(response.data[i]);
        }
        createBtnForAddNewApplication(response.data[0].id);
    })
}

function getProductsForNewApplication() {
    deleteItems_v_4();
    axios.get(BASE_URL + "/products/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createProductSelect(response.data[i]);
        }
    })
}

function getAllApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/funnel/all/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function getAllActiveApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/funnel/active/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function getAllNotActiveApplication(id) {
    deleteAllApplication();
    axios.get(BASE_URL + "/operations/funnel/not-active/" + id).then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createTableInBody(response.data[i]);
        }
    })
}

function getRoleForFunnels() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role === 'Сотрудник') {
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
    });
}

function mainSwitchOptionFunnelsSelect(funnel) {
    document.getElementById('funnels').insertAdjacentHTML('beforeend',
        '<option value="' + funnel.id + '">' + funnel.name + '</option>');
}

function getEmployeeForNewApplication() {
    axios.get(BASE_URL + "/users/not-admin").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createEmployeeSelect(response.data[i]);
        }
    })
}

function getPrice(price) {
    if (price !== null) {
        return price;
    } else {
        return 0;
    }
}

function getDataToNewApplication() {
    setTimeout(function () {
        getProductsForNewApplication();
        setUserValueForNewApplication();
    }, 300);
}

// Set/add/update functions

function addNewApplicationInOperation(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/save", {
        method: 'POST',
        body: data
    })
        .then((response) => {
            console.log(response);
            getRoleForFunnels();
            getStatusesByFunnelId(funnel_id);
            getAllActiveApplication(funnel_id);
            setTimeout(function () {
                dragAndDropFunc();
            }, 1000);
            document.getElementById('close_btn_new_app2').click();
            getMessageForCreate();
        })
        .catch((error) => {
            console.log('Error:', error);
        });
}

function setUserValueForNewApplication() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role === 'Сотрудник') {
            document.getElementById('employeeInNewApplication').value = response.data.id;
        } else {
            document.getElementById('employeeInNewApplication').remove();
            document.getElementById('add_new_application').insertAdjacentHTML('beforeend',
                '<div class="information_field">' +
                '<label for="employeeSwitchInNewApplication" style="width: 40%">Сотрудник</label>' +
                '<select class="form-select" aria-label="Select employee" id="employeeSwitchInNewApplication" name="employeeId" style="width: 60%" required></select>' +
                '</div>'
            );
            getEmployeeForNewApplication();
        }
    })
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
            setTimeout(function () {
                dragAndDropFunc();
            }, 1000);
        })
}

function updateFreeApplication(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    fetch(BASE_URL + "/application/edit", {
        method: 'PUT',
        body: data
    })
        .then((response) => {
            document.getElementById('freeApplicationFrom' + form.id.value).remove();
            document.getElementById('spinner_in_operations').setAttribute('style', 'display:flex !important');
            getStatusesByFunnelId(document.getElementById('funnels').value);
            getAllActiveApplication(document.getElementById('funnels').value);
            setTimeout(function () {
                dragAndDropFunc();
            }, 1000);
            getMessage();
            console.log(response)
        })
        .catch((error) => {
            console.log('Error:', error);
        });
}

function setIdForForm(e) {
    document.getElementById('freeApplicationFrom' + e).addEventListener('submit', updateFreeApplication);
}

// Changes

function changeToActiveApplication() {
    getStatusesByFunnelId(funnel_id);
    document.getElementById('spinner_in_operations').setAttribute('style', 'display:flex !important');
    getAllActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function changeToAllApplication() {
    getStatusesByFunnelId(funnel_id);
    document.getElementById('spinner_in_operations').setAttribute('style', 'display:flex !important');
    getAllApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function changeToNotActiveApplication() {
    getStatusesByFunnelId(funnel_id);
    document.getElementById('spinner_in_operations').setAttribute('style', 'display:flex !important');
    getAllNotActiveApplication(funnel_id);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
}

function changeFunnel(e) {
    document.getElementById('spinner_in_operations').setAttribute('style', 'display:flex !important');
    funnel_id = e;
    getStatusesByFunnelId(e);
    getAllActiveApplication(e);
    setTimeout(function () {
        dragAndDropFunc();
    }, 1000);
    document.getElementById('switch1').checked = true;
}

function changeInformationAboutDeals(id, price) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('sum_application_in_column' + id).textContent;

    document.getElementById('count_application_in_column' + id).innerText = (parseInt(elem1) + 1).toString();
    document.getElementById('sum_application_in_column' + id).innerText = (parseFloat(elem2) + price).toFixed(2);

    createDealNames(id);
}

function changeInformationTextAboutDeal(id, price) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('sum_application_in_column' + id).textContent;

    document.getElementById('count_application_in_column' + id).innerText = (parseInt(elem1) - 1).toString();
    document.getElementById('sum_application_in_column' + id).innerText = parseFloat((parseFloat(elem2) - price).toFixed(2));

    createDealNames(id);
}

// Create functions

function createBtnForAddNewApplication(id) {
    let elem = document.getElementById('column-list-to-drag' + id);
    elem.style.paddingTop = "60px";
    elem.style.position = "relative";
    document.getElementById('column-list-to-drag' + id).insertAdjacentHTML('beforeend',
        '<button class="addNewApplicationBtn" data-bs-toggle="modal" data-bs-target="#addNewApplicationInOperationSource" onclick="getDataToNewApplication()">Быстрое добавление</button>'
    )
}

function createProductSelect(product) {
    document.getElementById('new_product').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
}

function createEmployeeSelect(employee) {
    document.getElementById('employeeSwitchInNewApplication').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
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

function createTableInBody(response) {
    document.getElementById('spinner_in_operations').setAttribute('style', 'display:none !important');
    document.getElementById('column-list-to-drag' + response.status.id).insertAdjacentHTML('beforeend',
        '<div class="item-list-to-drag" id="application' + response.id + '" draggable="true" onclick="getApplicationById(' + response.id + ')" data-bs-toggle="modal" data-bs-target="#exampleModal2">' +
        '<input type="text" value="Заявление № ' + response.id + '">' +
        '<p>' + response.company + '</p>' +
        '<p class="badge bg-success">' + createPriceIfNull(response.price) + '</p>' +
        '<div class="item-list-to-drag_inner_div">' +
        '<div><p class="badge bg-light text-dark">' + response.employee.firstName + " " + response.employee.surname + '</p></div>' +
        '<div><p>' + parseDateToLocalDate(response.created_at) + '</p></div>' +
        '</div>' +
        '</div>'
    )
    changeInformationAboutDeals(response.status.id, response.price);
}

function createPriceIfNull(response) {
    if (response === null) {
        return 0;
    } else {
        return response;
    }
}

function createDealNames(id) {
    let elem1 = document.getElementById('count_application_in_column' + id).textContent;
    let elem2 = document.getElementById('count_application_in_column_text' + id);
    if (parseInt(elem1) === 1) {
        elem2.innerText = "сделка:";
    } else if (parseInt(elem1) >= 2 && parseInt(elem1) <= 4) {
        elem2.innerText = "сделки:";
    } else if (parseInt(elem1) >= 5) {
        elem2.innerText = "сделок:";
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

function createListOfFreeApplication(response) {
    document.getElementById('freeApplicationList').insertAdjacentHTML("afterbegin",
        '<form id="freeApplicationFrom' + response.id + '" class="freeApplicationForm">' +
        '<input name="id" class="input-inner-free-id" value="' + response.id + '" placeholder="' + response.id + '" readonly>' +
        '<input type="text" class="input-inner-free-com" name="company" value="' + response.company + '" placeholder="Компания" readonly>' +
        '<input type="number" class="input-inner-free-price" name="price" value="' + getPrice(response.price) + '" placeholder="0" readonly>' +
        '<input type="hidden" name="productId" value="' + response.product.id + '">' +
        '<input type="text" class="input-inner-free-name" name="name" value="' + response.name + '" placeholder="Представитель" readonly>' +
        '<input type="hidden" name="statusId" id="setNewValueForStatusFromFunnel' + response.id + '" value="' + response.status.id + '">' +
        '<input type="hidden" name="phone" value="' + response.phone + '">' +
        '<input type="hidden" name="email" value="' + response.email + '">' +
        '<input type="hidden" name="address" value="' + response.address + '">' +
        '<input type="hidden" name="employeeId" id="freeApplEmpl' + response.id + '" value="">' +
        '<input type="hidden" name="sourceId" value="' + response.source.id + '">' +
        '<input type="hidden" name="description" value="' + response.description + '">' +
        '<input type="hidden" name="" value="" id="freeApplToken' + response.id + '">' +
        '<button type="submit" class="btn btn-primary btn-inner-free" value="' + response.id + '" form="freeApplicationFrom' + response.id + '" onclick="setIdForForm(this.value)">Забрать</button>' +
        '</form>'
    )
    getForm(response.id);
}

function elementCreationCycle(response) {
    for (let i = 0; i < response.changes.length; i++) {
        document.getElementById('blockForLogsInApplication').insertAdjacentHTML('beforeend',
            '<p>' + parseDateOnlyTime(response.date) + " " + response.author.firstName + " " + response.author.surname + " Для поля " + response.changes[i].property + " установлено значение " + response.changes[i].newRecord + '</p>'
        )
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
    document.getElementById('createdAt').value = parseDateByPostgreFormat(response.created_at);
    document.getElementById('sourceId').value = response.source.id;
    document.getElementById('operationId').value = response.id;
    document.getElementById('departmentInApplication').value = response.product.department.name;

    if (response.description === "null") {
        document.getElementById('applicationTextarea').innerText = "";
    } else {
        document.getElementById('applicationTextarea').innerText = response.description;
    }

    if (response.employee === null) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
        getUsers(0);
    } else {
        getUsers(response.employee.id);
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option value="' + response.employee.id + '">' + response.employee.fio + '</option>'
        );
    }
}

function switchOptionEmployeeSelect(employee, userId) {
    if (employee.id === userId) {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + employee.id + '">' + employee.fio + '</option>'
        );
    } else {
        document.getElementById('employee').insertAdjacentHTML('beforeend',
            '<option value="' + employee.id + '">' + employee.fio + '</option>'
        );
    }
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

// msg functions

function getMessageForCreate() {
    let msg = document.getElementById('alert_new_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function deleteItems_v_4() {
    deleteElemInLoop(document.getElementById('new_product').querySelectorAll('option'));
}

function deleteAllApplication() {
    deleteElemInLoop(document.getElementById('operations_block').querySelectorAll('div'));
}

function getMessageForChangeStatus() {
    let msg = document.getElementById('alert_status');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function getMessage() {
    let msg = document.getElementById('alert_new_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

// Drag and Drop function

document.addEventListener("dragstart", (e) => {
    e.target.classList.add("dragging");
    outputStatusId = e.target.parentNode.id;
});

document.addEventListener("dragend", (e) => {
    e.target.classList.remove("dragging");
    setNewStatus(e.target.firstChild.value, statusIdForPut, e.target.children[2].textContent);
});

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
        changeInformationAboutDeals(status_id, parseFloat(price));
        changeInformationTextAboutDeal(output_status_id, parseFloat(price));
    }).catch(function (error) {
        console.log(error);
    })
}

function uploadFunnelToOperation(e) {
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    getStatuses(e);
}

// Delete/parse/calc functions

function deleteItems_v_2() {
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}

function deleteAllFreeApplication() {
    deleteElemInLoop(document.getElementById('freeApplicationList').querySelectorAll('form'));
}

function deleteAllTaskElement() {
    document.querySelectorAll(".taskBlockInApplication").forEach(item => item.remove());
    document.getElementById('deadline_for_task_in_application').value = "";
    document.getElementById("taskTypeChoose").value = 3;
    document.getElementById("textareaValue").value = "";
}

function parseDateByFormat(date) {
    return date[0] + "." + calcTimeInLoop(date[1]) + "." + calcTimeInLoop(date[2]) + " T" + calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function parseDateByPostgreFormat(date) {
    return date[0] + "-" + calcTimeInLoop(date[1]) + "-" + calcTimeInLoop(date[2]) + "T" + calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function calcTimeInLoop(time) {
    if (time < 10) {
        return "0" + time;
    } else {
        return time;
    }
}

function parseDateOnlyTime(date) {
    return calcTimeInLoop(date[3]) + ":" + calcTimeInLoop(date[4]);
}

function parseDateToLocalDate(date) {
    return date[0] + "." + calcTimeInLoop(date[1]) + "." + calcTimeInLoop(date[2]);
}

function cleanBlockInner() {
    deleteElemInLoop(document.getElementById('blockForLogsInApplication').querySelectorAll('p'));
}

function deleteElemInLoop(a) {
    for (let i = 0; i < a.length; i++) {
        a[i].remove();
    }
}

function cleanTextareaValue() {
    document.getElementById('textareaValue').value = "";
}