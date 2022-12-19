// Примечание: удалить все комментарии после выполнения данного раздела
'use strict';
const BASE_URL = "http://localhost:9000";

// Загрузка данных из БД

document.addEventListener("DOMContentLoaded", function () {
    getAllApplication();
})

async function getAllApplication() {
    await axios.get(BASE_URL + "/application/all").then(function (response) {

        for (let i = 0; i < response.data.length; i++) {
            createTdElemOnBody(response.data[i]);
        }
    })
}

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
        '<button type="button" class="btn btn-primary" id="modalBtn"' +
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

// При клике на id заявления открывается соответсвующее модальное окно для редактирования
// и заполняется данными из БД

let employee_id = 0;

function applicationEditModalWindow(id) {
    axios
        .get(BASE_URL + '/application/edit/' + id).then(function (response) {
        deleteItems();
        fillFields(response);
        getUsers(employee_id);
        getProducts(response.data.product.id);
        getStatuses(response.data.status.id);

    });
}

function fillFields(response) {
    document.getElementById('modal_title_from_js').innerText = "Заявление # " + response.data.id;
    document.getElementById('modal_company').value = response.data.company;
    document.getElementById('modal_product').insertAdjacentHTML('beforeend',
        '<option selected value="' + response.data.product.id + '">' + response.data.product.name + '</option>');
    document.getElementById('modal_deal_sum').value = response.data.price;
    document.getElementById('modal_name').value = response.data.name;
    document.getElementById('modal_phone').value = response.data.phone;
    document.getElementById('modal_email').value = response.data.email;
    document.getElementById('modal_address').value = response.data.address;
    document.getElementById('hiddenApplicationId').value = response.data.id;
    document.getElementById('modal_status').insertAdjacentHTML('beforeend',
        '<option value="' + response.data.status.id + '" selected>' + response.data.status.name + '</option>');

    if (response.data.employee === null) {
        document.getElementById('modal_employee').insertAdjacentHTML('beforeend',
            '<option selected value="0">' + "--- Назначить ---" + '</option>'
        );
    } else {
        document.getElementById('modal_employee').insertAdjacentHTML('beforeend',
            '<option selected value="' + response.data.employee.id + '">' + response.data.employee.firstName + " " + response.data.employee.surname + '</option>'
        );
        employee_id = response.data.employee.id;
    }
}

function deleteItems() {
    let removeEmployeeElem = document.getElementById('modal_employee').querySelectorAll('option');
    let removeProductElem = document.getElementById('modal_product').querySelectorAll('option');
    let removeStatusElem = document.getElementById('modal_status').querySelectorAll('option');
    for (let i = 0; i < removeEmployeeElem.length; i++) {
        removeEmployeeElem[i].remove();
    }
    for (let i = 0; i < removeProductElem.length; i++) {
        removeProductElem[i].remove();
    }
    for (let i = 0; i < removeStatusElem.length; i++) {
        removeStatusElem[i].remove();
    }
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

function switchOptionEmployeeSelect(employee) {
    document.getElementById('modal_employee').insertAdjacentHTML('beforeend',
        '<option value="' + employee.id + '">' + employee.firstName + " " + employee.surname + '</option>'
    );
}

function getProducts(id) {
    axios.get(BASE_URL + "/products/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].id !== id) {
                switchOptionProductSelect(response.data[i]);
            }
        }
    })
}

function switchOptionProductSelect(product) {
    document.getElementById('modal_product').insertAdjacentHTML('beforeend',
        '<option value="' + product.id + '">' + product.name + '</option>')
}

function getStatuses(id) {
    axios.get(BASE_URL + "/statuses/all").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            if(response.data[i].id !== id) {
                switchOptionStatusSelect(response.data[i]);
            }
        }
    })
}

function switchOptionStatusSelect(status) {
    document.getElementById('modal_status').insertAdjacentHTML('beforeend',
        '<option value="' + status.id + '">' + status.name + '</option>')
}

// Сохранение изменений в БД

document.getElementById('modal_edit_form').addEventListener('submit', updateEditedApplication);

function updateEditedApplication(e) {
    e.preventDefault();
    const form = e.target;
    let data = new FormData(form);

    const id = document.getElementById('hiddenApplicationId').value;

    fetch(BASE_URL + "/application/edit/" + id, {
        method: 'POST',
        body: data
    })
        .then((response) => {
            deleteAllApplication();
            getAllApplication();
            console.log('Success:', response.json())
        })
        .catch((error) => {
            console.log('Error:', error);
        });
}

function getMessage() {
    let msg = document.getElementById('alert_msg');
    msg.hidden = false;
    setTimeout(function () {
        msg.hidden = true
    }, 2000);
}

function deleteAllApplication() {
    let removeApplication = document.getElementById('tdsBlock').querySelectorAll('tr');

    for (let i = 0; i < removeApplication.length; i++) {
        removeApplication[i].remove();
    }
}