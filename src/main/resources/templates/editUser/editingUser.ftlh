
<#import "/spring.ftl" as spring>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "editUser.editing_your_profile"/></title>

    <style>
        body {
            font-family: "Segoe UI", Roboto, sans-serif;
            background: linear-gradient(135deg, #e3f2fd 0%, #f8e9ff 100%);
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            position: relative;
        }

        body::before, body::after {
            content: "";
            position: absolute;
            border-radius: 50%;
            filter: blur(140px);
            opacity: 0.35;
            z-index: 0;
        }

        body::before {
            width: 500px;
            height: 500px;
            background: #74b9ff;
            top: -150px;
            left: -120px;
        }

        body::after {
            width: 600px;
            height: 600px;
            background: #a29bfe;
            bottom: -200px;
            right: -150px;
        }

        .container {
            position: relative;
            z-index: 1;
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(25px);
            border-radius: 22px;
            padding: 45px 65px;
            text-align: center;
            box-shadow: 0 8px 40px rgba(0, 0, 0, 0.15);
            width: 460px;
        }

        h2 {
            font-size: 26px;
            color: #2d3436;
            margin-bottom: 25px;
            font-weight: 700;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            text-align: left;
            font-size: 15px;
            color: #333;
            font-weight: 600;
        }

        input {
            padding: 12px 15px;
            border: 1px solid #ccc;
            border-radius: 10px;
            font-size: 15px;
            outline: none;
            transition: 0.2s;
        }

        input:focus {
            border-color: #74b9ff;
            box-shadow: 0 0 6px rgba(116, 185, 255, 0.4);
        }

        .avatar-section {
            margin-bottom: 20px;
        }

        .avatar-preview {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #74b9ff;
            margin-bottom: 10px;
        }

        .file-input {
            display: none;
        }

        .file-label {
            display: inline-block;
            padding: 8px 20px;
            background: linear-gradient(90deg, #0984e3, #74b9ff);
            color: #fff;
            border-radius: 10px;
            cursor: pointer;
            transition: 0.3s;
            font-weight: 600;
        }

        .file-label:hover {
            transform: scale(1.05);
            box-shadow: 0 0 10px rgba(116, 185, 255, 0.6);
        }

        button {
            margin-top: 10px;
            background: linear-gradient(90deg, #0984e3, #74b9ff);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: 0.3s;
        }

        button:hover {
            transform: scale(1.05);
            box-shadow: 0 0 15px rgba(116, 185, 255, 0.6);
        }

        a.back {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #0984e3;
            font-weight: 500;
            transition: 0.3s;
        }

        a.back:hover {
            text-decoration: underline;
        }

    </style>
</head>

<body>
<div class="container">

    <h2><@spring.message "editUser.editing_your_profile"/></h2>



    <form action="/user/edit" method="post">
        <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>

        <label for="firstName"><@spring.message "editUser.name"/></label>
        <input type="text" id="firstName" placeholder="Введите имя"
               name="name" value="${userEditRequest.name!''}">

        <label for="lastName"><@spring.message "editUser.surname"/></label>
        <input type="text" id="lastName" placeholder="Введите фамилию"
               name="surname" value="${userEditRequest.surname!''}">

        <label for="age"><@spring.message "editUser.age"/></label>
        <input type="number" id="age" placeholder="18"
               name="age" value="${userEditRequest.age!''}">

        <label for="phone"><@spring.message "editUser.number"/></label>
        <input type="tel" id="phone" placeholder="+996 (___) __-__-__"
               name="phoneNumber" value="${userEditRequest.phoneNumber!''}">

        <label for="address"><@spring.message "editUser.address"/></label>
        <input type="text" id="address" placeholder="Введите адрес"
               name="address" value="${userEditRequest.address!''}">

        <button type="submit"><@spring.message "editUser.save"/></button>
    </form>

    <a href="/user/profile" class="back">← Назад к профилю</a>
</div>
</body>
</html>
