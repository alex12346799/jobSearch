
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <head>
        <meta charset="UTF-8">
        <title><@spring.message "createVacancies.creating_a_vacancy"/></title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
            }

            .container {
                width: 480px;
                margin: 40px auto;
                background: #ffffff;
                border-radius: 12px;
                padding: 25px 30px;
                box-shadow: 0 4px 14px rgba(0, 0, 0, 0.12);
                border: 1px solid #eeeeee;
            }


            h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #333;
                font-size: 22px;
            }

            label {
                display: block;
                margin-top: 12px;
                font-weight: bold;
                color: #444;
            }

            input[type="text"], input[type="number"], select, textarea {
                width: 100%;
                padding: 8px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }

            textarea {
                height: 90px;
                resize: vertical;
            }

            .experience {
                display: flex;
                gap: 10px;
                align-items: center;
                margin-top: 5px;
            }

            .experience input {
                width: 80px;
            }

            .checkbox-block {
                display: flex;
                align-items: center;
                gap: 8px;
                margin-top: 15px;
            }

            .checkbox-block label {
                margin: 0;
                font-weight: normal;
            }
            .page-wrapper {
                display: flex;
                justify-content: center;
                padding-top: 40px;
            }


            button {
                width: 100%;
                margin-top: 20px;
                padding: 12px;
                border: none;
                border-radius: 6px;
                background-color: #26a69a;
                color: white;
                font-size: 16px;
                cursor: pointer;
            }

            button:hover {
                background-color: #1f8f84;
            }
            .form-container {
                max-width: 600px;
                margin: 40px auto;
                background: #ffffff;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }

            .form-container h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            .form-container input,
            .form-container select {
                width: 100%;
                padding: 12px;
                margin-bottom: 15px;
                border-radius: 8px;
                border: 1px solid #ccc;
                font-size: 15px;
            }

            .form-container label {
                font-weight: 600;
                margin-bottom: 5px;
                display: block;
            }

            button {
                width: 100%;
                padding: 12px;
                background: #1aa38e;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                color: #fff;
                cursor: pointer;
            }

            body > .container {
                display: flex !important;
                justify-content: center !important;
                align-items: flex-start !important;
                background: transparent !important;
                box-shadow: none !important;
                border: none !important;
                padding-top: 40px !important;
                max-width: 100% !important;
            }



            button:hover {
                background: #148f7a;
            }

        </style>
    </head>

    <body>
    <div class="page-wrapper">
    <div class="container">
        <h2><@spring.message "createVacancies.creating_a_vacancy"/></h2>

        <form action="/vacancies/create" method="post">

            <#if _csrf??>
                <input type="hidden"
                       name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <input type="hidden" name="employerId" value="${employerId!}">


            <label><@spring.message "createVacancies.job_title"/>:</label>
            <@spring.formInput "vacancyRequestDto.title" 'class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />

            <label><@spring.message "createVacancies.category"/>:</label>
            <select name="categoryId">
                <option value=""><@spring.message "createVacancies.select_category"/></option>
                <#list categories as category>
                    <option value="${category.id}"
                            <#if vacancy?has_content && vacancy.categoryId?string == category.id?string>selected</#if>>
                        ${category.name}
                    </option>
                </#list>
            </select>

            <label><@spring.message "createVacancies.wages"/>:</label>
            <@spring.formInput "vacancyRequestDto.salary" 'class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />

            <label><@spring.message "createVacancies.job_description"/>:</label>
            <@spring.formInput "vacancyRequestDto.description" 'class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />

            <label><@spring.message "createVacancies.required_experience"/>:</label>
            <div class="experience">
                <div>
                    <@spring.message "createVacancies.from"/>
                    <input type="number" name="expFrom" min="0" value="${vacancyRequestDto.expFrom!}">
                </div>

                <div>
                    <@spring.message "createVacancies.to"/>
                    <input type="number" name="expTo" min="0" value="${vacancyRequestDto.expTo!}">
                </div>
            </div>

            <div class="checkbox-block">
                <input type="checkbox"
                       name="isActive"
                       value="true"
                       <#if vacancyRequestDto.isActive?? && vacancyRequestDto.isActive>checked</#if>>
                <label><@spring.message "createVacancies.actively"/></label>
            </div>

            <button type="submit"><@spring.message "createVacancies.publish"/></button>

        </form>
    </div>
    </div>
    </body>

</@main.layout>
