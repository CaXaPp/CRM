<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Landing page</title>
    <base href="/crm">
    <link rel="stylesheet" href="./css/landing.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-5">
        <h3 class="text-center landing-head">Оставьте нам вашу заявку и наши сотрудники обязательно с вами свяжутся.
            <br>(для заявки просим вас ответить на вопросы ниже)</h3>
        <div class="d-flex justify-content-center">
            <form id="application-form" action="/crm/client/save"  method="post" class="pt-4 w-50 row d-flex justify-content-center">
                <#if _csrf??>
                    <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}">
                </#if>
                <div class="form-group mt-4">
                    <input type="text" class="form-control landing-input" name="name" id="name" placeholder="Ваше имя" required>
                </div>
                <div class="form-group mt-4">
                    <input type="text" class="form-control landing-input" name="company" id="company" placeholder="Наименование организации" required>
                </div>

                <div class="form-group mt-4">
                    <select class="form-select landing-input" aria-label="Select product" name="productId" id="product" required>
                        <#list products as product>
                            <option value="${product.id}">${product.name}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group mt-4">
                    <input type="tel" class="form-control landing-input" name="phone" id="phone" placeholder="Ваш номер телефона" required>
                </div>
                <div class="form-group mt-4">
                    <input type="email" class="form-control landing-input" name="email" id="email" placeholder="Email" required>
                </div>
                <div class="form-group mt-4">
                    <input type="text" class="form-control landing-input" name="address" id="address" placeholder="Address" required>
                </div>
                <input type="number" hidden="hidden" name="sourceId" id="source" value="8">
                <button type="submit" class="btn btn-primary mt-4 w-25 landing-btn">Оставить заявку</button>
            </form>
        </div>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</html>