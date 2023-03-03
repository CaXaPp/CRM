<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/admin.js"] styles=["/css/admin.css"]>
    <div class="admin_main_block">
        <a href="/admin/create/product" class="btn btn-success">Добавить
            продукт</a>

        <a href="/admin/create/funnel" class="btn btn-success">Добавить воронку продаж</a>

        <a href="/admin/create/user" class="btn btn-success">Добавить
            пользователя</a>

        <a href="/admin/find/user" class="btn btn-success">Изменить
            пользователя</a>

        <a href="/admin/create/department" class="btn btn-success">Добавить
            департамент</a>

        <a href="/admin/plans" class="btn btn-success">Сумма по плану</a>

        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#deleteApplicationAdmin" onclick="getAllApplicationToAdminPanel()">
            Удаление заявлений
        </button>

        <div class="modal fade" id="deleteApplicationAdmin" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable modal-dialog-admin-panel">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Список заявлений для удалений</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body modal-body-admin-panel">
                        <table class="table">
                            <thead>
                            <tr class="tr-inner-table-admin-panel">
                                <th scope="col">#</th>
                                <th scope="col">Наименование</th>
                                <th scope="col">Товар/услуга</th>
                                <th scope="col">Контактное лицо</th>
                                <th scope="col">Почта</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Сотрудник</th>
                                <th scope="col">Действие</th>
                            </tr>
                            </thead>
                            <tbody id="bodyForDeleteInAdminPanel" class="table-group-divider">
                            </tbody>
                        </table>
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}" id="x-csrf-token">
                        </#if>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

</@g.general>