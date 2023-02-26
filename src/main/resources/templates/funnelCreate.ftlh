<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"] scripts=["/js/funnel.js"]>

    <div class="login-form">
        <form action="/admin/create/funnel" method="post" id="login-form" modelA>
            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <h1>Добавить воронку продаж</h1>
            <div class="content">
                <div class="input-field">
                    <input type="text" name="name" placeholder="Название" autocomplete="nope" required>
                </div>
                <#list departments as d>
                    <div class="form-check mt-3">
                        <input class="form-check-input" name="departments" style="width: 17px; border: black solid 1px" type="checkbox" value="${d.id}" id="${d.name}checkbox">
                        <label class="form-check-label" for="${d.name}checkbox">
                            ${d.name}
                        </label>
                    </div>
                </#list>

                <div style="border: solid yellowgreen 2px; border-radius: 10px; padding: 5px;" class="mt-4">
                    <div id="primary_contact_input_block">
                        <input class="form-control my-3" type="text" id="primary_contact_statusInput1"
                               placeholder="Введите статус" name="statuses" value="Первичный контакт" readonly>
                    </div>
                    <button type="button" class="btn btn-warning mt-2" id="primary_contact_add_status_btn">Добавить статус</button>
                </div>
                <div style="border: solid orange 2px; border-radius: 10px; padding: 5px;" class="mt-2">
                    <div id="NEGOTIATION_input_block">
                        <input class="form-control my-3" type="text" id="NEGOTIATION_statusInput1"
                               placeholder="Введите статус" name="statuses" value="Переговоры" readonly>
                    </div>
                    <button type="button" class="btn btn-warning mt-2" id="NEGOTIATION_add_status_btn">Добавить статус</button>
                </div>
                <div style="border: solid darkorange 2px; border-radius: 10px; padding: 5px;" class="mt-2">
                    <div id="DECISION_input_block">
                        <input class="form-control my-3" type="text" id="DECISION_statusInput1"
                               placeholder="Введите статус" name="statuses" value="Принятие решения" readonly>
                    </div>
                    <button type="button" class="btn btn-warning mt-2" id="DECISION_add_status_btn">Добавить статус</button>
                </div>
                <div style="border: solid coral 2px; border-radius: 10px; padding: 5px;" class="mt-2">
                    <div id="AGREEMENT_input_block">
                        <input class="form-control my-3" type="text" id="AGREEMENT_statusInput1"
                               placeholder="Введите статус" name="statuses" value="Согласование договора" readonly>
                    </div>
                    <button type="button" class="btn btn-warning mt-2" id="AGREEMENT_add_status_btn">Добавить статус</button>
                </div>
                <div id="SUCCESS_input_block" style="border: solid green 2px; border-radius: 10px; padding: 5px;" class="mt-2">
                    <input class="form-control my-3" type="text" id="SUCCESS_statusInput1"
                           placeholder="Введите статус" name="statuses" value="Успешно" readonly>
                </div>
                <div id="FAIL_input_block" style="border: solid red 2px; border-radius: 10px; padding: 5px;" class="mt-2">
                    <input class="form-control my-3" type="text" id="FAIL_statusInput1"
                           placeholder="Введите статус" name="statuses" value="Отказ" readonly>
                </div>
            </div>
            <div class="action">
                <button type="submit" >Добавить Воронку</button>
            </div>
        </form>
    </div>
</@g.general>