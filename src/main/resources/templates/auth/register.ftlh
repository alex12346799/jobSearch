
<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        .auth-bg {
            min-height: 100vh;
            display: grid;
            place-items: center;
            background: linear-gradient(135deg, #6366f1, #22c55e);
        }
        .auth-card {
            width: 100%;
            max-width: 460px;
            background: #fff;
            border-radius: 1rem;
            box-shadow: 0 10px 30px rgba(0,0,0,.2);
            padding: 2rem;
        }
        .auth-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
        }
    </style>

    <div class="auth-bg">
        <div class="auth-card">
            <h2>Регистрация</h2>
            <form action="/auth/register" method="post">

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName" class="form-label">Имя</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Иван">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName" class="form-label">Фамилия</label>
                        <input type="text" class="form-control" id="surname" name="surname" placeholder="Иванов">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com">
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="••••••••">
                </div>

                <div class="mb-3">
                    <label for="role" class="form-label">Роль</label>
                    <select class="form-select" id="role" name="roleId">
                        <option value="" disabled selected>Выберите роль</option>
                        <option value="1">EMPLOYEE</option>
                        <option value="2">APPLICANT</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
            </form>

            <div class="text-center mt-3">
                Уже есть аккаунт? <a href="/auth/login">Войти</a>
            </div>
        </div>
    </div>

</@main.layout>
