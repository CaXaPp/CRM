<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"]>
    <div class="login-form">
        <form action="/admin/edit/user/${user.id}" method="post" id="login-form">
            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <h3 class="text-center">${user.getFIO()}</h3>
            <div class="content">
                <input type="hidden"
                       name="id" value="${user.id}">
                <div class="input-field">
                    <input type="text" name="firstName" placeholder="Имя" autocomplete="nope" required value="${user.firstName}">
                </div>
                <div class="input-field">
                    <input type="text" name="surname" placeholder="Фамилия" autocomplete="nope" required value="${user.surname}">
                </div>
                <div class="input-field">
                    <input type="text" name="middleName" placeholder="Отчество" autocomplete="nope" required value="${user.middleName}">
                </div>
                <div class="input-field">
                    <input type="email" name="email" placeholder="Почта" autocomplete="nope" required value="${user.email}">
                </div>
                <div class="input-field">
                    <label class="form-label">Роли</label>
                    <select name="role" id="option" class="form-control">
                        <#list roles as role>
                            <#if role.name == user.role>
                                <option selected value="${role.name}">${role.name}</option>
                            <#else>
                                <option value="${role.name}">${role.name}</option>
                            </#if>
                        </#list>
                    </select>
                </div>

                <div>
                    <label  class="form-label">Отделы</label>
                    <select name="department" id="option" class="form-control">
                        <#list departments as d>
                            <#if d.name == user.department>
                                <option selected value="${d.name}">${d.name}</option>
                            <#else>
                                <option value="${d.name}">${d.name}</option>
                            </#if>
                        </#list>
                    </select>
                </div>

                <div>
                    <label  class="form-label">Отделы</label>
                    <select name="status" id="option" class="form-control">
                        <#list statuses as s>
                            <#if s.name == user.status>
                                <option selected value="${s.name}">${s.name}</option>
                            <#else>
                                <option value="${s.name}">${s.name}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="action">
                <button type="submit">Изменить</button>
            </div>
        </form>
    </div>
</@g.general>
