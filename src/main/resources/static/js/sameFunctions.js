function getUserById(id) {
    axios.get(BASE_URL + "/users/user/" + id).then(function (response) {
        deleteItems_v_2();
        getFunnels(response.data.department.id);
        getProducts(response.data.department.id, 1);
        setTimeout(function () {
            getStatuses(document.getElementById('funnelInApplication').value, 1);
        }, 100);
        deleteElemInLoop(document.getElementById('taskForEmployee').querySelectorAll('option'));
        document.getElementById('taskForEmployee').insertAdjacentHTML('beforeend',
            '<option value="' + response.data.id + '">' + response.data.fio + '</option>'
        );
    })
}

function createTasksInApplication(response) {
    document.getElementById('log_block_footer').insertAdjacentHTML('afterbegin',
        '<div class="taskBlockInApplication">' +
        '<p>' + parseDateByFormat(response.createdAt) + " для " + response.employee.firstName + " " + response.employee.surname + '</p> ' +
        ' <p><span class="alert alert-success">' + response.type.name + '</span>' + " - " + response.application.name + " дата: " + parseDateByFormat(response.deadline) + '</p>' +
        '</div>'
    )
}

function switchOptionProductSelect(product, productId) {
    if (product.id === productId) {
        document.getElementById('productId').insertAdjacentHTML('beforeend',
            '<option selected value="' + product.id + '">' + product.name + '</option>');
    } else {
        document.getElementById('productId').insertAdjacentHTML('beforeend',
            '<option value="' + product.id + '">' + product.name + '</option>');
    }
}

function switchOptionStatusSelect(status, statusId) {
    if (status.id === statusId) {
        document.getElementById('status').insertAdjacentHTML('beforeend',
            '<option selected value="' + status.id + '">' + status.name + '</option>');
    } else {
        document.getElementById('status').insertAdjacentHTML('beforeend',
            '<option value="' + status.id + '">' + status.name + '</option>');
    }
}


function deleteItems() {
    deleteElemInLoop(document.getElementById('employee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskForEmployee').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('taskTypeChoose').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('productId').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('status').querySelectorAll('option'));
    deleteElemInLoop(document.getElementById('funnelInApplication').querySelectorAll('option'));
}