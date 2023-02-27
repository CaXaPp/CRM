<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"] scripts=["/js/registration.js"]>
    <div class="login-form">
        <form action="/admin/create/user" method="post" id="login-form">
            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <h1>Добавить пользователя</h1>
            <div class="content">
                <div class="input-field">
                    <input type="text" name="firstName" placeholder="Имя" autocomplete="nope" required>
                </div>
                <div class="input-field">
                    <input type="text" name="surname" placeholder="Фамилия" autocomplete="nope" required>
                </div>
                <div class="input-field">
                    <input type="text" name="middleName" placeholder="Отчество" autocomplete="nope" required>
                </div>
                <div class="input-field">
                    <input type="email" name="email" placeholder="Почта" autocomplete="nope" required>
                </div>
                <input type="hidden"
                       name="status" value="Active">
                <div class="input-field">
                    <label class="form-label">Роли</label>
                    <select name="roleId" id="option-role" class="form-control">
                        <#list roles as role>
                            <option value="${role.id}">${role.name}</option>
                        </#list>
                    </select>
                </div>

                <div id="departments-block">
                    <label  class="form-label">Отделы</label>
                    <select name="departmentId" id="option-departments" class="form-control">
                        <#list departments as d>
                            <option value="${d.id}">${d.name}</option>
                        </#list>
                    </select>
                </div>
                <div class="input-field">
                    <input type="password" name="password" placeholder="Password" autocomplete="new-password" required>
                </div>
            </div>
            <div class="action">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</@g.general>