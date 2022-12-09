<nav class="navbar navbar-expand-lg navbar-light bg-light navbar_main_block">

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Рабочий стол</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">Список всех заявок</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Мои операции</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Задачи</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Аналитика</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Администрирование</a>
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
</nav>