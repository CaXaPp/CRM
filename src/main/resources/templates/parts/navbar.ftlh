<nav class="navbar navbar-expand-lg navbar-light bg-light navbar_main_block">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <div class="container d-flex flex-row justify-content-between">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Рабочий стол</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/application">Список всех заявок</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/operations">Мои операции</a>
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
            <form class="d-flex" action="/logout" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'not-token'}">
                </#if>
                <button class="btn btn-outline-success" type="submit" value="Logout">
                    Выйти
                </button>
            </form>
        </div>
    </div>
</nav>