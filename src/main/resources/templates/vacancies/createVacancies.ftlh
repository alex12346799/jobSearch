<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>

    <head>
        <meta charset="UTF-8">
        <title>Создание вакансии</title>
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
        <h2><@spring.message"createVacancies.creating_a_vacancy"/>:</h2>
        <form action="/vacancies/create" method="post">


            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}"/>
            </#if>


            <input type="hidden" name="employerId" value="${employerId!}">
            <label><@spring.message"createVacancies.job_title"/>:
                <input type="text" name="title" placeholder="<@spring.message"createVacancies.placeholder.enter_a_name"/>" value="${vacancyRequestDto.title!}" required>
            </label>

            <label><@spring.message"createVacancies.category"/>:
                <select name="categoryId" required>
                    <option value=""><@spring.message"createVacancies.select_category"/></option>
                    <#list categories as category>
                        <option value="${category.id}" <#if vacancy?has_content && vacancy.categoryId?? && (vacancy.categoryId?string == category.id?string)>selected</#if>>

                            ${category.name}
                        </option>
                    </#list>
                </select>
            </label>






            <label><@spring.message"createVacancies.wages"/>:
                <input type="text" id="salary" name="salary"
                       value="${vacancyRequestDto.salary!}" class="form-control" placeholder="<@spring.message"createVacancies.placeholder.enter_salary"/>" required>
            </label>

            <label><@spring.message"createVacancies"/>:
                <textarea id="description" name="description" class="form-control"
                          placeholder="<@spring.message"createVacancies.placeholder.enter_a_job_description"/>" required>${vacancyRequestDto.description!}</textarea>
            </label>


<#--            <label>Заработная плата:-->
<#--                <input type="number" name="salary" placeholder="Введите оклад" value="${vacancy.salary!}" required>-->
<#--            </label>-->

<#--            <label>Описание вакансии:-->
<#--                <textarea name="description" placeholder="Введите описание">${vacancy.description!}</textarea>-->
<#--            </label>-->

            <label><@spring.message"createVacancies.required_experience"/>:</label>
            <div class="experience">
                <@spring.message"createVacancies.from"/> <input id="expFrom" type="number" name="expFrom" min="0" value="${vacancyRequestDto.expFrom!}">
                <@spring.message"createVacancies.to"/> <input id="expTo" type="number" name="expTo" min="0" value="${vacancyRequestDto.expTo!}">
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" name="active" <#if vacancyRequestDto.isActive()?? && vacancyRequestDto.isActive()>checked</#if>> <@spring.message"createVacancies.actively"/>
                </label>
            </div>

            <button type="submit"><@spring.message"createVacancies.publish"/></button>
        </form>
    </div>
    </body>


</@main.layout>