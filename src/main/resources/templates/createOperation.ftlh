<#import "parts/general.ftlh" as g>

<@g.general>
    <div class="container">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Наименование клиента</th>
                    <th scope="col">Товар/услуга</th>
                    <th scope="col">Имя контактного лица (если организация)</th>
                    <th scope="col">Телефон для обратной связи</th>
                    <th scope="col">Email</th>
                    <th scope="col">Адрес</th>
                    <th scope="col">Статус операции</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <#list applications as application>
                    <tr>
                        <th scope="row">${application.id}</th>
                        <td>${application.name}</td>
                        <td>${application.product.name}</td>
                        <td>${application.company}</td>
                        <td>${application.phone}</td>
                        <td>${application.email}</td>
                        <td>${application.address}</td>
                        <td>${application.status.name}</td>
                        <td><a href="/operations/add/new?id=${application.id}" class="btn btn-dark">Создать</a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</@g.general>