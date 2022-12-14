<#import "parts/general.ftlh" as g>

<@g.general>
    <form action="/application/edit/${application.id}" method="post">
        <input type="hidden" name="id" value="${(application.id)!''}">
        <input type="hidden" name="createdAt" value="${application.createdAt}">
        <label>Наименование клиента
            <input type="text" name="company" value="${(application.company)!''}">
        </label>
        <label>Сумма сделки
            <input type="text" name="price" value="${(application.price)!''}">
        </label>
        <label>Товар/услуга
            <select name="productId" required>
                <#list products as product>
                    <#if application.product.id == product.id>
                        <option value="${product.id}" selected>${product.name}</option>
                    <#else>
                        <option value="${product.id}">${product.name}</option>
                    </#if>
                </#list>
            </select>
        </label>
        <label>Имя контактного лица (если организация)
            <input type="text" name="name" value="${application.name}">
        </label>
        <label>Телефон для обратной связи
            <input type="text" name="phone" value="${application.phone}">
        </label>
        <label>Email
            <input type="email" name="email" value="${application.email}">
        </label>
        <label>Адрес
            <input type="text" name="address" value="${application.address}">
        </label>
        <label>Статус операции
            <select name="statusId" required>
                <#list statuses as status>
                    <#if application.status.id == status.id>
                        <option value="${status.id}" selected>${status.name}</option>
                    <#else>
                        <option value="${status.id}">${status.name}</option>
                    </#if>
                </#list>
            </select>
        </label>
        <label>Ответственный сотрудник
            <select name="employeeId" required>
                <#list employees as employee>
                    <#if application.employee.id == employee.id>
                        <option value="${employee.id}" selected>${employee.name}</option>
                    <#else>
                        <option value="${employee.id}">${employee.name}</option>
                    </#if>
                    <option value="${employee.id}">${employee.name}</option>
                </#list>
            </select>
        </label>

        <button class="btn btn-primary" type="submit">Сохранить</button>
        <button class="btn btn-primary" id="cancelBtn">Отменить</button>
    </form>

</@g.general>