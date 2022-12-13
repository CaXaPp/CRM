<#import "parts/general.ftlh" as g>

<@g.general>
    <form action="/application/edit/${application.get().id}" method="post">
        <label>Наименование клиента
            <input type="text" name="company" value="${(application.get().company)!''}">
        </label>
        <label>Сумма сделки
            <input type="text" name="price" value="${(application.get().product.price)!''}">
        </label>
        <label>Товар/услуга
            <select name="product" required>
                <#list products as product>
                    <option value="${product.name}">${product.name}</option>
                </#list>
            </select>
        </label>
        <label>Имя контактного лица (если организация)
            <input type="text" name="name" value="${application.get().name}">
        </label>
        <label>Телефон для обратной связи
            <input type="text" name="phone" value="${application.get().phone}">
        </label>
        <label>Email
            <input type="email" name="email" value="${application.get().email}">
        </label>

        <label>Статус операции
            <input type="text" name="status" value="Не обработан">
        </label>
        <label>Ответственный сотрудник
            <input type="text" name="employee" value="Отсутствует">
        </label>

        <button class="btn btn-primary" type="submit">Сохранить</button>
        <button class="btn btn-primary" id="cancelBtn">Отменить</button>
    </form>

</@g.general>