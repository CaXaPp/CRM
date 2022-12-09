<#import "parts/general.ftlh" as g>

<@g.general>
    <div class="dashboard">
        <a href="#" class="active">
            Информационное табло
        </a>
        <div id="dashboard_links">
            <div class="employee_info">
                <a class="open_close_day" href="#">Закрыть/Открыть день</a>
                <a class="employee_name" href="#">ФИО сотрудника</a>
            </div>

            <div class="dashboard_info">
                Информационное табло
            </div>

        </div>
        <a class="icon" onclick="burgerMenuChange()">
            <i class="fa fa-bars"></i>
        </a>
    </div>
    <div class="info_block">
        <div class="section_info">
            Информация по разделам
        </div>
    </div>
</@g.general>