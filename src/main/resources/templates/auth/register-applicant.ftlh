
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "register.applicant.title"/> — JobSearch</title>
    <style>
        body {
            font-family: "Segoe UI", Roboto, sans-serif;
            background: linear-gradient(135deg, #e3f2fd 0%, #f8e9ff 100%);
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: rgba(255, 255, 255, 0.85);
            padding: 40px 60px;
            border-radius: 16px;
            box-shadow: 0 8px 40px rgba(0,0,0,0.15);
            text-align: center;
            width: 380px;
        }

        h2 {
            font-size: 26px;
            color: #2d3436;
            margin-bottom: 25px;
        }

        label {
            display: block;
            text-align: left;
            font-weight: 500;
            margin-top: 12px;
            margin-bottom: 5px;
            color: #333;
        }

        input {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            outline: none;
            transition: border 0.2s, box-shadow 0.2s;
        }

        input:focus {
            border-color: #74b9ff;
            box-shadow: 0 0 5px rgba(116,185,255,0.4);
        }

        .error {
            color: #e74c3c;
            font-size: 13px;
            text-align: left;
            margin-top: 3px;
        }

        button {
            margin-top: 20px;
            background: linear-gradient(90deg, #74b9ff, #0984e3);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            width: 100%;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        button:hover {
            transform: scale(1.04);
            box-shadow: 0 0 10px rgba(116,185,255,0.6);
        }

        a.back {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            color: #0984e3;
        }

        a.back:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2><@spring.message "register.applicant.title"/></h2>
    <#if errorMessage??>
        <div class="error">${errorMessage}</div>
    </#if>
    <form action="/auth/register/applicant" method="post">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <label for="name"><@spring.message "register.name"/></label>
        <@spring.formInput "userRegisterRequest.name" 'id="name"' />
        <@spring.showErrors "<br>" "error" />

        <label for="surname"><@spring.message "register.surname"/></label>
        <@spring.formInput "userRegisterRequest.surname" 'id="surname"' />
        <@spring.showErrors "<br>" "error" />

        <label for="email"><@spring.message "register.email"/></label>
        <@spring.formInput "userRegisterRequest.email" 'id="email"' />
        <@spring.showErrors "<br>" "error" />

        <label for="password"><@spring.message "register.password"/></label>
        <@spring.formInput "userRegisterRequest.password" 'id="password" type="password"' />
        <@spring.showErrors "<br>" "error" />

        <button type="submit"><@spring.message "register.create_an_account"/></button>
    </form>

    <a href="/auth/register" class="back">← <@spring.message "register.back"/></a>
</div>
</body>
</html>
