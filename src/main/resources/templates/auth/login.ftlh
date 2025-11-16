
<#import "/spring.ftl" as spring>

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


        .login-card {
            position: relative;
            z-index: 1;
            background: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(25px);
            border-radius: 22px;
            box-shadow: 0 8px 40px rgba(0, 0, 0, 0.15);
            padding: 60px 70px;
            text-align: center;
            width: 400px;
            transition: transform 0.4s ease, box-shadow 0.4s ease;
        }

        .login-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 60px rgba(0, 0, 0, 0.12);
        }

        .login-title {
            font-size: 28px;
            margin-bottom: 25px;
            font-weight: 600;
            color: #2d3436;
        }


        .form-control {
            width: 100%;
            padding: 12px 15px;
            border-radius: 10px;
            border: 1px solid #ccc;
            font-size: 15px;
            margin-bottom: 18px;
            transition: 0.2s;
        }

        .form-control:focus {
            border-color: #74b9ff;
            box-shadow: 0 0 6px rgba(116, 185, 255, 0.4);
            outline: none;
        }


        .btn-primary {
            background: linear-gradient(90deg, #74b9ff, #0984e3);
            color: #fff;
            border: none;
            padding: 12px 25px;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: 0.3s;
            width: 100%;
        }

        .btn-primary:hover {
            transform: scale(1.05);
            box-shadow: 0 0 15px rgba(116, 185, 255, 0.6);
        }


        .error-message {
            background: rgba(255, 99, 99, 0.15);
            color: #d63031;
            border: 1px solid #fab1a0;
            border-radius: 10px;
            padding: 10px;
            margin-bottom: 20px;
            font-size: 15px;
        }

        .forgot-password {
            display: inline-block;
            margin-top: 15px;
            font-size: 14px;
            color: #0984e3;
            text-decoration: none;
            transition: 0.3s;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }
    </style>

    <section class="login-section">
        <div class="login-card">
            <h2 class="login-title"><@spring.message"login.login_to_JobSearch"/></h2>

            <#if error>
                <div class="error-message">
                    <@spring.message "login.Incorrect.login_or_password.Please_re_enter"/>
                </div>
            </#if>

            <form id="login-form" action="/auth/login" method="post">
                <#if _csrf??>
                    <input
                            type="hidden"
                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                            value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <input type="email" id="form2Example1" class="form-control" name="username"
                       placeholder="Введите email" required>

                <input type="password" id="form2Example2" class="form-control" name="password"
                       placeholder="Введите пароль" required>

                <button type="submit" class="btn-primary">
                    <@spring.message"login.sign_up"/>
                </button>

                <a href="/auth/forgot-password" class="forgot-password">
                   <@spring.message"login.forgot_your_password"/>
                </a>
            </form>
        </div>
    </section>

