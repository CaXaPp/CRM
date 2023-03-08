<nav class="navbar navbar-expand-lg navbar-light bg-light navbar_main_block">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <div class="container d-flex flex-row justify-content-between">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Рабочий стол</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/crm/application">Список заявок</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/crm/operations">Операции</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/crm/tasks">Задачи</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/crm/analytics">Аналитика</a>
                </li>
                <li class="nav-item" id="nav-admin-panel">
                    <a class="nav-link" href="/crm/admin">Администрирование</a>
                </li>
            </ul>

            <div class="dropdown">
                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="user_role_username" data-bs-toggle="dropdown" aria-expanded="false">
                </button>
                <ul class="dropdown-menu dropdown_menu_inner" aria-labelledby="user_role">
                    <p id="user_role"></p>
                    <p id="user_role_department"></p>
                    <form class="d-flex" action="/crm/logout" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'not-token'}">
                        </#if>
                        <button class="btn btn-outline-danger" type="submit" value="Logout">
                            Завершить сеанс
                        </button>
                    </form>
                </ul>

            </div>
        </div>
    </div>
</nav>