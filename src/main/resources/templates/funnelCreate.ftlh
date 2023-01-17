<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"]>
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
                <div class="py-3">
                    <label  class="form-label">Отделы</label>
                    <select name="departmentId" id="option" class="form-control">
                        <#list departments as d>
                            <option value="${d.id}">${d.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="action">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</@g.general>