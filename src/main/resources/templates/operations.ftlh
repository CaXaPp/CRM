<#import "parts/general.ftlh" as g>

<@g.general>
    <div class="wrap mt-5">
        <div class="button-group mb-5">
            <div class="dropdown">
                <button class="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Добавить операцию
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="/operations/add">Выбрать из всех заявок</a></li>
                    <li><a class="dropdown-item" href="/operations/add/new">Добавить новую</a></li>
                </ul>
            </div>
            <#if user.role != 'Сотрудник'>
                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Воронки
                    </button>
                    <ul class="dropdown-menu">
                        <#list funnels as funnel>
                            <li><a class="dropdown-item" href="/operations?funnel=${funnel.id}">${funnel.name}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
        <div class="operations">
            <#list operation_statuses as status>
                <div class="card">
                    <div class="card-header text-center">
                        ${status.name}
                    </div>
                    <ul class="list-group list-group-flush">
                        <#list operations as operation>
                            <#if status.id == operation.status.id>
                                <li class="list-group-item text-center">Операция №${operation.id}</li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </#list>
        </div>
    </div>
</@g.general>