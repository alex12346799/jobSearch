
<#import "../layout.ftlh" as main>
<@main.layout>
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
                <td>${vacancy.categoryName}</td>
                <td>${vacancy.salary}</td>
                <td>${vacancy.employerName}</td>
<#--                <td>${vacancy.createdDate}</td>-->
<#--                <td>${vacancy.updateDate}</td>-->
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>
