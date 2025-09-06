
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>

    <head>
        <meta charset="UTF-8">
        <title><@spring.message "createVacancies.creating_a_vacancy"/></title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .container {
                width: 400px;
                margin: 50px auto;
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 20px;
            }
            h2 {
                color: red;
                text-align: center;
            }
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }
            input[type="text"], input[type="number"], select, textarea {
                width: 100%;
                padding: 5px;
                margin-top: 5px;
                box-sizing: border-box;
            }
            textarea {
                height: 80px;
                resize: vertical;
            }
            .experience {
                display: flex;
                align-items: center;
                gap: 5px;
            }
            .experience input {
                width: 50px;
            }
            .checkbox {
                margin-top: 10px;
            }
            button {
                width: 100%;
                background-color: #b2dfdb;
                border: none;
                padding: 10px;
                margin-top: 15px;
                cursor: pointer;
                font-size: 16px;
            }
            button:hover {
                background-color: #80cbc4;
            }
        </style>
    </head>

    <body>
    <div class="container">
        <h2><@spring.message "createVacancies.creating_a_vacancy"/>:</h2>

        <form action="/vacancies/create" method="post">

            <#if _csrf??>
                <input type="hidden"
                       name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <input type="hidden" name="employerId" value="${employerId!}">



            <div data-mdb-input-init class="form-outline mb-4">
                <label class="form-label" for="form3Example3cg"><@spring.message "createVacancies.job_title"/>:</label>
                <@spring.formInput "vacancyRequestDto.title" 'id="form3Example3cg" class="form-control form-control-lg"' />
                <@spring.showErrors "<br>" "error text-danger" />
            </div>

            <!-- Категория -->
            <label><@spring.message "createVacancies.category"/>:
                <select name="categoryId" >
                    <option value=""><@spring.message "createVacancies.select_category"/></option>
                    <#list categories as category>
                        <option value="${category.id}" <#if vacancy?has_content && vacancy.categoryId?? && (vacancy.categoryId?string == category.id?string)>selected</#if>>
                            ${category.name}
                        </option>
                    </#list>
                </select>
            </label>



            <div data-mdb-input-init class="form-outline mb-4">
                <label class="form-label" for="form3Example3cg1"><@spring.message "createVacancies.wages"/>:</label>
                <@spring.formInput "vacancyRequestDto.salary" 'id="form3Example3cg1" class="form-control form-control-lg"' />
                <@spring.showErrors "<br>" "error text-danger" />
            </div>



            <div data-mdb-input-init class="form-outline mb-4">
                <label class="form-label" for="form3Example3cg1"><@spring.message "createVacancies.job_description"/>:</label>
                <@spring.formInput "vacancyRequestDto.description" 'id="form3Example3cg1" class="form-control form-control-lg"' />
                <@spring.showErrors "<br>" "error text-danger" />
            </div>


            <!-- Опыт работы -->
            <label><@spring.message "createVacancies.required_experience"/>:</label>
            <div class="experience">
                <@spring.message "createVacancies.from"/>
                <input id="expFrom" type="number" name="expFrom" min="0" value="${vacancyRequestDto.expFrom!}">
                <@spring.message "createVacancies.to"/>
                <input id="expTo" type="number" name="expTo" min="0" value="${vacancyRequestDto.expTo!}">
            </div>

            <!-- Активность вакансии -->
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="active" <#if vacancyRequestDto.active?? && vacancyRequestDto.active>checked</#if>>

                    <@spring.message "createVacancies.actively"/>
                </label>
            </div>

            <button type="submit"><@spring.message "createVacancies.publish"/></button>
        </form>
    </div>
    </body>

</@main.layout>
