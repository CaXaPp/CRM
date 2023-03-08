'use strict';
const BASE_URL = 'http://37.139.16.4:9000/crm';

document.addEventListener("DOMContentLoaded", function () {
    getRole();
})

function getRole() {
    axios.get(BASE_URL + "/users/auth_user").then(function (response) {
        if (response.data.role !== 'Администратор') {
            document.getElementById('nav-admin-panel').style.display = "none";
        }
        document.getElementById('user_role_username').innerText = response.data.surname[0] + ". " + response.data.firstName[0] + ". " + response.data.middleName;
        document.getElementById('user_role').innerText = "Доступ: " + response.data.role;
        document.getElementById('user_role_department').innerText = response.data.department.name;
    })
}