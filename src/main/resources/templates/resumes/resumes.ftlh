
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
        <#list resumes as resume>
            <tr>
                <td>${resume?index + 1}</td>
                <td>${resume.salary}</td>
                <td>${resume.name}</td>
                <td>${resume.createdDate}</td>
                <td>${resume.applicantId}</td>
                <td>${resume.updateDate}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>
