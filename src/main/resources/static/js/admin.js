'use strict';

function getAllApplicationToAdminPanel() {
    deleteAllApplicationToAdminPanel();
    axios.get(BASE_URL + "/application/all-sort-by-id").then(function (response) {
        for (let i = 0; i < response.data.length; i++) {
            createElemsInAdminPanel(response.data[i]);
        }
    })
}

function createElemsInAdminPanel(response) {
    let employee = (response.employee !== null) ? response.employee.fio : "Не назначено";
    let product = (response.product !== null) ? response.product.name : "Не назначен";
    document.getElementById('bodyForDeleteInAdminPanel').insertAdjacentHTML('beforeend',
        '<tr id="deleteRow' + response.id + '">' +
        '<td>'  + response.id + '</td>' +
        '<td>' + response.company + '</td>' +
        '<td>' + product + '</td>' +
        '<td>' + response.name + '</td>' +
        '<td>' + response.email + '</td>' +
        '<td>' + response.status.name + '</td>' +
        '<td>' + employee + '</td>' +
        '<td>' + '<button class="btn btn-danger" onclick="deleteApplication(' + response.id + ')"><i class="fa-solid fa-trash"></i></button>' + '</td>' +
        '</tr>'
    )
}

function deleteApplication(e) {
    axios.delete(BASE_URL + "/application/delete", {
        params: {
            id: e
        },
        headers: {
            'X-CSRF-TOKEN': document.getElementById('x-csrf-token').value
        }
    })
        .then(function () {
            document.getElementById('deleteRow' + e).remove();
        })
}

function deleteAllApplicationToAdminPanel() {
    deleteLoop(document.getElementById('bodyForDeleteInAdminPanel').querySelectorAll('tr'));
}

function deleteLoop(a) {
    for (let i = 0; i < a.length; i++) {
        a[i].remove();
    }
}