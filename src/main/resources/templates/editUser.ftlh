<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"] scripts=["/js/registration.js"]>
  <div class="login-form">
    <form action="/admin/edit/user/${form.id}" method="post" id="login-form">
      <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
      </#if>
      <input type="hidden"
             name="id" value="${form.id}">
      <h1>Изменить пользователя</h1>
      <h3 class="text-center">${form.getFIO()}</h3>
      <div class="content">
        <div class="input-field">
          <input type="text" name="firstName" placeholder="Имя" autocomplete="nope" value="${form.firstName}" required>
        </div>
        <div class="input-field">
          <input type="text" name="surname" placeholder="Фамилия" autocomplete="nope" value="${form.surname}" required>
        </div>
        <div class="input-field">
          <input type="text" name="middleName" placeholder="Отчество" autocomplete="nope" value="${form.middleName}" required>
        </div>
        <div class="input-field">
          <input type="email" name="email" placeholder="Почта" autocomplete="nope" value="${form.email}" required>
        </div>
        <div class="input-field">
          <label class="form-label">Статус</label>
          <select name="status" id="option-status" class="form-control">
            <#list statuses as s>
              <#if s.name == form.status>
                <option selected value="${s.name}">${s.value}</option>
              <#else>
                <option value="${s.name}">${s.value}</option>
              </#if>
            </#list>
          </select>
        </div>


        <div class="input-field">
          <label class="form-label">Роли</label>
          <select name="roleId" id="option-role" class="form-control">
            <#list roles as role>
              <#if role.id == form.roleId>
                <option selected value="${role.id}">${role.name}</option>
              <#else>
                <option value="${role.id}">${role.name}</option>
              </#if>
            </#list>
          </select>
        </div>

        <div id="departments-block" class="input-field">
          <label  class="form-label">Отделы</label>
          <select name="departmentId" id="option-departments" class="form-control">
            <#list departments as d>
              <#if d.id == form.departmentId>
                <option selected value="${d.id}">${d.name}</option>
              <#else>
                <option value="${d.id}">${d.name}</option>
              </#if>
            </#list>
          </select>
        </div>
        <button class="btn btn-secondary mt-3" id="chang-password">Сменить пароль</button>
        <div class="input-field" hidden="hidden" id="password-block">
          <input type="password" name="password" placeholder="Password" id="password-input" required>
        </div>
      </div>
      <div class="action">
        <button type="submit">Добавить</button>
      </div>
    </form>
  </div>

</@g.general>