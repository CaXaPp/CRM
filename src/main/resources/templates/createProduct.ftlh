<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"]>
    <div class="login-form">
        <form action="/admin/create/product" method="post" id="product-form">
            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <h1>Создать продукт</h1>
            <div class="content">
                <div class="input-field">
                    <input type="text" name="name" placeholder="Название продукта" autocomplete="nope" required>
                </div>
            </div>
            <div class="action">
                <button><a href="/admin" style="text-decoration: none; color: black">Вернуться</a></button>
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</@g.general>