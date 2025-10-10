
<#import "/spring.ftl" as spring>


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

    .form-group {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
    }

    .custom-form label {
        font-weight: 600;
        margin-bottom: 8px;
        font-size: 1.1rem;
    }

    .form-control {
        border-radius: 10px;
        border: 1px solid #ccc;
        padding: 12px 15px;
        font-size: 1.1rem;
        transition: all 0.3s ease;
    }

    .form-control:focus {
        border-color: #6a11cb;
        box-shadow: 0 0 10px rgba(106, 17, 203, 0.3);
    }

    .btn-submit {
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

    .btn-submit:hover {
        background: linear-gradient(90deg, #2575fc, #6a11cb);
        transform: scale(1.05);
        box-shadow: 0 5px 15px rgba(37, 117, 252, 0.4);
    }


    .avatar-section {
        text-align: center;
        margin-bottom: 20px;
    }



    .file-input {
        display: none;
    }

    .file-label {
        display: inline-block;
        padding: 10px 20px;
        background: #2575fc;
        color: #fff;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.3s ease;
        font-weight: 600;
    }

    .file-label:hover {
        background: #6a11cb;
    }
</style>

<form class="custom-form" enctype="multipart/form-data" method="post" action="/auth/upload-avatar">
    <#if _csrf??>
        <input
                type="hidden"
                name="${(_csrf.parameterName)!'csrf-param-name'}"
                value="${(_csrf.token)!'csrf-token'}"/>
    </#if>
    <div class="avatar-section">
        <img src="/images/${user.avatar! 'default.png'}" alt="" class="avatar-preview">
        <input type="file" id="avatar" name="avatar" accept="image/*" class="file-input"/>
        <label for="avatar" class="file-label">Выбрать фото</label>
    </div>

</form>

    <form class="custom-form" method="post" action="/auth/update">
        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
    <h2><@spring.message"editUser.editing_your_profile"/></h2>


    <div class="form-group">
        <label for="firstName"><@spring.message"editUser.name"/></label>
        <input type="text" class="form-control" id="firstName" placeholder="Введите имя" name="name" value="${user.name}">
    </div>

    <div class="form-group">
        <label for="lastName"><@spring.message"editUser.surname"/></label>
        <input type="text" class="form-control" id="lastName" placeholder="Введите фамилию" name="surname" value="${user.surname}">
    </div>

    <div class="form-group">
        <label for="age"><@spring.message"editUser.age"/></label>
        <input type="number" class="form-control" id="age" placeholder="18" name="age" value="${user.age}">
    </div>

    <div class="form-group">
        <label for="phone"><@spring.message"editUser.number"/></label>
        <input type="tel" class="form-control" id="phone" placeholder="+996 (___) __-__-__" name="phoneNumber" value="${user.phoneNumber!""}">
    </div>

    <div class="form-group">
        <label for="address"><@spring.message"editUser.address"/></label>
        <input type="text" class="form-control" id="address" placeholder="Введите адрес" name="address" value="${user.address!""}">
    </div>



    <button type="submit" class="btn-submit"><@spring.message"editUser.save"/></button>
</form>

