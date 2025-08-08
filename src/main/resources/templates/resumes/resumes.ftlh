<#import "../layout.ftlh" as main>
<@main.layout>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>название</th>
            <th>зарплата</th>
            <th>дата начала</th>
            <th>имя</th>
            <th>Актуально</th>
            <th>категория</th>

        </tr>
        </thead>
        <tbody>
        <#list resumes as resume>
            <tr>
                <td>${resume?index + 1}</td>
                <td>${resume.name}</td>
                <td>${resume.salary}</td>
                <td>${resume.createdDate}</td>
                <td>${resume.applicantName}</td>
                <td>${resume.updateDate}</td>
                <td>${resume.categoryName}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>


