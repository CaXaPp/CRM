<#import "parts/general.ftlh" as g>

<@g.general styles=["/crm/css/login.css"]>
<div class="login-form">
    <form action="/crm/admin/create/department" method="post" id="login-form">
        <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>
    <h1>Добавить департамент</h1>
    <div class="content">
        <div class="input-field">
            <input type="text" name="name" placeholder="Название" autocomplete="nope" required>
        </div>
    </div>
    <div class="action">
        <button type="submit">Добавить</button>
    </div>
    </form>
</div>
</@g.general>