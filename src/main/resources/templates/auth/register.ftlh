<#--<#import "../layout.ftlh" as main>-->
<#--<@main.layout>-->
<#--<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#authModal">-->
<#--    Войти / Регистрация-->
<#--</button>-->


<#--<div class="modal fade" id="authModal" tabindex="-1" aria-labelledby="authModalLabel"-->
<#--     aria-hidden="true">-->
<#--    <div class="modal-dialog modal-dialog-centered">-->
<#--        <div class="modal-content shadow-lg rounded-4 border-0">-->
<#--            <div class="modal-header border-0 pb-0">-->
<#--                <h5 class="modal-title fw-bold" id="authModalLabel">Добро пожаловать</h5>-->
<#--                <button type="button" class="btn-close" data-bs-dismiss="modal"-->
<#--                        aria-label="Закрыть"></button>-->
<#--            </div>-->

<#--            <div class="modal-body">-->

<#--                <ul class="nav nav-pills mb-3 justify-content-center" id="pills-tab" role="tablist">-->
<#--                    <li class="nav-item" role="presentation">-->
<#--                        <button class="nav-link active" id="login-tab" data-bs-toggle="pill"-->
<#--                                data-bs-target="#login" type="button" role="tab">Вход-->
<#--                        </button>-->
<#--                    </li>-->
<#--                    <li class="nav-item" role="presentation">-->
<#--                        <button class="nav-link" id="register-tab" data-bs-toggle="pill"-->
<#--                                data-bs-target="#register" type="button" role="tab">Регистрация-->
<#--                        </button>-->
<#--                    </li>-->
<#--                </ul>-->


<#--                <div class="tab-content" id="pills-tabContent">-->

<#--                    <div class="tab-pane fade show active" id="login" role="tabpanel">-->
<#--                        <form>-->
<#--                            <div class="mb-3">-->
<#--                                <label class="form-label">Email</label>-->
<#--                                <input type="email" class="form-control form-control-lg"-->
<#--                                       placeholder="Введите email">-->
<#--                            </div>-->
<#--                            <div class="mb-3">-->
<#--                                <label class="form-label">Пароль</label>-->
<#--                                <input type="password" class="form-control form-control-lg"-->
<#--                                       placeholder="Введите пароль">-->
<#--                            </div>-->
<#--                            <button type="submit" class="btn btn-primary w-100 py-2 rounded-3">Войти-->
<#--                            </button>-->
<#--                        </form>-->
<#--                    </div>-->


<#--                    <div class="tab-pane fade" id="register" role="tabpanel">-->
<#--                        <form action="/auth/register" method="post">-->
<#--                            <div class="mb-3">-->
<#--                                <label class="form-label">Имя</label>-->
<#--                                <input-->
<#--                                        type="text"-->
<#--                                        class="form-control form-control-lg"-->
<#--                                        name="name"-->
<#--                                        placeholder="Введите имя"-->
<#--                                        required-->
<#--                                >-->
<#--                            </div>-->
<#--                            <div class="mb-3">-->
<#--                                <label class="form-label">Email</label>-->
<#--                                <input-->
<#--                                        type="email"-->
<#--                                        class="form-control form-control-lg"-->
<#--                                        name="email"-->
<#--                                        placeholder="Введите email"-->
<#--                                        required-->
<#--                                >-->
<#--                            </div>-->
<#--                            <div class="mb-3">-->
<#--                                <label class="form-label">Пароль</label>-->
<#--                                <input-->
<#--                                        type="password"-->
<#--                                        class="form-control form-control-lg"-->
<#--                                        name="password"-->
<#--                                        placeholder="Введите пароль"-->
<#--                                        required-->
<#--                                >-->
<#--                            </div>-->
<#--                            <button type="submit" class="btn btn-success w-100 py-2 rounded-3">-->
<#--                                Зарегистрироваться-->
<#--                            </button>-->
<#--                        </form>-->
<#--                    </div>-->

<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->


<#--<style>-->
<#--    .modal-content {-->
<#--        background: #fff;-->
<#--        border-radius: 1rem;-->
<#--        animation: fadeInUp 0.3s ease-in-out;-->
<#--    }-->

<#--    .nav-pills .nav-link.active {-->
<#--        background-color: #0d6efd;-->
<#--        border-radius: 30px;-->
<#--    }-->

<#--    .nav-pills .nav-link {-->
<#--        border-radius: 30px;-->
<#--        padding: 8px 20px;-->
<#--    }-->

<#--    @keyframes fadeInUp {-->
<#--        from {-->
<#--            transform: translateY(20px);-->
<#--            opacity: 0;-->
<#--        }-->
<#--        to {-->
<#--            transform: translateY(0);-->
<#--            opacity: 1;-->
<#--        }-->
<#--    }-->
<#--</style>-->
<#--</div>-->
<#--</@main.layout>-->

<#-- register.ftlh -->
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
                        <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Иван">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName" class="form-label">Фамилия</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Иванов">
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
                    <select class="form-select" id="role" name="role">
                        <option value="" disabled selected>Выберите роль</option>
                        <option value="APPLICANT">Соискатель</option>
                        <option value="EMPLOYER">Работодатель</option>
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
