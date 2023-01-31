'use strict';

const BASE_URL = 'http://localhost:9000'

const tableBody = document.getElementById('tbody')
const tasks = document.querySelectorAll(".task")
let taskId;

const form = document.getElementById('edit-task-form')
form.addEventListener('submit', async (event) => {
    event.preventDefault()
    let data = getDataFromForm(event)
    let htmlElement = createTask(data)
    tableBody.append(await htmlElement)
})

async function createTask(data) {
    let response = await fetch(BASE_URL + '/tasks', {
        method: 'POST',
        body: data
    })
    .catch((error) => {
        alert(error)
    })
    let object = await response.json()
    return createTaskElement(object)
}

function getDataFromForm(event) {
    let target = event.target
    return new FormData(target)
}

function createTaskElement(task) {
    let tr = document.createElement('tr')
    tr.innerHTML =
    `
        <td>${getDate(task.deadline).toISOString()}</td>
        <td>${task.employee.surname} ${task.employee.firstName} ${task.employee.middleName}</td>
        <td>${task.application.name}</td>
        <td>${task.type.name}</td>
        <td>${task.description}</td>
        <td>${replaceNull(task.result)}</td>
    `
    return tr
}

function replaceNull(value) {
    return value === null ? '-' : value
}

function getDate(date) {
    return new Date(date[0], date[1], date[2], date[3], date[4])
}

tasks.forEach(task => task.addEventListener("click",  () => editTask(task)))

function editTask(task) {
    showTask($(task).data("id"))
    taskId = $(task).data("id")
}

function showTask(id) {
    $.get(window.location.origin + `/tasks/task/${id}`, function (data) {
        $("select#operation").val(data.operation_id).change()
        $("input[name='deadline']").val(getDate(data.deadline).toISOString().slice(0, 19))
        $("select#employee").val(data.employee_id).change()
        $("select[name='typeId']").val(data.type_id).change()
        $("textarea#description").val(data.description)
    })
}

$("#task-edit-form button[type='submit']").on("click", function (e) {
    e.preventDefault()
    edit(taskId)
})

function edit(id) {
    let formData = {
        operation_id: $("#task-edit-form #operation").val(),
        deadline: $("#task-edit-form #deadline").val(),
        employee_id: $("#task-edit-form #employee").val(),
        type_id: $("#task-edit-form #typeId").val(),
        description: $("#task-edit-form #description").val()
    };
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: window.location.origin + `/tasks/task/${id}`,
        data: JSON.stringify(formData),
        data_type: "json",
        success: function (data) {
            alert("123")
        },
        beforeSend: function (xhr) {
            let token = $("#task-edit-form input[name='_csrf']").val()
            let header = $("#task-edit-form input[name='_csrf_header']").val()
            xhr.setRequestHeader(header, token)
        }
    })
}
