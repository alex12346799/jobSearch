
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f3f3f3;
        }

        .profile-container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 40px;
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        }

        .profile-title {
            text-align: center;
            font-size: 2rem;
            font-weight: bold;
            margin-bottom: 30px;
            color: #d63031;
        }

        .profile-header {
            display: flex;
            justify-content: space-between;
            gap: 30px;
            flex-wrap: wrap;
            align-items: center;
        }

        .avatar-section img {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            border: 3px solid #74b9ff;
            box-shadow: 0 0 10px rgba(116,185,255,0.6);
            object-fit: cover;
            cursor: pointer;
            transition: .25s;
        }

        .avatar-section img:hover {
            transform: scale(1.05);
        }

        .btn {
            padding: 10px 18px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            cursor: pointer;
            transition: .25s;
            display: inline-block;
        }

        .btn-main {
            background: #4169E1;
            color: #fff;
        }

        .btn-main:hover {
            transform: scale(1.05);
        }

        .btn-gray {
            background: #dfe6e9;
            color: #2d3436;
        }

        .cards-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 25px;
            margin-top: 25px;
        }

        .card-item {
            background: #fff;
            padding: 18px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: .25s;
        }

        .card-item:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.12);
        }

        .card-actions {
            margin-top: 10px;
            display: flex;
            gap: 10px;
        }

        .btn-update {
            background: #a0c4ff;
        }

        .btn-delete {
            background: #ff7675;
            color: #fff;
            width: 100%;
            text-align: center;
            display: block;
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
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </#if>

                    <label for="avatarInput">
                        <img id="avatarPreview"
                             src="<#if user.avatar?? && user.avatar?has_content>
                                /data/images/${user.avatar}
                              <#else>
                                /images/default-avatar.png
                              </#if>"
                             alt="avatar">
                    </label>

                    <input id="avatarInput" name="avatar" type="file" accept="image/*" style="display:none"
                           onchange="previewAvatar()">

                </form>
            </div>


            <div>
                <h2>${user.name!''} ${user.surname!''}</h2>
                <p><strong><@spring.message "profile.age"/>:</strong> ${user.age!'—'}</p>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong><@spring.message "profile.address"/>:</strong> ${user.address!'—'}</p>

                <a class="btn btn-main" href="/user/edit">
                    <@spring.message "profile.edit"/>
                </a>
            </div>


            <div>
                <#if isApplicant?? && isApplicant>
                    <a class="btn btn-main" href="/resumes/create">
                        <@spring.message "profile.create_a_resume"/>
                    </a>
                    <a class="btn btn-gray">
                        <@spring.message "profile.responses"/>
                    </a>
                </#if>

                <#if isEmployer?? && isEmployer>
                    <a class="btn btn-main" href="/vacancies/create">
                        <@spring.message "profile.create_a_vacancy"/>
                    </a>
                    <a class="btn btn-gray">
                        <@spring.message "profile.responses"/>
                    </a>
                </#if>
            </div>

        </div>



        <#if isApplicant?? && isApplicant>
            <h2 style="margin-top:40px;"><@spring.message "profile.resume"/></h2>

            <#if resumes?? && (resumes?size > 0)>
                <div class="cards-grid">
                    <#list resumes as r>
                        <div class="card-item">
                            <p><b>${r.name}</b></p>
                            <p><@spring.message "profile.card.update_date"/>: ${r.updateDate}</p>

                            <div class="card-actions">
                                <a class="btn btn-update" href="/resumes/${r.id}/edit">
                                    <@spring.message "profile.card.update"/>
                                </a>
                                <a class="btn btn-delete" href="/resumes/${r.id}/delete">
                                    <@spring.message "profile.card.delete"/>
                                </a>
                            </div>

                            <a class="btn btn-main" style="width:100%; margin-top:10px"
                               href="/resumes/${r.id}">
                                <@spring.message "profile.card.details"/>
                            </a>
                        </div>
                    </#list>
                </div>

            <#else>
                <p style="text-align:center;"><@spring.message "profile.no_resumes"/></p>
            </#if>
        </#if>


        <#if isEmployer?? && isEmployer>
            <h2 style="margin-top:40px;"><@spring.message "profile.vacancies"/></h2>

            <#if vacancies?? && (vacancies?size > 0)>
                <div class="cards-grid">
                    <#list vacancies as v>
                        <div class="card-item">
                            <p><b>${v.title}</b></p>
                            <p><@spring.message "profile.salary"/>: ${v.salary}</p>
                            <p><@spring.message "profile.card.update_date"/>: ${v.updateDate!'—'}</p>

                            <div class="card-actions">
                                <a class="btn btn-update" href="/vacancies/${v.id}/edit">
                                    <@spring.message "profile.card.update"/>
                                </a>
                                <a class="btn btn-delete" href="/vacancies/${v.id}/delete">
                                    <@spring.message "profile.card.delete"/>
                                </a>
                            </div>

                            <a class="btn btn-main" style="width:100%; margin-top:10px"
                               href="/vacancies/${v.id}">
                                <@spring.message "profile.card.details"/>
                            </a>
                        </div>
                    </#list>
                </div>

            <#else>
                <p style="text-align:center;"><@spring.message "profile.no_vacancies"/></p>
            </#if>
        </#if>

    </div>

    <script>
        function previewAvatar() {
            const input = document.getElementById("avatarInput");
            const preview = document.getElementById("avatarPreview");
            const form = document.getElementById("avatarForm");

            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = e => preview.src = e.target.result;
                reader.readAsDataURL(input.files[0]);

                setTimeout(() => form.submit(), 300);
            }
        }
    </script>

</@main.layout>
