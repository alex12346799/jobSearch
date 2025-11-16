<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>


<#function ageText age>
    <#local n   = (age!0)?number>
    <#local loc = (.locale?string?lower_case)!''>
    <#if loc?starts_with("ru")>
        <#local n100 = n % 100>
        <#local n10  = n % 10>
        <#if (n100 >= 11 && n100 <= 14)>
            <#return n + " лет">
        <#elseif (n10 == 1)>
            <#return n + " год">
        <#elseif (n10 >= 2 && n10 <= 4)>
            <#return n + " года">
        </#if>
        <#return n + " лет">
    <#else>
        <#return n + " " + (n == 1)?string("year","years")>
    </#if>
</#function>

<@main.layout>
    <style>

        .resumes-toolbar {
            display: flex;
            align-items: center;
            gap: 12px;
            margin: 20px 0 14px;
        }


        .resumes-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 16px;
        }


        @media (max-width: 992px) {
            .resumes-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 576px) {
            .resumes-grid {
                grid-template-columns: 1fr;
            }
        }

        .resume-card {
            position: relative;
            background: #fff;
            border: 1px solid #eef1f6;
            border-radius: 14px;
            padding: 16px;
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.05);
            text-decoration: none;
            display: block;
            transition: transform .15s ease,
            box-shadow .15s ease,
            border-color .15s ease;
        }


        .resume-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.08);
            border-color: #e6ebf2;
        }


        .resume-head {
            display: flex;
            align-items: flex-start;
            gap: 12px;
        }


        .resume-index {
            width: 34px;
            height: 34px;
            border-radius: 10px;
            background: #f3f4ff;
            color: #5a31d6;
            display: grid;
            place-items: center;
            font-weight: 800;
        }


        .resume-title {
            margin: 0;
            font-size: 1.05rem;
            font-weight: 800;
            color: #1f2937;
            line-height: 1.35;
        }


        .salary {
            margin-left: auto;
            white-space: nowrap;
            font-weight: 800;
            color: #10b981;
            font-size: 1rem;
        }


        .meta {
            margin-top: 8px;
            display: flex;
            flex-wrap: wrap;
            gap: 8px 12px;
            font-size: .92rem;
            color: #6b7280;
        }


        .badges {
            margin-top: 10px;
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }


        .badge-soft {
            background: #f8fafc;
            border: 1px solid #e5e7eb;
            color: #475569;
            border-radius: 999px;
            padding: 4px 10px;
            font-size: .82rem;
            font-weight: 600;
        }


        .empty {
            text-align: center;
            color: #6b7280;
            background: #fff;
            border: 1px dashed #e5e7eb;
            border-radius: 14px;
            padding: 28px;
        }


        .custom-select {
            width: 220px;
            padding: 6px 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
            font-size: 14px;
            transition: all 0.2s ease-in-out;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
            cursor: pointer;
        }


        .custom-select:hover {
            border-color: #6c63ff;
            background-color: #fff;
            box-shadow: 0 2px 6px rgba(108, 99, 255, 0.25);
        }


        .custom-select:focus {
            outline: none;
            border-color: #6c63ff;
            box-shadow: 0 0 6px rgba(108, 99, 255, 0.4);
        }


        .ms-auto {
            margin-left: auto;
        }
    </style>


    <#assign pageObj = (page?is_hash)?then(page, null)>

    <#assign baseUrl = (url!'/resumes')>

    <#assign currentSize = (pageObj?? && (pageObj.size)??)?then(pageObj.size?c, '12')>

    <div class="resumes-toolbar">
        <select class="custom-select" onchange="location.href=this.value">
            <option selected><@spring.message "resumes.sorting"/></option>

            <option value="/resumes/sorted?sort=name&direction=asc&size=${currentSize}">
                <@spring.message "resumes.sort.name"/> (A–Z)
            </option>
            <option value="/resumes/sorted?sort=name&direction=desc&size=${currentSize}">
                <@spring.message "resumes.sort.name"/> (Z–A)
            </option>

            <option value="/resumes/sorted?sort=createdDate&direction=desc&size=${currentSize}">
                <@spring.message "resumes.sort.data"/>
            </option>

            <option value="/resumes/sorted?sort=salary&direction=asc&size=${currentSize}">
                <@spring.message "resumes.sort.salary.asc"/>
            </option>
            <option value="/resumes/sorted?sort=salary&direction=desc&size=${currentSize}">
                <@spring.message "resumes.sort.salary.desc"/>
            </option>
        </select>

        <div class="ms-auto">
            <#if pageObj??>
                <@p.pager url=baseUrl page=pageObj/>
            </#if>
        </div>
    </div>


    <#if !pageObj??>
        <div class="empty">
            Не удалось получить список резюме (объект страницы отсутствует).
        </div>
    <#elseif pageObj.content?size == 0>
        <div class="empty">
            <div class="mb-1"></div>
            <div><@spring.message "resumes.empty.list"!"Пока нет резюме по выбранным параметрам."/></div>
        </div>
    <#else>
        <div class="resumes-grid">
            <#list pageObj.content as resume>
                <a class="resume-card" href="/resumes/${resume.id}" aria-label="Открыть резюме ${resume.name}">
                    <div class="resume-head">
                        <div class="resume-index">${resume?index + 1}</div>

                        <div>
                            <h3 class="resume-title">${resume.name}</h3>

                            <div class="meta">
                                <div>
                                    <div>${resume.applicant.name} ${resume.applicant.surname}</div>
                                    <div>${ageText(resume.applicant.age)}</div>
                                    <div class="badges">
                    <span class="badge-soft">
                      <@spring.message "resumes.th.category"/>: ${resume.category.name}
                    </span>
                                    </div>
                                </div>

                                <span><@spring.message "resumes.th.creation_date"/>: ${resume.formattedCreatedDate}</span>
                                <span><@spring.message "resumes.th.update_date"/>: ${resume.formattedUpdateDate}</span>
                            </div>
                        </div>

                        <div class="salary">${resume.salary}</div>
                    </div>
                </a>
            </#list>
        </div>
    </#if>
</@main.layout>
