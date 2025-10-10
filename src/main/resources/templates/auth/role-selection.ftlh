<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Выбор роли — JobSearch</title>
    <style>
        /* 🌄 Яркий, но сбалансированный фон */
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

        /* 💧 Цветные мягкие акценты */
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
            background: #ff9ff3;
            top: -150px;
            left: -120px;
        }

        body::after {
            width: 600px;
            height: 600px;
            background: #74b9ff;
            bottom: -200px;
            right: -150px;
        }

        /* 🧊 Контейнер */
        .container {
            position: relative;
            z-index: 1;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(25px);
            border-radius: 22px;
            padding: 60px 90px;
            text-align: center;
            box-shadow: 0 8px 50px rgba(0, 0, 0, 0.1);
            transition: transform 0.4s ease, box-shadow 0.4s ease;
            color: #333;
        }

        .container:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 60px rgba(0, 0, 0, 0.12);
        }

        /* 📝 Заголовок */
        h2 {
            font-size: 30px;
            margin-bottom: 35px;
            color: #2d3436;
            letter-spacing: 0.4px;
            font-weight: 600;
        }

        /* 🔘 Кнопки */
        .role-button {
            display: inline-block;
            padding: 15px 45px;
            margin: 15px;
            border-radius: 50px;
            font-size: 18px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            border: 2px solid transparent;
            cursor: pointer;
        }

        .applicant {
            background: linear-gradient(90deg, #a29bfe, #ff9ff3);
            color: white;
            border: none;
            box-shadow: 0 0 15px rgba(162, 155, 254, 0.4);
        }

        .applicant:hover {
            transform: scale(1.05);
            box-shadow: 0 0 25px rgba(162, 155, 254, 0.6);
        }

        .employer {
            background: linear-gradient(90deg, #74b9ff, #0984e3);
            color: white;
            border: none;
            box-shadow: 0 0 15px rgba(116, 185, 255, 0.4);
        }

        .employer:hover {
            transform: scale(1.05);
            box-shadow: 0 0 25px rgba(116, 185, 255, 0.6);
        }

        /* 📄 Подпись */
        .footer {
            margin-top: 40px;
            font-size: 14px;
            color: #555;
        }
    </style>
</head>
<body>
<div class="container">
    <h2><@spring.message "register.choose_role.title"/></h2>

    <a href="/auth/register/applicant" class="role-button applicant">
        👤 <@spring.message "register.role.applicant"/>
    </a>

    <a href="/auth/register/employer" class="role-button employer">
        💼 <@spring.message "register.role.employer"/>
    </a>

    <div class="footer">
        © 2025 JobSearch — <@spring.message "register.footer"/>
    </div>
</div>
</body>
</html>


