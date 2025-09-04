<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f0f0f0;
        margin: 0;
        padding: 0;
    }

    .profile-container {
        max-width: 900px;
        background: white;
        border-radius: 16px;
        box-shadow: 0 12px 28px rgba(0, 0, 0, 0.12);
        margin: 40px auto;
        padding: 30px;
        position: relative;
    }

    .profile-header {
        display: flex;
        align-items: center;
        gap: 30px;
        position: relative;
    }

    .profile-photo {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        background: #ddd url('https://via.placeholder.com/100') center center no-repeat;
        background-size: cover;
    }

    .profile-info {
        flex-grow: 1;
    }

    .profile-info h1 {
        margin: 0;
        font-size: 1.5rem;
    }

    .profile-info p {
        margin: 5px 0;
    }

    .btn {
        display: inline-block;
        font-weight: 600;
        padding: 10px 20px;
        font-size: 0.9rem;
        border-radius: 10px;
        cursor: pointer;
        border: none;
        transition: background-color 0.3s ease;
        user-select: none;
    }

    .btn-edit {
        background-color: #6a11cb;
        color: white;
        margin-top: 10px;
    }

    .btn-edit:hover {
        background-color: #520da0;
    }

    .profile-actions {
        display: flex;
        flex-direction: column;
        gap: 10px;
        position: absolute;
        top: 20px;
        right: 20px;
    }

    .btn-create {
        background: linear-gradient(90deg, #2575fc, #6a11cb);
        color: white;
        padding: 15px 25px;
        text-decoration: none;
    }

    .btn-create:hover {
        background: linear-gradient(90deg, #6a11cb, #2575fc);
    }

    .btn-feedbacks {
        background-color: #e0e0e0;
        color: #333;
        padding: 15px 25px;
    }

    .btn-feedbacks:hover {
        background-color: #cacaca;
    }

    .resumes {
        margin-top: 40px;
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
    }

    .resume-card {
        background: #f9f9f9;
        padding: 15px;
        border-radius: 12px;
        width: 250px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
    }

    .resume-card p {
        margin: 5px 0;
    }

    .resume-card .card-actions {
        margin-top: 10px;
        display: flex;
        gap: 10px;
    }

    .btn-update {
        background-color: #a0c4ff;
        color: #000;
        padding: 8px 12px;
    }

    .btn-update:hover {
        background-color: #79aaff;
    }

    .btn-red {
        background-color: #b5e7a0;
        color: #000;
        padding: 8px 12px;
    }

    .btn-red:hover {
        background-color: #95d180;
    }

    .profile-title {
        text-align: center;
        color: red;
        margin: 0 0 20px 0;
        font-size: 2rem;
    }
</style>

<div class="profile-container">
    <h1 class="profile-title">
        <#if isApplicant>Личный кабинет Соискателя</#if>
        <#if isEmployer>Личный кабинет Работодателя</#if>
    </h1>

    <div class="profile-header">
<#--        <div class="profile-photo">-->


<#--        </div>-->

        <div class="profile-photo">
            <img src="/auth/download/${user.id}"
                 alt="Avatar"
                 class="avatar-preview"
                 style="width:100px; height:100px; border-radius:50%;">
        </div>




        <#--        <div class="profile-photo">-->
<#--            <img src="/images/image/${user.avatar}"-->
<#--                 alt="Avatar"-->
<#--                 class="avatar-preview">-->
<#--        </div>-->




        <div class="profile-info">




            <h2>${user.name} ${user.surname}</h2>
            <p><strong>Возраст:</strong> ${user.age}</p>
            <#--            <button class="btn btn-edit">Редактировать</button>-->
            <a class="btn btn-create" href="/auth/update">Редактировать</a>
        </div>

        <div class="profile-actions">
            <#if isApplicant>
                <a class="btn btn-create" href="/resumes/create">Создать резюме</a>
                <button class="btn btn-feedbacks">Отклики</button>
            </#if>

            <#if isEmployer>
                <a class="btn btn-create" href="/vacancies/create">Создать вакансию</a>
                <button class="btn btn-feedbacks">Отклики</button>
            </#if>
        </div>
    </div>

    <#if isApplicant>
        <h2>Резюме:</h2>
        <div class="resumes">
            <#list resumes as resume>
                <div class="resume-card">
                    <p><strong>${resume.name}</strong></p>
                    <p>Дата обновления: ${resume.updateDate.toLocalDate()}</p>
                    <div class="card-actions">
                        <a class="btn btn-update" href="/resumes/${resume.id}/edit">Обновить</a>
                        <a class="btn btn-red" href="/resumes/${resume.id}/delete">Удалить</a>
                    </div>
                </div>
            </#list>
        </div>
    </#if>

    <#if isEmployer>
        <h2>Вакансии:</h2>
        <div class="resumes">
            <#list vacancies as vacancy>
                <div class="resume-card">
                    <p><strong>${vacancy.title}</strong></p>
                    <p>Дата обновления: ${vacancy.updateDate.toLocalDate()}</p>
                    <div class="card-actions">
                        <a class="btn btn-update" href="/vacancies/${vacancy.id}/edit">Обновить</a>
                        <a class="btn btn-red" href="/vacancies/${vacancy.id}/delete">Удалить</a>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</div>

