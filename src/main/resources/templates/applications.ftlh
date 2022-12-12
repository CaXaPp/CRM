<#import "parts/general.ftlh" as g>

<@g.general>
    <br>
    <br>
    <div>

        <a href="/getApplication">Добавить заявку</a>
<#--        <button>Редактировать</button>-->
        <button>Назначить</button>
    </div>
    <br>
    <br>

    <div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Наименование клиента</th>
                <th scope="col">Сумма сделки</th>
                <th scope="col">Товар/услуга</th>
                <th scope="col">Имя контактного лица (если организация)</th>
                <th scope="col">Телефон для обратной связи</th>
                <th scope="col">Email</th>
                <th scope="col">Адрес</th>
                <th scope="col">Статус операции</th>
                <th scope="col">Ответственный сотрудник</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list page.content as applications>
                <tr>
                    <td>${applications.company}</td>
                    <td>${applications.product.price}</td>
                    <td>${applications.product.name}</td>
                    <td>${applications.name}</td>
                    <td>${applications.phone}</td>
                    <td>${applications.email}</td>
                    <td>Отсутствует</td>
                    <td>Не обработан</td>
                    <td>Отсутствует</td>
                    <td><a href="/applications/edit/${applications.id}">Редактировать</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
<br>
    <div>
        сортировка
    </div>

</@g.general>