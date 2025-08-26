
<style>
    body {
        background: linear-gradient(135deg, #6a11cb, #2575fc);
        font-family: 'Segoe UI', sans-serif;
        padding: 40px;
    }

    .custom-form {
        background: #ffffff;
        padding: 40px;
        border-radius: 16px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
        transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        max-width: 600px;
        margin: auto;
    }

    .custom-form:hover {
        transform: scale(1.02);
        box-shadow: 0 15px 40px rgba(0, 0, 0, 0.2);
    }

    .custom-form h2 {
        text-align: center;
        margin-bottom: 30px;
        font-weight: 700;
        color: #333;
    }

    .custom-form .form-group {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
    }

    .custom-form label {
        font-weight: 600;
        margin-bottom: 8px;
        font-size: 1.1rem;
    }

    .custom-form .form-control {
        border-radius: 10px;
        border: 1px solid #ccc;
        padding: 12px 15px;
        font-size: 1.1rem;
        transition: all 0.3s ease;
    }

    .custom-form .form-control::placeholder {
        color: #999;
    }

    .custom-form .form-control:focus {
        border-color: #6a11cb;
        box-shadow: 0 0 10px rgba(106, 17, 203, 0.3);
    }

    .custom-form .btn-submit {
        background: linear-gradient(90deg, #6a11cb, #2575fc);
        border: none;
        border-radius: 10px;
        padding: 12px 40px;
        font-size: 1rem;
        font-weight: bold;
        color: white;
        transition: all 0.3s ease;
        display: block;
        margin: 25px auto 0 auto;
        text-align: center;
        cursor: pointer;
    }

    .custom-form .btn-submit:hover {
        background: linear-gradient(90deg, #2575fc, #6a11cb);
        transform: scale(1.05);
        box-shadow: 0 5px 15px rgba(37, 117, 252, 0.4);
    }
</style>

<form class="custom-form" enctype="multipart/form-data" method="post" action="/auth/update">
    <#if _csrf??>
        <input
                type="hidden"
                name="${(_csrf.parameterName)!'csrf-param-name'}"
                value="${(_csrf.token)!'csrf-token'}"/>
    </#if>
    <h2>Редактирование профиля</h2>

    <div class="form-group">
        <label for="firstName">Имя</label>
        <input type="text" class="form-control" id="firstName" placeholder="Введите имя" name="name" value="${user.name}">
    </div>

    <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input type="text" class="form-control" id="lastName" placeholder="Введите фамилию" name="surname" value="${user.surname}">
    </div>

    <div class="form-group">
        <label for="age">Возраст</label>
        <input type="number" class="form-control" id="age" placeholder="18" name="age" value="${user.age}">
    </div>

    <div class="form-group">
        <label for="phone">Телефон</label>
        <input type="tel" class="form-control" id="phone" placeholder="+996 (___) __-__-__" name="phoneNumber" value="${user.phoneNumber!""}">
    </div>

    <div class="form-group">
        <label for="address">Адрес</label>
        <input type="text" class="form-control" id="address" placeholder="Введите адрес" name="address" value="${user.address!""}">
    </div>

<#--    <div class="form-group">-->
<#--        <label for="photo">Фото профиля</label>-->
<#--        <input type="file" class="form-control" id="photo" name="photo">-->
<#--    </div>-->

    <button type="submit" class="btn-submit">Сохранить</button>
</form>
