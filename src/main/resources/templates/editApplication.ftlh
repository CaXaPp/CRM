<#import "parts/general.ftlh" as g>

<@g.general>

    <div class="editContainer">
        <form action="/applications/edit" method="post">
            <label>
                <input type="hidden" name="id" value="${application.get().id}">
            </label>
            <label>Наименование клиента
                <input type="text" name="company" value="${(application.get().company)!''}">
            </label>
            <label>Сумма сделки
                <input type="text" name="price" value="${(application.get().product.price)!''}">
            </label>
            <label>Товар/услуга
                <input type="text" name="product" value="${application.get().product.name}">
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
            <label>Адрес
                <input type="text" name="address" value="Отсутствует">
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
    </div>


</@g.general>