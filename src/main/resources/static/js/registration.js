const DEPARTMENTS_BLOCK = document.getElementById('departments-block');
const DEPARTMENTS_BLOCK_INNER = DEPARTMENTS_BLOCK.innerHTML;

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