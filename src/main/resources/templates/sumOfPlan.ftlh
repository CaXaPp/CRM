<#import "parts/general.ftlh" as g>

<@g.general styles=["/crm/css/login.css", "/crm/css/tasks.css", "/crm/css/application.css"] scripts=["/crm/js/planSum.js"]>

    <div class="login-form">
        <div class="card">
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Отдел</th>
                            <th scope="col">Сумма по плану</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                    <#list sums as sum>
                        <tr id="plan-sum-id-${sum.id}">
                            <td>
                                <button type="button" class="btn btn-warning" onclick="sumEditModalWindow(${sum.id})" data-bs-toggle="modal" data-bs-target="#sumEditModal"> ${sum.id}</button>
                            </td>
                            <td>${(sum.department.name)!''}</td>
                            <td>${sum.sum?string("0")}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="sumEditModal" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="department-name">Hello</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form class="modal-body" id="edit-sum-form">
                    <div class="form-body">
                        <input type="hidden" name="id" value="" id="sum-id">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>
                        <div class="information_field">
                            <label for="sum">Сумма по плану</label>
                            <input type="number" class="form-control" step="any" min="0" name="sum"
                                   id="sum"
                                   value="">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                        <button type="submit" class="btn btn-success" data-bs-dismiss="modal">Поставить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</@g.general>