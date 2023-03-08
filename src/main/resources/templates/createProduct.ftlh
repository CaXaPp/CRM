<#import "parts/general.ftlh" as g>

<@g.general styles=["/crm/css/login.css"]>
    <div class="login-form">
        <form action="/crm/admin/create/product" method="post" id="product-form">
            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <h1>Создать продукт</h1>
            <div class="content">
                <div class="input-field">
                    <input type="text" name="name" placeholder="Название продукта" autocomplete="nope" required>
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