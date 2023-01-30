'use strict';

const BASE_URL = 'http://localhost:9000'

const tableBody = document.getElementById('tbody')
const tasks = document.querySelectorAll(".task")

const form = document.getElementById('edit-task-form')
form.addEventListener('submit', async (event) => {
    event.preventDefault()
    let data = getDataFromForm(event)
    let htmlElement = createTask(data)
    let htmlElements = editTask(data)
    tableBody.append(await htmlElement, htmlElements)
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

async function editTask(data) {
    let response = await fetch(BASE_URL + '/tasks', {
        method: 'GET',
        body: data
    })
        .catch((error) => {
            alert(error)
        })
    let object = await response.json()
    return editTaskElement(object)
}

tasks.forEach(task => task.addEventListener("click",  () => editTask(task)))


function editTask(task) {
    //$("#editTask").attr("class", "model fade show")
    showTask($(task).data("id"))
}

function showTask(id) {
    $.get(window.location.origin + `/tasks/task/${id}`, function (data) {
        console.log(data)
        $("select#operation").val(data.application.id).change()
        $("input[name='deadline']").val(getDate(data.deadline).toISOString().slice(0, 19))
        $("select#employee").val(data.employee.id).change()
        $("select[name='typeId']").val(data.type.id).change()
        $("textarea#description").val(data.description)
    })
}

function editUser(id) {
    $.ajax({
        url: window.location.origin + `/tasks/task/${id}`,
        type: "PUT",
        success: function (data) {
            console.log(data.data)
        }
    })
}
