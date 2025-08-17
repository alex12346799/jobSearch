

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
            box-shadow: 0 12px 28px rgba(0,0,0,0.12);
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
        .btn-edit:hover { background-color: #520da0; }

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
            box-shadow: 0 4px 8px rgba(0,0,0,0.08);
        }

        .resume-card p {
            margin: 5px 0;
        }

        .resume-card .card-actions {
            margin-top: 10px;
            display: flex;
            gap: 10px;
        }

        .btn-update { background-color: #a0c4ff; color: #000; padding: 8px 12px; }
        .btn-update:hover { background-color: #79aaff; }

        .btn-red { background-color: #b5e7a0; color: #000; padding: 8px 12px; }
        .btn-red:hover { background-color: #95d180; }
    </style>



<div class="profile-container">
    <div class="profile-header">
        <div class="profile-photo"></div>
        <div class="profile-info">
            <h2>${vaca}</h2>
            <h1>${user.name} ${user.surname}</h1>
            <p><strong>Возраст:</strong> ${user.age}</p>
            <button class="btn btn-edit">Редактировать</button>
        </div>

        <div class="profile-actions">
            <a class="btn btn-create" href="/resumes/create">Создать резюме</a>
            <button class="btn btn-create">Создать резюме</button>
            <button class="btn btn-feedbacks">Отклики <span style="background:red;color:white;border-radius:50%;padding:2px 6px;">5</span></button>
        </div>
    </div>

<#--    <h2>Резюме:</h2>-->
<#--    <div class="resumes">-->
<#--        <#list resumes as resume>-->
<#--            <div class="resume-card">-->
<#--                <p><strong>${resume.title}</strong></p>-->
<#--                <p>Дата обновления: ${resume.updatedAt?string("dd/MM/yyyy")}</p>-->
<#--                <div class="card-actions">-->
<#--                    <button class="btn btn-update">Обновить</button>-->
<#--                    <button class="btn btn-red">Ред.</button>-->
<#--                </div>-->
<#--            </div>-->
<#--        </#list>-->
    </div>



