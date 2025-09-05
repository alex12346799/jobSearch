<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout; spring>

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
            box-shadow: 0 10px 30px rgba(0, 0, 0, .2);
            padding: 2rem;
        }

        .auth-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
        }
    </style>

    <div class="auth-bg">
        <div class="auth-card">
            <h2><@spring.message"register.registration"/></h2>
            <form action="/auth/register" method="post">
                <#if _csrf??>
                    <input
                            type="hidden"
                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                            value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <div class="row">


                    <div data-mdb-input-init class="form-outline mb-4">
                        <label class="form-label" for="form3Example3cg"><@spring.message"register.name"/></label>
                        <@spring.formInput "userRequestRegisterDto.name" 'id="form3Example3cg" class="form-control form-control-lg"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>





                    <div data-mdb-input-init class="form-outline mb-4">
                        <label class="form-label" for="form3Example3cg"><@spring.message"register.surname"/></label>
                        <@spring.formInput "userRequestRegisterDto.surname" 'id="form3Example3cg" class="form-control form-control-lg"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>





                <div data-mdb-input-init class="form-outline mb-4">
                    <label class="form-label" for="form3Example3cg"><@spring.message"register.email"/></label>
                    <@spring.formInput "userRequestRegisterDto.email" 'id="form3Example3cg" class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>









                <div data-mdb-input-init class="form-outline mb-4">
                    <label class="form-label" for="form3Example4cg"><@spring.message"register.password"/></label>
                    <@spring.formInput "userRequestRegisterDto.password" 'id="form3Example4cg" class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-3">
                    <label for="role" class="form-label"><@spring.message"register.role"/></label>
                    <select class="form-select" id="role" name="roleId">
                        <option value="" disabled selected><@spring.message"register.select_a_role"/></option>
                        <option value="1"><@spring.message"register.EMPLOYEE"/></option>
                        <option value="2"><@spring.message"register.APPLICANT"/></option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100"><@spring.message"register.create_an_account"/></button>
            </form>

            <div class="text-center mt-3">
                <@spring.message"register.already_have_an_account"/>? <a href="/auth/login"><@spring.message"register.login"/></a>
            </div>
        </div>
    </div>

</@main.layout>
