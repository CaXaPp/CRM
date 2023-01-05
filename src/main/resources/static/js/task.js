'use strict';

const BASE_URL = 'http://localhost:9000'

const tableBody = document.getElementById('tbody')

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
        <td>${replaceNull(task.quotes)}</td>
    `
    return tr
}

function replaceNull(value) {
    return value === null ? '-' : value
}

function getDate(date) {
    return new Date(date[0], date[1], date[2], date[3], date[4])
}