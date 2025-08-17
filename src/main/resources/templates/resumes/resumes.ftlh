<#import "../layout.ftlh" as main>
<@main.layout>
    <select class="form-select" aria-label="Default select example" onchange="window.location=this.value">
        <option selected>Open this select menu</option>
        <option value="/resumes/sorted?sort=name">Имя</option>
        <option value="/resumes/sorted?sort=createdDate">Дата</option>
        <option value="/resumes/sorted?sort=salary">Зарплата</option>
        <option value="/resumes/sorted?sort=response">Отклик</option>
    </select>

    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="/resumes/sorted?page=0&size=3">Previous</a></li>
            <li class="page-item"><a class="page-link" href="/resumes/sorted?page=1&size=3">1</a></li>
            <li class="page-item"><a class="page-link" href="/resumes/sorted?page=2&size=3">2</a></li>
            <li class="page-item"><a class="page-link" href="/resumes/sorted?page=3&size=3">3</a></li>
            <li class="page-item"><a class="page-link" href="/resumes/sorted?page=4&size=3">Next</a></li>
        </ul>
    </nav>

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


