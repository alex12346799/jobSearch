
<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="card text-center">
        <div class="card-header">
            ${resume.name}
        </div>
        <div class="card-body">
            <h5 class="card-title">${resume.applicantId}</h5>
            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
        <div class="card-footer text-body-secondary">
            2 days ago
        </div>
    </div>
</@main.layout>
