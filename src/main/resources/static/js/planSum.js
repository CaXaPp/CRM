'use strict';

const planSumForm = document.getElementById('edit-sum-form')

planSumForm.addEventListener('submit', (event) => {
    event.preventDefault()
    updateSum(event)
})



function sumEditModalWindow(id) {
    axios.get(BASE_URL + '/admin/plans/' + id)
        .then((response) => {
            resetForm()
            setFields(response.data)
        }
    );
}

function setFields(response) {
    document.getElementById('department-name').innerText = response.department.name
    document.getElementById('sum-id').value = response.id
    document.getElementById('sum').value = response.sum
}

function resetForm() {
    planSumForm.reset()
}

function updateSum(event) {
    let target = event.target;
    let formData = new FormData(target);

    fetch(BASE_URL + "/admin/plans", {
        method: 'PUT',
        body: formData
    }).then((response) => {
        return response.json()
    }).then((data) => {
        updateRowOnPage(data)
    }).catch((error) => {
        console.log('Error:', error);
    });
}

function updateRowOnPage(response) {
    let oldRow = document.getElementById('plan-sum-id-' + response.id)
    let parent = document.getElementById('tbody')
    parent.insertBefore(createSumElement(response), oldRow)
    oldRow.remove()
}

function createSumElement(response) {
    let row = document.createElement('tr')
    row.id = 'plan-sum-id-' + response.id
    row.innerHTML = `
        <td>
            <button type="button" class="btn btn-warning" onclick="sumEditModalWindow(${response.id})" data-bs-toggle="modal" data-bs-target="#sumEditModal"> ${response.id}</button>
        </td>
        <td>${response.department.name}</td>
        <td>${response.sum}</td>
    `
    return row
}



