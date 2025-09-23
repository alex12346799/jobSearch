


<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>

<@main.layout>


    <div class="filters">
        <select id="filterCategory" class="form-select">
            <option value="">Все категории</option>
            <#list categories as cat>
                <option value="${cat.id}">${cat.name}</option>
            </#list>
        </select>

        <input id="salaryFrom" type="number" placeholder="от зарплаты">
        <input id="salaryTo" type="number" placeholder="до зарплаты">
        <button id="applyFilter" class="btn btn-primary">Применить фильтр</button>
        <button id="clearFilter" class="btn btn-secondary">Очистить фильтры</button>
    </div>

    <@p.pager url page/>

    <table class="table table-success table-striped" id="vacanciesTable">
        <thead>
        <tr>
            <th>#</th>
            <th><@spring.message "vacancies.vacancies_title"/></th>
            <th><@spring.message "vacancies.description"/></th>
            <th><@spring.message "vacancies.category"/></th>
            <th><@spring.message "vacancies.salary"/></th>
            <th><@spring.message "vacancies.employer"/></th>
            <th><@spring.message "vacancies.more"/></th>

        </tr>
        </thead>
        <tbody>
        <#list vacancy as v>
            <tr data-category="${v.category.id}" data-salary="${v.salary?string("0.##")}">
                <td>${v?index + 1}</td>
                <td>${v.title}</td>
                <td>${v.description}</td>
                <td>${v.category.name}</td>
                <td>${v.salary}</td>
                <td>${v.employer.name}</td>
                <td><a href="vacancies/${v.id}">More</a></td>
            </tr>
        </#list>
        </tbody>
    </table>


    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const table = document.getElementById('vacanciesTable');
            const rows = Array.from(table.querySelectorAll('tbody tr'));
            const categorySelect = document.getElementById('filterCategory');
            const salaryFromInput = document.getElementById('salaryFrom');
            const salaryToInput = document.getElementById('salaryTo');
            const applyBtn = document.getElementById('applyFilter');
            const clearBtn = document.getElementById('clearFilter');


            const savedFilter = JSON.parse(localStorage.getItem('vacancyFilter')) || {};
            if(savedFilter.category) categorySelect.value = savedFilter.category;
            if(savedFilter.salaryFrom) salaryFromInput.value = savedFilter.salaryFrom;
            if(savedFilter.salaryTo) salaryToInput.value = savedFilter.salaryTo;

            function applyFilter() {
                const category = categorySelect.value;
                const salaryFrom = salaryFromInput.value ? parseFloat(salaryFromInput.value) : null;
                const salaryTo = salaryToInput.value ? parseFloat(salaryToInput.value) : null;


                localStorage.setItem('vacancyFilter', JSON.stringify({category, salaryFrom, salaryTo}));

                rows.forEach(row => {
                    const rowCategory = row.dataset.category;
                    const rowSalary = parseFloat(row.dataset.salary);

                    let show = true;
                    if(category && String(rowCategory) !== String(category)) show = false;
                    if(salaryFrom !== null && rowSalary < salaryFrom) show = false;
                    if(salaryTo !== null && rowSalary > salaryTo) show = false;

                    row.style.display = show ? '' : 'none';
                });
            }

            function clearFilter() {
                categorySelect.value = '';
                salaryFromInput.value = '';
                salaryToInput.value = '';
                localStorage.removeItem('vacancyFilter');
                rows.forEach(row => row.style.display = '');
            }


            applyFilter();


            applyBtn.addEventListener('click', applyFilter);
            clearBtn.addEventListener('click', clearFilter);
        });

    </script>



</@main.layout>
