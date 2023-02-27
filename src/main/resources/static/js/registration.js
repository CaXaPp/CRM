const DEPARTMENTS_BLOCK = document.getElementById('departments-block');
const DEPARTMENTS_BLOCK_INNER = DEPARTMENTS_BLOCK.innerHTML;
const ROLE_OPTION = document.getElementById('option-role');
const CHANGE_PASSWORD_BTN = document.getElementById('chang-password');

if (ROLE_OPTION.value === '1') {
    DEPARTMENTS_BLOCK.innerHTML = '';
} else {
    DEPARTMENTS_BLOCK.innerHTML = DEPARTMENTS_BLOCK_INNER;
}

$(document).ready(function() {
    $("#option-role").change(function () {
        let selectedOption = $('#option-role').val();
        if (selectedOption === '1'){
            DEPARTMENTS_BLOCK.innerHTML = '';
        } else {
            DEPARTMENTS_BLOCK.innerHTML = DEPARTMENTS_BLOCK_INNER;
        }
    });
});

if (CHANGE_PASSWORD_BTN != null) {
    CHANGE_PASSWORD_BTN.addEventListener('click', function () {
        document.getElementById('password-block').hidden = false;
        document.getElementById('password-input').value = '';
        CHANGE_PASSWORD_BTN.hidden = true;
    })
}