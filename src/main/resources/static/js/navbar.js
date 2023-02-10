'use strict';

document.addEventListener("DOMContentLoaded", function () {
    getRole();
})

function getRole() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        document.getElementById('user_role').innerText = response.data.role.name;
        document.getElementById('user_role_username').innerText = response.data.firstName + " " + response.data.surname;
        document.getElementById('user_role_department').innerText = response.data.department.name;
    })
}