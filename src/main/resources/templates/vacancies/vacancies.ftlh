
<#import "../layout.ftlh" as main>
<@main.layout>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Salary</th>
            <th>Name</th>
            <th>create date</th>
            <th>Applicant_Id</th>
            <th>Актуально</th>

        </tr>
        </thead>
        <tbody>
        <#list vacancy as vacancy>
            <tr>
                <td>${vacancy?index + 1}</td>
                <td>${vacancy.title}</td>
                <td>${vacancy.description}</td>
                <td>${vacancy.categoryId}</td>
                <td>${vacancy.expFrom}</td>
                <td>${vacancy.expTo}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>
