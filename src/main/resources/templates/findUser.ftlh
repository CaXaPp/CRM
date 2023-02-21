<#import "parts/general.ftlh" as g>

<@g.general styles=["/css/login.css"]>
    <div class="login-form">
        <h2 class="text-center">Поиск сотрудника</h2>
        <div class="content">
            <select id="user-option" class="form-select form-select-lg mb-3" name="id">
                <#list users as u>
                    <option value="${u.id}">${u.getFIO()} (${u.email})</option>
                </#list>
            </select>
        </div>
        <div class="action mt-3">
            <a id="search-btn" class="my-btn">Перейти</a>
        </div>
    </div>

    <script>

        let CHOICE_LINK = 'http://localhost:9000/admin/edit/user/' + $('#user-option').val();

        document.getElementById('search-btn').href = CHOICE_LINK;

        $(function(){
            $("#user-option").select2();
        });

        $(document).ready(function() {
            $("#user-option").change(function () {
                CHOICE_LINK
                    = 'http://localhost:9000/admin/edit/user/' + $('#user-option').val();
            });
        });

    </script>
</@g.general>