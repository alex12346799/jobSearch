
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">

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
            background: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(25px);
            border-radius: 22px;
            padding: 50px 70px;
            text-align: center;
            box-shadow: 0 8px 40px rgba(0, 0, 0, 0.15);
            width: 420px;
        }

        h2 {
            font-size: 28px;
            color: #2d3436;
            margin-bottom: 30px;
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
            margin-bottom: -8px;
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

    <h2><@spring.message "register.employer.title1"/></h2>
    <form action="/auth/register/employer" method="post">
        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <label for="password"><@spring.message "register.company_name"/></label>
        <@spring.formInput "userRequestRegisterDto.companyName" 'id="companyName" type="companyName"' />
        <@spring.showErrors "<br>" "error" />


        <label for="password"><@spring.message "register.email"/></label>
        <@spring.formInput "userRequestRegisterDto.email" 'id="email" type="email"' />
        <@spring.showErrors "<br>" "error" />


        <label for="password"><@spring.message "register.password"/></label>
        <@spring.formInput "userRequestRegisterDto.password" 'id="password" type="password"' />
        <@spring.showErrors "<br>" "error" />



<#--        <label>Название компании:</label>-->
<#--        <input type="text" name="companyName" required>-->

<#--        <label>Email:</label>-->
<#--        <input type="email" name="email" required>-->

<#--        <label>Пароль:</label>-->
<#--        <input type="password" name="password" required>-->



        <button type="submit"><@spring.message "register.create_an_account"/></button>
    </form>

    <a href="/auth/register" class="back">← <@spring.message "register.back"/></a>
</div>
</body>
</html>
