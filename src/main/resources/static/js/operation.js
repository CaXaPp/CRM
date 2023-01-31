'use strict'

const BASE_URL = 'http://localhost:9000'

let employeeSelect = document.getElementById('employees')
let statusSelect = document.getElementById('statuses')

employeeSelect.addEventListener('change', async (event) => {
    let employeeId = getEmployeeId()
    let statuses = getStatusesByEmployeeId(employeeId)
    removeStatuses()
    addStatusesToPage(await statuses)
})

function getEmployeeId() {
    let form = document.getElementById('operation-form')
    let formData = new FormData(form)
    return formData.get('employeeId')
}

async function getStatusesByEmployeeId(id) {
    let response = await axios.get(BASE_URL + '/statuses?employee=' + id)
        .catch((error) => {
            alert(error)
        })
    return await response.data
}

function addStatusesToPage(statuses) {
    statuses.forEach((status) => {
        let element = createStatusElement(status)
        statusSelect.append(element)
    })
}

function createStatusElement(status) {
    let element = document.createElement('option')
    element.value = status.id
    element.innerText = status.name
    return element
}

function removeStatuses() {
    statusSelect.innerHTML = ``
}
