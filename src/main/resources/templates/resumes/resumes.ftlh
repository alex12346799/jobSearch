<#import "../layout.ftlh" as main>
<@main.layout>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>название</th>
            <th>зарплата</th>
            <th>дата создания</th>
            <th>имя</th>
            <th>дата обновления</th>
            <th>категория</th>
            <th></th>

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
                <td><a href="resumes/${resume.id}">More</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>


