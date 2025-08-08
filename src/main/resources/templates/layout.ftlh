<#macro layout>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>JobSearch</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
                crossorigin="anonymous"></script>
    </head>
    <body>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Jobsearch</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="resumes">Резюме</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="vacancies">Вакансии</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/dasfdsf">Редактирование</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/qwe">Личный кабинет</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Dropdown
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="resumes">Резюме</a></li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                    </li>
                </ul>
                <#--                <form class="d-flex" role="search">-->
                <#--                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search"/>-->
                <#--                    <button class="btn btn-outline-success" type="submit">Search</button>-->
                <#--                </form>-->

                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#authModal">
                    Войти / Регистрация
                </button>


                <div class="modal fade" id="authModal" tabindex="-1" aria-labelledby="authModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content shadow-lg rounded-4 border-0">
                            <div class="modal-header border-0 pb-0">
                                <h5 class="modal-title fw-bold" id="authModalLabel">Добро пожаловать</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Закрыть"></button>
                            </div>

                            <div class="modal-body">
                                <!-- Вкладки -->
                                <ul class="nav nav-pills mb-3 justify-content-center" id="pills-tab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link active" id="login-tab" data-bs-toggle="pill"
                                                data-bs-target="#login" type="button" role="tab">Вход
                                        </button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="register-tab" data-bs-toggle="pill"
                                                data-bs-target="#register" type="button" role="tab">Регистрация
                                        </button>
                                    </li>
                                </ul>


                                <div class="tab-content" id="pills-tabContent">
                                    <!-- Вход -->
                                    <div class="tab-pane fade show active" id="login" role="tabpanel">
                                        <form>
                                            <div class="mb-3">
                                                <label class="form-label">Email</label>
                                                <input type="email" class="form-control form-control-lg"
                                                       placeholder="Введите email">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Пароль</label>
                                                <input type="password" class="form-control form-control-lg"
                                                       placeholder="Введите пароль">
                                            </div>
                                            <button type="submit" class="btn btn-primary w-100 py-2 rounded-3">Войти
                                            </button>
                                        </form>
                                    </div>


                                    <div class="tab-pane fade" id="register" role="tabpanel">
                                        <form action="/auth/register" method="post">
                                            <div class="mb-3">
                                                <label class="form-label">Имя</label>
                                                <input
                                                        type="text"
                                                        class="form-control form-control-lg"
                                                        name="name"
                                                        placeholder="Введите имя"
                                                        required
                                                >
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Email</label>
                                                <input
                                                        type="email"
                                                        class="form-control form-control-lg"
                                                        name="email"
                                                        placeholder="Введите email"
                                                        required
                                                >
                                            </div>
                                            <div class="dropdown">
                                                <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownRoleButton" data-bs-toggle="dropdown" aria-expanded="false">
                                                    Выберите роль
                                                </button>
                                                <ul class="dropdown-menu" aria-labelledby="dropdownRoleButton">
                                                    <li><a class="dropdown-item" href="#" onclick="selectRole('Соискатель')">Соискатель</a></li>
                                                    <li><a class="dropdown-item" href="#" onclick="selectRole('Работодатель')">Работодатель</a></li>
                                                </ul>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Пароль</label>
                                                <input
                                                        type="password"
                                                        class="form-control form-control-lg"
                                                        name="password"
                                                        placeholder="Введите пароль"
                                                        required
                                                >
                                            </div>
                                            <button type="submit" class="btn btn-success w-100 py-2 rounded-3">
                                                Зарегистрироваться
                                            </button>
                                        </form>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <style>

                    .dropdown .btn {
                        border-radius: 1rem;
                        padding: 10px 24px;
                        font-size: 1.1rem;
                        background-color: #0d6efd;
                        border: none;
                        transition: background-color 0.3s ease;
                    }

                    .dropdown .btn:hover,
                    .dropdown .btn:focus {
                        background-color: #0b5ed7;
                        box-shadow: 0 0 8px rgba(13,110,253,0.6);
                    }


                    .dropdown-menu {
                        border-radius: 1rem;
                        padding: 0;
                        animation: fadeInUp 0.3s ease-in-out;
                        min-width: 200px;
                    }


                    .dropdown-item {
                        padding: 10px 20px;
                        font-size: 1rem;
                        border-radius: 0.75rem;
                        transition: background-color 0.2s ease;
                    }

                    .dropdown-item:hover,
                    .dropdown-item:focus {
                        background-color: #0d6efd;
                        color: #fff;
                    }


                    @keyframes fadeInUp {
                        from {
                            opacity: 0;
                            transform: translateY(10px);
                        }
                        to {
                            opacity: 1;
                            transform: translateY(0);
                        }
                    }

                    .modal-content {
                        background: #fff;
                        border-radius: 1rem;
                        animation: fadeInUp 0.3s ease-in-out;
                    }

                    .nav-pills .nav-link.active {
                        background-color: #0d6efd;
                        border-radius: 30px;
                    }

                    .nav-pills .nav-link {
                        border-radius: 30px;
                        padding: 8px 20px;
                    }

                    @keyframes fadeInUp {
                        from {
                            transform: translateY(20px);
                            opacity: 0;
                        }
                        to {
                            transform: translateY(0);
                            opacity: 1;
                        }
                    }
                </style>
            </div>


            <#assign known = SPRING_SECURITY_CONTEXT?? >
            <#if known>
                <#assign
                user = SPRING_SECURITY_CONTEXT.authentication.principal
                name = user.getUsername
                >
                <#if user??>
                    <a href="/profile/${name}" class="text-light m-2 m-sm-0">
                        Hello, ${name}!
                    </a>
                    <form action="/logout" method="post">
                        <button type="submit" class="btn btn-outline-info m-2"
                                id="logout">
                            logout
                        </button>
                    </form>
                <#else>
                    <a href="/auth/login"
                       class="btn btn-outline-info m-2">Login</a>
                    <a href="/auth/register"
                       class="btn btn-outline-info m-2">Register</a>
                </#if>
            </#if>
        </div>
    </nav>

    <div class="container mt-4">
        <#nested>
    </div>
    </body>
    </html>
</#macro>