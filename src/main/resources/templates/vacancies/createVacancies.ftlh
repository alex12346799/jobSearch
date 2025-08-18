<#import "../layout.ftlh" as main>
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
        <h2>Создание вакансии:</h2>
        <form action="/vacancies/create" method="post">
            <input type="hidden" name="employerId" value="${employerId!}">
            <label>Название вакансии:
                <input type="text" name="title" placeholder="Введите название" value="${vacancyRequestDto.title!}" required>
            </label>

            <label>Категория:
                <select name="categoryId" required>
                    <option value="">Выберите категорию</option>
                    <#list categories as category>
                        <option value="${category.id}" <#if vacancy?has_content && vacancy.categoryId?? && (vacancy.categoryId?string == category.id?string)>selected</#if>>

                            ${category.name}
                        </option>
                    </#list>
                </select>
            </label>






            <label>Заработная плата:
                <input type="text" id="salary" name="salary"
                       value="${vacancyRequestDto.salary!}" class="form-control" placeholder="Введите зарплату" required>
            </label>

            <label>Описание вакансии:
                <textarea id="description" name="description" class="form-control"
                          placeholder="Введите описание вакансии" required>${vacancyRequestDto.description!}</textarea>
            </label>


<#--            <label>Заработная плата:-->
<#--                <input type="number" name="salary" placeholder="Введите оклад" value="${vacancy.salary!}" required>-->
<#--            </label>-->

<#--            <label>Описание вакансии:-->
<#--                <textarea name="description" placeholder="Введите описание">${vacancy.description!}</textarea>-->
<#--            </label>-->

            <label>Требуемый опыт:</label>
            <div class="experience">
                от <input id="expFrom" type="number" name="expFrom" min="0" value="${vacancyRequestDto.expFrom!}">
                до <input id="expTo" type="number" name="expTo" min="0" value="${vacancyRequestDto.expTo!}">
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" name="active" <#if vacancyRequestDto.isActive()?? && vacancyRequestDto.isActive()>checked</#if>> Активно
                </label>
            </div>

            <button type="submit">Опубликовать</button>
        </form>
    </div>
    </body>


</@main.layout>