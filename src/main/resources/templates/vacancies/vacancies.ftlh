
<#--<#import "../layout.ftlh" as main>-->
<#--<#import "/spring.ftl" as spring>-->
<#--<#import "../pages/pages.ftlh" as p>-->
<#--<@main.layout>-->

<#--    <select class="form-select" aria-label="Default select example" onchange="window.location=this.value">-->
<#--        <option selected><@spring.message"resumes.sorting"/></option>-->
<#--        <option value="/vacancies/sorted?sort=title"><@spring.message"resumes.sort.name"/></option>-->
<#--        <option value="/vacancies/sorted?sort=createdDate"><@spring.message"resumes.sort.data"/></option>-->
<#--        <option value="/vacancies/sorted?sort=response"><@spring.message"resumes.sort.response"/></option>-->
<#--    </select>-->


<#--    <@p.pager url page/>-->
<#--    <table class="table table-success table-striped">-->
<#--        <thead>-->
<#--        <tr>-->
<#--            <th>#</th>-->
<#--            <th><@spring.message"vacancies.vacancies_title"/></th>-->
<#--            <th><@spring.message"vacancies.description"/></th>-->
<#--            <th><@spring.message"vacancies.category"/></th>-->
<#--            <th><@spring.message"vacancies.salary"/></th>-->
<#--            <th><@spring.message"vacancies.employer"/></th>-->


<#--        </tr>-->
<#--        </thead>-->
<#--        <tbody>-->
<#--        <#list vacancy as vacancy>-->
<#--            <tr>-->
<#--                <td>${vacancy?index + 1}</td>-->
<#--                <td>${vacancy.title}</td>-->
<#--                <td>${vacancy.description}</td>-->
<#--                <td>${vacancy.category.name}</td>-->
<#--                <td>${vacancy.salary}</td>-->
<#--                <td>${vacancy.employer.name}</td>-->
<#--&lt;#&ndash;                <td>${vacancy.createdDate}</td>&ndash;&gt;-->
<#--&lt;#&ndash;                <td>${vacancy.updateDate}</td>&ndash;&gt;-->
<#--            </tr>-->
<#--        </#list>-->
<#--        </tbody>-->
<#--    </table>-->
<#--</@main.layout>-->
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>

<@main.layout>

    <!-- Панель фильтрации -->
    <form id="filterForm" class="mb-3">
        <div class="row">
            <!-- Зарплата от -->
            <div class="col">
                <input type="number" id="salaryFrom" name="salaryFrom"
                       class="form-control" placeholder="Зарплата от">
            </div>

            <!-- Зарплата до -->
            <div class="col">
                <input type="number" id="salaryTo" name="salaryTo"
                       class="form-control" placeholder="Зарплата до">
            </div>

            <!-- Категория -->
            <div class="col">
                <select id="category" name="category" class="form-select">
                    <option value="">Все категории</option>
                    <#list vacancy as cat>
                        <option value="${cat.category.id}">${cat.category.name}</option>
                    </#list>
                </select>
            </div>

            <!-- Активность -->
            <div class="col">
                <select id="isActive" name="isActive" class="form-select">
                    <option value="">Все</option>
                    <option value="true">Активные</option>
                    <option value="false">Неактивные</option>
                </select>
            </div>

            <!-- Сортировка -->
            <div class="col">
                <select id="sort" name="sort" class="form-select">
                    <option value="">Без сортировки</option>
                    <option value="title">По названию</option>
                    <option value="createdDate">По дате создания</option>
                    <option value="response">По откликам</option>
                </select>
            </div>

            <!-- Кнопки -->
            <div class="col">
                <button type="submit" class="btn btn-primary">Применить</button>
                <button type="button" id="resetFilter" class="btn btn-secondary">Сбросить</button>
            </div>
        </div>
    </form>

    <!-- Пагинация -->
    <@p.pager url page/>

    <!-- Таблица вакансий -->
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th><@spring.message "vacancies.vacancies_title"/></th>
            <th><@spring.message "vacancies.description"/></th>
            <th><@spring.message "vacancies.category"/></th>
            <th><@spring.message "vacancies.salary"/></th>
            <th><@spring.message "vacancies.employer"/></th>
        </tr>
        </thead>
        <tbody>
        <#list vacancy as v>
            <tr>
                <td>${v?index + 1}</td>
                <td>${v.title}</td>
                <td>${v.description}</td>
                <td>${v.category.name}</td>
                <td>${v.salary}</td>
                <td>${v.employer.name}</td>
            </tr>
        </#list>
        </tbody>
    </table>

    <!-- Скрипт для фильтра и сортировки -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.getElementById("filterForm");
            const resetBtn = document.getElementById("resetFilter");

            // Восстановление фильтра и сортировки из localStorage
            const savedFilter = JSON.parse(localStorage.getItem("vacancyFilter"));
            if (savedFilter) {
                for (const key in savedFilter) {
                    if (form.elements[key]) {
                        form.elements[key].value = savedFilter[key];
                    }
                }
            }

            // Применение фильтра + сортировки
            form.addEventListener("submit", function (e) {
                e.preventDefault();
                const filterData = {};
                const params = new URLSearchParams();

                for (const el of form.elements) {
                    if (el.name && el.value) {
                        filterData[el.name] = el.value;
                        params.append(el.name, el.value);
                    }
                }

                // Сохраняем фильтр и сортировку в localStorage
                localStorage.setItem("vacancyFilter", JSON.stringify(filterData));

                // Перенаправление на URL с параметрами фильтра + сортировки
                window.location = "/vacancies?" + params.toString();
            });

            // Сброс фильтра
            resetBtn.addEventListener("click", function () {
                localStorage.removeItem("vacancyFilter");
                form.reset();
                window.location = "/vacancies";
            });
        });
    </script>


</@main.layout>
