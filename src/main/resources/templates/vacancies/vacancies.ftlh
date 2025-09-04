
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>
<@main.layout>

    <select class="form-select" aria-label="Default select example" onchange="window.location=this.value">
        <option selected><@spring.message"resumes.sorting"/></option>
        <option value="/vacancies/sorted?sort=title"><@spring.message"resumes.sort.name"/></option>
        <option value="/vacancies/sorted?sort=createdDate"><@spring.message"resumes.sort.data"/></option>
        <option value="/vacancies/sorted?sort=response"><@spring.message"resumes.sort.response"/></option>
    </select>


    <@p.pager url page/>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th><@spring.message"vacancies.vacancies_title"/></th>
            <th><@spring.message"vacancies.description"/></th>
            <th><@spring.message"vacancies.category"/></th>
            <th><@spring.message"vacancies.salary"/></th>
            <th><@spring.message"vacancies.employer"/></th>


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
