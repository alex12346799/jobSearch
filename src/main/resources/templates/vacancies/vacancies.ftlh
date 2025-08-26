
<#import "../layout.ftlh" as main>
<#import "../pages/pages.ftlh" as p>
<@main.layout>

    <select class="form-select" aria-label="Default select example" onchange="window.location=this.value">
        <option selected>Open this select menu</option>
        <option value="/vacancies/sorted?sort=title">Имя</option>
        <option value="/vacancies/sorted?sort=createdDate">Дата</option>
        <option value="/vacancies/sorted?sort=response">Отклик</option>
    </select>

<#--    <nav aria-label="Page navigation example">-->
<#--        <ul class="pagination">-->
<#--            <li class="page-item"><a class="page-link" href="/vacancies/sorted?page=0&size=3">Previous</a></li>-->
<#--            <li class="page-item"><a class="page-link" href="/vacancies/sorted?page=1&size=3">1</a></li>-->
<#--            <li class="page-item"><a class="page-link" href="/vacancies/sorted?page=2&size=3">2</a></li>-->
<#--            <li class="page-item"><a class="page-link" href="/vacancies/sorted?page=3&size=3">3</a></li>-->
<#--            <li class="page-item"><a class="page-link" href="/vacancies/sorted?page=4&size=3">Next</a></li>-->
<#--        </ul>-->
<#--    </nav>-->
    <@p.pager url page/>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>заголовок</th>
            <th>описание</th>
            <th>категории</th>
            <th>зарплата</th>
            <th>employear name</th>
<#--            <th>дата создания</th>-->
<#--            <th>дата обновления</th>-->

        </tr>
        </thead>
        <tbody>
        <#list vacancy as vacancy>
            <tr>
                <td>${vacancy?index + 1}</td>
                <td>${vacancy.title}</td>
                <td>${vacancy.description}</td>
                <td>${vacancy.category.name}</td>
                <td>${vacancy.salary}</td>
                <td>${vacancy.employer.name}</td>
<#--                <td>${vacancy.createdDate}</td>-->
<#--                <td>${vacancy.updateDate}</td>-->
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>
