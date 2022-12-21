<#import "parts/general.ftlh" as g>

<@g.general>
    <div class="dashboard_block">

        <div class="dashboard">
            <a href="#" class="active" onclick="burgerMenuChange()">
                Информационное табло
                <i class="fa fa-bars"></i>
            </a>
            <div id="dashboard_links">
                <div class="employee_info">
                    <a class="open_close_day" href="#">Закрыть/Открыть день</a>
                    <a class="employee_name" href="#">ФИО сотрудника</a>
                </div>

                <div class="dashboard_info">
                    <div>
                        <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                            <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                            <label class="btn btn-outline-primary" for="btnradio1">Сегодня</label>

                            <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                            <label class="btn btn-outline-primary" for="btnradio2">Вчера</label>

                            <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                            <label class="btn btn-outline-primary" for="btnradio3">Неделя</label>

                            <input type="radio" class="btn-check" name="btnradio" id="btnradio4" autocomplete="off">
                            <label class="btn btn-outline-primary" for="btnradio4">Месяц</label>

                            <input type="radio" class="btn-check" name="btnradio" id="btnradio5" autocomplete="off">
                            <label class="btn btn-outline-primary" for="btnradio5">Период</label>
                        </div>

                        <div class="btn-group" role="group" aria-label="Basic example">
                            <button type="button" class="btn btn-primary">Все</button>
                            <div class="btn-group" role="group">
                                <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                    Мои
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                    <li><a class="dropdown-item" href="#">Сортировка №1</a></li>
                                    <li><a class="dropdown-item" href="#">Сортировка №2</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="info_block">
            <div class="section_info">
                Информация по разделам
            </div>
        </div>
    </div>

</@g.general>