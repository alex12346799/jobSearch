<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>
<@main.layout>
    <select class="form-select" aria-label="Default select example" onchange="window.location=this.value">
        <option selected><@spring.message"resumes.sorting"/></option>
        <option value="/resumes/sorted?sort=name"><@spring.message"resumes.sort.name"/></option>
        <option value="/resumes/sorted?sort=createdDate"><@spring.message"resumes.sort.data"/></option>
        <option value="/resumes/sorted?sort=salary"><@spring.message"resumes.sort.salary"/></option>
        <option value="/resumes/sorted?sort=response"><@spring.message"resumes.sort.response"/></option>
    </select>


<@p.pager url page/>
    <table class="table table-success table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th><@spring.message"resumes.th.resume_title"/></th>
            <th><@spring.message"resumes.th.salary"/></th>
            <th><@spring.message"resumes.th.creation_date"/></th>
            <th><@spring.message"resumes.th.name"/></th>
            <th><@spring.message"resumes.th.update_date"/></th>
            <th><@spring.message"resumes.th.category"/></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <#list page.content as resume>
            <tr>
                <td>${resume?index + 1}</td>
                <td>${resume.name}</td>
                <td>${resume.salary}</td>
                <td>${resume.createdDate}</td>
                <td>${resume.applicant.name}</td>
                <td>${resume.updateDate}</td>
                <td>${resume.category.name}</td>
                <td><a href="resumes/${resume.id}">More</a></td>
            </tr>
        </#list>
        </tbody>
    </table>

</@main.layout>


