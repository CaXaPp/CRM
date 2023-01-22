'use strict';

const ADD_STATUS_BTN = document.getElementById("add_status_btn");
const INPUT_BLOCK = document.getElementById("input_block");
const FIRST_INPUT = document.getElementById("statusInput1");
let status_cnt = 1;

ADD_STATUS_BTN.addEventListener('click', (event) => {
    event.preventDefault();
    status_cnt++;
    const status_input = document.createElement('input');
    status_input.classList = FIRST_INPUT.classList;
    status_input.type = FIRST_INPUT.type;
    status_input.required = true;
    status_input.placeholder = FIRST_INPUT.placeholder;
    status_input.id = 'statusInput' + status_cnt;
    status_input.name = FIRST_INPUT.name;
    INPUT_BLOCK.append(status_input);
    const delete_btn = document.createElement('button');
    delete_btn.className = 'btn btn-danger';
    delete_btn.type = 'button';
    delete_btn.innerText = 'Удалить статус';
    delete_btn.addEventListener('click', (event) => {
        event.preventDefault();
        INPUT_BLOCK.removeChild(status_input);
        INPUT_BLOCK.removeChild(delete_btn);
    })
    INPUT_BLOCK.append(delete_btn);
});
