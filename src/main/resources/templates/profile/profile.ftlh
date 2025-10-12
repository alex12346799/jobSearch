<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f3f3f3;
            margin: 0;
            padding: 0;
        }

        .profile-container {
            max-width: 1100px;
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            margin: 50px auto;
            padding: 40px;
        }

        .profile-title {
            text-align: center;
            color: #d63031;
            font-size: 2rem;
            font-weight: bold;
            margin-bottom: 40px;
        }


        .profile-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 30px;
            flex-wrap: wrap;
        }


        .avatar-section {
            flex: 0 0 150px;
            text-align: center;
        }

        .avatar-preview {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            border: 3px solid #74b9ff;
            object-fit: cover;
            box-shadow: 0 0 15px rgba(116, 185, 255, 0.5);
            transition: 0.3s;
        }

        .avatar-preview:hover {
            transform: scale(1.05);
            box-shadow: 0 0 25px rgba(116, 185, 255, 0.7);
        }

        .profile-info {
            flex: 1;
            min-width: 250px;
        }

        .profile-info h2 {
            margin: 0;
            font-size: 1.6rem;
            color: #2d3436;
        }

        .profile-info p {
            margin: 6px 0;
            color: #333;
            font-size: 1rem;
        }


        .profile-actions {
            display: flex;
            flex-direction: column;
            gap: 10px;
            align-items: flex-end;
            justify-content: center;
        }

        .btn {
            display: inline-block;
            width: 180px;
            text-align: center;
            font-weight: 600;
            padding: 12px 20px;
            font-size: 0.95rem;
            border-radius: 10px;
            cursor: pointer;
            border: none;
            transition: 0.3s;
            text-decoration: none;
            user-select: none;
        }

        .btn-create {
            background: linear-gradient(90deg, #2575fc, #6a11cb);
            color: white;
        }

        .btn-create:hover {
            background: linear-gradient(90deg, #6a11cb, #2575fc);
            transform: scale(1.03);
        }

        .btn-feedbacks {
            background: #e0e0e0;
            color: #333;
        }

        .btn-feedbacks:hover {
            background-color: #cacaca;
        }

        /* ===== Раздел резюме ===== */
        h2.section-title {
            text-align: center;
            font-size: 1.6rem;
            color: #2d3436;
            margin: 50px 0 25px;
        }

        .resumes {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 25px;
            justify-items: center;
            align-items: start;
        }

        .resume-card {
            width: 100%;
            max-width: 250px;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            transition: 0.3s;
        }

        .resume-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 18px rgba(0, 0, 0, 0.15);
        }

        .resume-info p {
            margin: 6px 0;
            color: #333;
            font-size: 0.95rem;
        }


        .card-actions {
            margin-top: 12px;
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }

        .card-actions .btn {
            flex: 1;
            padding: 8px 10px;
            font-size: 0.85rem;
            border-radius: 8px;
            text-align: center;
            font-weight: 600;
        }

        .btn-update {
            background-color: #a0c4ff;
            color: #000;
        }

        .btn-update:hover {
            background-color: #79aaff;
        }

        .btn-red {
            background-color: #b5e7a0;
            color: #000;
        }

        .btn-red:hover {
            background-color: #95d180;
        }

        /* Кнопка "Подробнее" ниже */
        .card-footer {
            margin-top: 15px;
            display: flex;
            justify-content: center;
        }

        .btn-detail {
            width: 100%;
            text-align: center;
            background: linear-gradient(90deg, #2575fc, #6a11cb);
            color: white;
            padding: 10px;
            font-weight: 600;
            border-radius: 8px;
            transition: 0.3s;
        }

        .btn-detail:hover {
            transform: scale(1.03);
        }

        /* ===== Адаптив ===== */
        @media (max-width: 992px) {
            .resumes {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 600px) {
            .resumes {
                grid-template-columns: 1fr;
            }
        }
    </style>

    <div class="profile-container">
        <h1 class="profile-title">
            <#if isApplicant?? && isApplicant>
                <@spring.message "profile.personal_account_of_the_Applicant"/>
            <#elseif isEmployer?? && isEmployer>
                <@spring.message "profile.personal_account_of_the_Employer"/>
            <#else>
                <@spring.message "profile.personal_account"/>
            </#if>
        </h1>


        <div class="profile-header">

            <div class="avatar-section">
                <form id="avatarForm" action="/user/upload-avatar" method="post" enctype="multipart/form-data">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>

                    <label for="avatarInput">
                        <img id="avatarPreview"
                             src="<#if user.avatar?? && user.avatar?has_content>
                                /data/images/${user.avatar}
                              <#else>
                                /images/default-avatar.png
                              </#if>"
                             alt="Avatar"
                             class="avatar-preview">
                    </label>
                    <input type="file" id="avatarInput" name="avatar" accept="image/*" style="display:none"
                           onchange="previewAvatar()">
                </form>
            </div>


            <div class="profile-info">
                <h2>${user.name!''} ${user.surname!''}</h2>
                <p><strong><@spring.message "profile.age"/>:</strong> ${user.age!'—'}</p>
                <p><strong>Email:</strong> ${user.email!''}</p>
                <p><strong><@spring.message "profile.address"/>:</strong> ${user.address!'Не указан'}</p>
                <a class="btn btn-create" href="/user/edit">
                    <@spring.message "profile.edit"/>
                </a>
            </div>


            <div class="profile-actions">
                <#if isApplicant?? && isApplicant>
                    <a class="btn btn-create" href="/resumes/create">
                        <@spring.message "profile.create_a_resume"/>
                    </a>
                    <button class="btn btn-feedbacks">
                        <@spring.message "profile.responses"/>
                    </button>
                <#elseif isEmployer?? && isEmployer>
                    <a class="btn btn-create" href="/vacancies/create">
                        <@spring.message "profile.create_a_vacancy"/>
                    </a>
                    <button class="btn btn-feedbacks">
                        <@spring.message "profile.responses"/>
                    </button>
                </#if>
            </div>
        </div>


        <#if isApplicant?? && isApplicant>
            <h2 class="section-title"><@spring.message "profile.resume"/></h2>
            <#if resumes?? && (resumes?size > 0)>
                <div class="resumes">
                    <#list resumes as resume>
                        <div class="resume-card">
                            <div class="resume-info">
                                <p><strong>${resume.name!'Без названия'}</strong></p>
                                <p><@spring.message "profile.card.update_date"/>: ${resume.updateDate!''}</p>
                            </div>
                            <div class="card-actions">
                                <a class="btn btn-update" href="/resumes/${resume.id!0}/edit">
                                    <@spring.message "profile.card.update"/>
                                </a>
                                <a class="btn btn-red" href="/resumes/${resume.id!0}/delete">
                                    <@spring.message "profile.card.delete"/>
                                </a>
                            </div>
                            <div class="card-footer">
                                <a class="btn btn-detail" href="/resumes/${resume.id!0}">
                                    <@spring.message "profile.card.details"/>
                                </a>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <p style="text-align:center;"><@spring.message "profile.no_resumes"/></p>
            </#if>
        </#if>
    </div>

    <script>
        function previewAvatar() {
            const fileInput = document.getElementById("avatarInput");
            const preview = document.getElementById("avatarPreview");
            const form = document.getElementById("avatarForm");

            if (fileInput.files && fileInput.files[0]) {
                const reader = new FileReader();
                reader.onload = (e) => preview.src = e.target.result;
                reader.readAsDataURL(fileInput.files[0]);
                setTimeout(() => form.submit(), 500);
            }
        }
    </script>

</@main.layout>













