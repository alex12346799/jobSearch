<#import "spring.ftl" as spring>
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
                        <a class="nav-link active" aria-current="page" href="resumes"><@spring.message "layout.resumes"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="vacancies"><@spring.message "layout.vacancies"/></a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/auth/profile"><@spring.message "layout.Personal_account"/></a>
                    </li>




                </ul>




                <div class="ms-3 d-flex align-items-center">


                    <a href="/?lang=ru" class="text-decoration-none me-2">
                        <img src="/images/ru.png" alt="Русский" width="24" height="24">
                    </a>

                    <a href="/?lang=en" class="text-decoration-none">
                        <img src="/images/en.png" alt="English" width="24" height="24">
                    </a>
                </div>


                <#assign known = SPRING_SECURITY_CONTEXT?? >
            <#if known>
                <#assign
                user = SPRING_SECURITY_CONTEXT.authentication.principal
                name = user.getUsername()
                >
                <#if user??>
<#--                    <a href="/profile/${name}" class="text-light m-2 m-sm-0">-->
<#--                        <@spring.message "layout.hello"/>, ${name}-->

<#--                        <#list user.authorities as auth>-->
<#--                            <span class="text-danger">${auth}</span>-->
<#--                            <#if auth?has_next>-->
<#--                                /-->
<#--                            </#if>-->
<#--                        </#list>-->
<#--                    </a>-->


                    <form action="/logout" method="post">
                        <#if _csrf??>
                            <input
                                    type="hidden"
                                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                                    value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <button type="submit" class="btn btn-outline-info m-2"
                                id="logout">
                            <@spring.message "layout.logout"/>
                        </button>
                    </form>
                </#if>

            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2"><@spring.message "layout.login"/></a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2"><@spring.message "layout.register"/></a>
            </#if>


        </div>
        </div>
    </nav>


    <div class="container">
        <#nested spring>
    </div>
    </body>

</html>

</#macro>