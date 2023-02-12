<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"] scripts=["/js/funnel.js"]>

    <div class="login-form">
        <form action="/admin/create/funnel" method="post" id="login-form">
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
                <div id="input_block">
                    <input class="form-control my-3" type="text" id="statusInput1" placeholder="Введите статус" name="statuses" required>
                </div>
                <button type="button" class="btn btn-warning mt-3" id="add_status_btn">Добавить статус</button>
            </div>
            <div class="action">
                <button type="submit" >Добавить Воронку</button>
            </div>
        </form>
    </div>
</@g.general>