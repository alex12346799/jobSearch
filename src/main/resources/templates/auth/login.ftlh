<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <section class=" text-center text-lg-start">
        <div class="card m-3">
            <div class="row g-0 d-flex align-items-center">
                <div class="col-lg-4 d-none d-lg-flex">
                    <img src="https://mdbootstrap.com/img/new/ecommerce/vertical/004.jpg" alt="Trendy Pants and Shoes"
                         class="w-100 rounded-t-5 rounded-tr-lg-0 rounded-bl-lg-5"/>
                </div>
                <div class="col-lg-8">
                    <div class="card-body py-5 px-md-5">
                        <#if error>
                            <h2><@spring.message"login.Incorrect.login_or_password.Please_re-enter"/></h2>
                        </#if>

                        <form id="login-form" action="/login" method="post">

                            <#if _csrf??>
                                <input
                                        type="hidden"
                                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                                        value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>


                            <!-- Email input -->
                            <div data-mdb-input-init class="form-outline mb-4">
                                <input type="email" id="form2Example1" class="form-control" name="username"/>
                                <label class="form-label" for="form2Example1"><@spring.message"login.email"/></label>
                            </div>

                            <!-- Password input -->
                            <div data-mdb-input-init class="form-outline mb-4">
                                <input type="password" id="form2Example2" class="form-control" name="password"/>
                                <label class="form-label" for="form2Example2"><@spring.message"login.password"/></label>
                            </div>
                            <!-- Submit button -->
                            <button type="submit" data-mdb-button-init data-mdb-ripple-init
                                    class="btn btn-primary btn-block mb-4"><@spring.message"login.sign_up"/>
                            </button>

<#--                            <a href="/auth/forgot-password">Forgot password?</a>-->
                            <div class="text-center mt-3">
                                <a href="/auth/forgot-password" class="btn btn-outline-primary btn-sm">
                                    <@spring.message"login.forgot_your_password"/>?
                                </a>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </section>
</@main.layout>