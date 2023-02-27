'use strict';

const PRIMARY_CONTACT_ADD_STATUS_BTN = document.getElementById("primary_contact_add_status_btn");
const PRIMARY_CONTACT_INPUT_BLOCK = document.getElementById("primary_contact_input_block");
const PRIMARY_CONTACT_FIRST_INPUT = document.getElementById("primary_contact_statusInput1");
let primary_contact_status_cnt = 1;

PRIMARY_CONTACT_ADD_STATUS_BTN.addEventListener('click', (event) => {
    event.preventDefault();
    primary_contact_status_cnt++;
    const status_input = document.createElement('input');
    status_input.classList = PRIMARY_CONTACT_FIRST_INPUT.classList;
    status_input.type = PRIMARY_CONTACT_FIRST_INPUT.type;
    status_input.required = true;
    status_input.placeholder = PRIMARY_CONTACT_FIRST_INPUT.placeholder;
    status_input.id = 'PRIMARY_statusInput' + primary_contact_status_cnt;
    status_input.name = PRIMARY_CONTACT_FIRST_INPUT.name;
    status_input.required = true;
    PRIMARY_CONTACT_INPUT_BLOCK.append(status_input);
    const delete_btn = document.createElement('button');
    delete_btn.className = 'btn btn-danger';
    delete_btn.type = 'button';
    delete_btn.innerText = 'Удалить статус';
    delete_btn.addEventListener('click', (event) => {
        event.preventDefault();
        PRIMARY_CONTACT_INPUT_BLOCK.removeChild(status_input);
        PRIMARY_CONTACT_INPUT_BLOCK.removeChild(delete_btn);
    })
    PRIMARY_CONTACT_INPUT_BLOCK.append(delete_btn);
});


const NEGOTIATION_ADD_STATUS_BTN = document.getElementById("NEGOTIATION_add_status_btn");
const NEGOTIATION_INPUT_BLOCK = document.getElementById("NEGOTIATION_input_block");
const NEGOTIATION_FIRST_INPUT = document.getElementById("NEGOTIATION_statusInput1");
let NEGOTIATION_status_cnt = 1;

NEGOTIATION_ADD_STATUS_BTN.addEventListener('click', (event) => {
    event.preventDefault();
    NEGOTIATION_status_cnt++;
    const status_input = document.createElement('input');
    status_input.classList = NEGOTIATION_FIRST_INPUT.classList;
    status_input.type = NEGOTIATION_FIRST_INPUT.type;
    status_input.required = true;
    status_input.placeholder = NEGOTIATION_FIRST_INPUT.placeholder;
    status_input.id = 'NEGOTIATION_statusInput' + NEGOTIATION_status_cnt;
    status_input.name = NEGOTIATION_FIRST_INPUT.name;
    status_input.required = true;
    NEGOTIATION_INPUT_BLOCK.append(status_input);
    const delete_btn = document.createElement('button');
    delete_btn.className = 'btn btn-danger';
    delete_btn.type = 'button';
    delete_btn.innerText = 'Удалить статус';
    delete_btn.addEventListener('click', (event) => {
        event.preventDefault();
        NEGOTIATION_INPUT_BLOCK.removeChild(status_input);
        NEGOTIATION_INPUT_BLOCK.removeChild(delete_btn);
    })
    NEGOTIATION_INPUT_BLOCK.append(delete_btn);
});

const DECISION_ADD_STATUS_BTN = document.getElementById("DECISION_add_status_btn");
const DECISION_INPUT_BLOCK = document.getElementById("DECISION_input_block");
const DECISION_FIRST_INPUT = document.getElementById("DECISION_statusInput1");
let DECISION_status_cnt = 1;

DECISION_ADD_STATUS_BTN.addEventListener('click', (event) => {
    event.preventDefault();
    DECISION_status_cnt++;
    const status_input = document.createElement('input');
    status_input.classList = DECISION_FIRST_INPUT.classList;
    status_input.type = DECISION_FIRST_INPUT.type;
    status_input.required = true;
    status_input.placeholder = DECISION_FIRST_INPUT.placeholder;
    status_input.id = 'DECISION_statusInput' + DECISION_status_cnt;
    status_input.name = DECISION_FIRST_INPUT.name;
    status_input.required = true;
    DECISION_INPUT_BLOCK.append(status_input);
    const delete_btn = document.createElement('button');
    delete_btn.className = 'btn btn-danger';
    delete_btn.type = 'button';
    delete_btn.innerText = 'Удалить статус';
    delete_btn.addEventListener('click', (event) => {
        event.preventDefault();
        DECISION_INPUT_BLOCK.removeChild(status_input);
        DECISION_INPUT_BLOCK.removeChild(delete_btn);
    })
    DECISION_INPUT_BLOCK.append(delete_btn);
});


const AGREEMENT_ADD_STATUS_BTN = document.getElementById("AGREEMENT_add_status_btn");
const AGREEMENT_INPUT_BLOCK = document.getElementById("AGREEMENT_input_block");
const AGREEMENT_FIRST_INPUT = document.getElementById("AGREEMENT_statusInput1");
let AGREEMENT_status_cnt = 1;

AGREEMENT_ADD_STATUS_BTN.addEventListener('click', (event) => {
    event.preventDefault();
    AGREEMENT_status_cnt++;
    const status_input = document.createElement('input');
    status_input.classList = AGREEMENT_FIRST_INPUT.classList;
    status_input.type = AGREEMENT_FIRST_INPUT.type;
    status_input.required = true;
    status_input.placeholder = AGREEMENT_FIRST_INPUT.placeholder;
    status_input.id = 'AGREEMENT_statusInput' + AGREEMENT_status_cnt;
    status_input.name = AGREEMENT_FIRST_INPUT.name;
    status_input.required = true;
    AGREEMENT_INPUT_BLOCK.append(status_input);
    const delete_btn = document.createElement('button');
    delete_btn.className = 'btn btn-danger';
    delete_btn.type = 'button';
    delete_btn.innerText = 'Удалить статус';
    delete_btn.addEventListener('click', (event) => {
        event.preventDefault();
        AGREEMENT_INPUT_BLOCK.removeChild(status_input);
        AGREEMENT_INPUT_BLOCK.removeChild(delete_btn);
    })
    AGREEMENT_INPUT_BLOCK.append(delete_btn);
});