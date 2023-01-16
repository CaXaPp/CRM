<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/operation.js"]>
    <link rel="stylesheet" href="./css/operation.css">
    <div class="container mt-4">
        <div class="wrapper">
            <div class="left-bar">
                <div class="left-bar-header">
                    <a href="/operations" class="btn btn-light"><</a>
                    <div class="left-bar-header-title">
                        <p>${('Сделка №' + application.id)!'Новая сделка'}</p>
                    </div>
                </div>
                <div class="left-bar-body">
                    <form action="/operations" method="post" id="operation-form">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>
                        <label for="employees">Ответственный
                            <select name="employeeId" id="employees" required>
                                <#list employees as employee>
                                    <option value="${employee.id}">${employee.firstName} ${employee.middleName}</option>
                                </#list>
                            </select>
                        </label>
                        <hr>
                        <input type="hidden" name="id" value="${(application.id)!''}">
                        <label for="phone" class="label">
                            <p>Номер телефона</p>
                            <input type="text" name="phone" id="phone" placeholder="Контакты" value="${(application.phone)!''}" required>
                        </label>
                        <label for="name" class="label">
                            <p>Имя клиента</p>
                            <input type="text" name="name" id="name" placeholder="Имя клиента" value="${(application.name)!''}" required>
                        </label>
                        <label for="company" class="label">
                            <p>Компания</p>
                            <input type="text" name="company" id="company" placeholder="Компания клиента" value="${(application.company)!''}" required>
                        </label>
                        <label for="email" class="label">
                            <p>Адрес электронной почты</p>
                            <input type="email" name="email" id="email" placeholder="Email" value="${(application.email)!''}" required>
                        </label>
                        <label for="address" class="label">
                            <p>Адрес</p>
                            <input type="text" name="address" id="address" placeholder="Адрес" value="${(application.address)!''}" required>
                        </label>
                        <label for="product" class="label">
                            <p>Продукт/услуга</p>
                            <select name="productId" id="product" required>
                                <#list products as product>
                                    <option value="${product.id}">${product.name}</option>
                                </#list>
                            </select>
                        </label>
                        <label for="price" class="label">
                            <p>Бюджет</p>
                            <input type="number" name="price" id="price" placeholder="Бюджет" value="${(application.price)!'0'}" required>
                        </label>
                        <label for="source" class="label">
                            <p>Источники</p>
                            <select name="sourceId" id="source" required>
                                <#list sources as source>
                                    <option value="${source.id}">${source.name}</option>
                                </#list>
                            </select>
                        </label>
                        <label for="statuses" class="label">
                            <p>Статус</p>
                            <select name="statusId" id="statuses" required>
                                <#list statuses as status>
                                    <option value="${status.id}">${status.name}</option>
                                </#list>
                            </select>
                        </label>
                        <button type="submit" class="btn btn-dark">Создать</button>
                    </form>
                </div>
            </div>
            <div class="right-bar">
                <div class="right-bar-body">
                    <div class="text">
                        <p>hello</p>
                    </div>
                    <div class="text">
                        <p>hello</p>
                    </div>
                </div>
                <div class="right-bar-form">
                    <form>
                        <textarea name="description" placeholder="Примечание"></textarea>
                        <button type="button" class="btn btn-dark">Добавить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@g.general>