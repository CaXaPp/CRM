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
                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="user_role" data-bs-toggle="dropdown" aria-expanded="false">
                </button>
                <ul class="dropdown-menu" aria-labelledby="user_role">
                    <p class="dropdown-item" id="user_role_username"></p>
                    <p class="dropdown-item" id="user_role_department"></p>
<#--                    <li><a class="dropdown-item" href="#">Action</a></li>-->
<#--                    <li><a class="dropdown-item" href="#">Another action</a></li>-->
<#--                    <li><a class="dropdown-item" href="#">Something else here</a></li>-->
                    <form class="d-flex dropdown-item" action="/logout" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'not-token'}">
                        </#if>
                        <button class="btn btn-outline-success" type="submit" value="Logout">
                            Выйти
                        </button>
                    </form>
                </ul>

            </div>
        </div>
    </div>
</nav>