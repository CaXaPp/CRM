<nav class="navbar navbar-expand-lg navbar-light bg-light navbar_main_block">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <div class="container d-flex flex-row justify-content-between">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Рабочий стол</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/application">Список заявлений</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/operations">Операции</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tasks">Задачи</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/analytics">Аналитика</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin">Администрирование</a>
                </li>
            </ul>

            <div class="dropdown">
                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="user_role_username" data-bs-toggle="dropdown" aria-expanded="false">
                </button>
                <ul class="dropdown-menu dropdown_menu_inner" aria-labelledby="user_role">
                    <p id="user_role"></p>
                    <p id="user_role_department"></p>
                    <form class="d-flex" action="/logout" method="post">
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