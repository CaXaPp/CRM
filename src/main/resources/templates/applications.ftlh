<#import "parts/general.ftlh" as g>

<@g.general scripts=["/js/application.js"] styles=["/css/main.css"]>
<div class="application_list_class">
    <div>
        <div>
            <a href="/application/save">Добавить заявку</a>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
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
            <tbody id="tdsBlock">
            </tbody>
        </table>
    </div>
    <br>
    <div id="sort_block_footer">
        Блок для сортировки
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="alert alert-success alert_success_msg" id="alert_msg" hidden role="alert">
        Изменения сохранены!
    </div>
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="info_block">

                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="modal_title_from_js"></h1>
                </div>
                <div class="modal-body" id="modal_body_form">
                    <table>

                        <form id="modal_edit_form" class="modal_edit_form">
                            <#if _csrf??>
                                <input type="hidden"
                                       name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <tr>
                                <td><label for="modal_company">Клиент</label></td>
                                <td><input type="text" class="form-control" name="company" id="modal_company" value="">
                                </td>
                            </tr>
                            <tr>
                                <td><label for="modal_deal_sum">Бюджет</label></td>
                                <td><input type="number" class="form-control" step="any" min="0" name="price" id="modal_deal_sum" value=""></td>
                            </tr>
                            <tr>
                                <td>Товар/услуга</td>
                                <td><select class="form-select" aria-label="Select product" id="modal_product"
                                            name="product" required> </select></td>
                            </tr>
                            <tr>
                                <td><label for="modal_name">Контактное лицо</label></td>
                                <td><input type="text" class="form-control" name="name" id="modal_name" value=""></td>
                            </tr>
                            <tr>
                                <td><hr>Компания:</td>
                            </tr>
                            <tr>
                                <td><label for="modal_phone">Раб. тел</label></td>
                                <td><input type="text" class="form-control" name="phone" id="modal_phone" value=""></td>
                            </tr>
                            <tr>
                                <td><label for="modal_email">Email</label></td>
                                <td><input type="email" class="form-control" name="email" id="modal_email" value="">
                                </td>
                            </tr>
                            <tr>
                                <td><label for="modal_address">Адрес</label></td>
                                <td><input type="text" class="form-control" name="address" id="modal_address" value="">
                                </td>
                            </tr>
                            <tr>
                                <td>Отв-ный сотрудник</td>
                                <td><select class="form-select" aria-label="Select employee" id="modal_employee"
                                            name="employee" required></select></td>
                            </tr>
                            <tr>
                                <td>Статус</td>
                                <td><select class="form-select" aria-label="Select status" id="modal_status"
                                            name="status" required></select></td>
                            </tr>

                            <input type="hidden" value="" id="hiddenApplicationId">


                        </form>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                    <button type="submit" class="btn btn-primary" form="modal_edit_form" onclick="getMessage()">
                        Сохранить
                    </button>
                </div>
            </div>
            <div class="log_block">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div>Блок для задач и логов</div>
            </div>
        </div>
    </div>

    </@g.general>
