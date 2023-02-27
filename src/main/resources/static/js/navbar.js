'use strict';
const BASE_URL = 'http://localhost:9000';

document.addEventListener("DOMContentLoaded", function () {
    getRole();
})

function getRole() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        document.getElementById('user_role_username').innerText = response.data.surname[0] + ". " + response.data.firstName[0] + ". " + response.data.middleName;
        document.getElementById('user_role').innerText = "Доступ: " + response.data.role;
        document.getElementById('user_role_department').innerText = response.data.department.name;
    })
}