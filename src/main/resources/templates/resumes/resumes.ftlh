

<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>

<@main.layout>
    <style>
        .resumes-toolbar {
            display:flex;
            align-items:center;
            gap:12px;
            margin:20px 0 14px;
        }
        .resumes-total {
            color:#6b7280;
            font-size:.95rem;
        }


        .resumes-grid {
            display:grid;
            grid-template-columns: repeat(3, minmax(0,1fr));
            gap:16px;
        }
        @media (max-width: 992px) { .resumes-grid { grid-template-columns: repeat(2,1fr); } }
        @media (max-width: 576px) { .resumes-grid { grid-template-columns: 1fr; } }


        .resume-card {
            position:relative;
            background:#fff;
            border:1px solid #eef1f6;
            border-radius:14px;
            padding:16px 16px 14px;
            box-shadow:0 6px 16px rgba(0,0,0,.05);
            transition:transform .15s ease, box-shadow .15s ease, border-color .15s ease;
        }
        .resume-card:hover {
            transform: translateY(-2px);
            box-shadow:0 12px 24px rgba(0,0,0,.08);
            border-color:#e6ebf2;
        }

        .resume-head {
            display:flex;
            align-items:flex-start;
            gap:12px;
        }
        .resume-index {
            flex:0 0 34px;
            height:34px;
            border-radius:10px;
            background:#f3f4ff;
            color:#5a31d6;
            display:grid;
            place-items:center;
            font-weight:800;
        }
        .resume-title {
            margin:0;
            font-size:1.05rem;
            font-weight:800;
            color:#1f2937;
            line-height:1.35;
        }

        .salary {
            margin-left:auto;
            white-space:nowrap;
            font-weight:800;
            color:#10b981;
            font-size:1rem;
        }

        .meta {
            margin-top:8px;
            display:flex;
            flex-wrap:wrap;
            gap:8px 12px;
            font-size:.92rem;
            color:#6b7280;
        }
        .meta .dot { width:4px; height:4px; border-radius:50%; background:#cbd5e1; align-self:center; }

        .badges {
            margin-top:10px;
            display:flex;
            gap:8px;
            flex-wrap:wrap;
        }
        .badge-soft {
            background:#f8fafc;
            border:1px solid #e5e7eb;
            color:#475569;
            border-radius:999px;
            padding:4px 10px;
            font-size:.82rem;
            font-weight:600;
        }

        .card-footer {
            margin-top:12px;
            display:flex;
            gap:10px;
            align-items:center;
        }
        .btn-more {
            padding:8px 12px;
            border-radius:10px;
            font-weight:700;
            border:1px solid #dde3ee;
            background:#f9fafb;
            color:#374151;
            text-decoration:none;
            transition:background .15s ease, border-color .15s ease, transform .15s ease;
        }
        .btn-more:hover { background:#eef2f7; border-color:#d6dde9; transform: translateY(-1px); }

        .date {
            margin-left:auto;
            font-size:.88rem;
            color:#9aa3ad;
        }

        .empty {
            text-align:center;
            color:#6b7280;
            background:#fff;
            border:1px dashed #e5e7eb;
            border-radius:14px;
            padding:28px;
        }
    </style>


    <div class="resumes-toolbar">
        <select class="form-select" onchange="location.href=this.value">
            <option selected><@spring.message "resumes.sorting"/></option>
            <option value="/resumes/sorted?sort=name&size=${page.getSize()}"><@spring.message "resumes.sort.name"/></option>
            <option value="/resumes/sorted?sort=createdDate&size=${page.getSize()}"><@spring.message "resumes.sort.data"/></option>
            <option value="/resumes/sorted?sort=salary&size=${page.getSize()}"><@spring.message "resumes.sort.salary"/></option>
            <option value="/resumes/sorted?sort=response&size=${page.getSize()}"><@spring.message "resumes.sort.response"/></option>
        </select>


        <div class="ms-auto">
            <@p.pager url page/>
        </div>
    </div>


    <#if page.content?size == 0>
        <div class="empty">
            <div class="mb-1">¯\_(ツ)_/¯</div>
            <div><@spring.message "resumes.empty.list"!"Пока нет резюме по выбранным параметрам."/></div>
        </div>
    <#else>
        <div class="resumes-grid">
            <#list page.content as resume>
                <div class="resume-card">
                    <div class="resume-head">
                        <div class="resume-index">${resume?index + 1}</div>
                        <div>
                            <h3 class="resume-title">${resume.name}</h3>
                            <div class="meta">
                                <span>${resume.applicant.name}</span>
                                <span class="dot"></span>
                                <span>${resume.category.name}</span>
                                <span class="dot"></span>
                                <span><@spring.message "resumes.th.creation_date"/>: ${resume.createdDate}</span>
                                <span class="dot"></span>
                                <span><@spring.message "resumes.th.update_date"/>: ${resume.updateDate}</span>
                            </div>
                        </div>
                        <div class="salary">
                            ${resume.salary}
                        </div>
                    </div>

                    <div class="badges">
                        <span class="badge-soft"><@spring.message "resumes.th.name"/>: ${resume.applicant.name}</span>
                        <span class="badge-soft"><@spring.message "resumes.th.category"/>: ${resume.category.name}</span>
                    </div>

                    <div class="card-footer">
                        <a class="btn-more" href="resumes/${resume.id}">
                            <@spring.message "resumes.more"/>
                        </a>
                        <span class="date">
                            <@spring.message "resumes.th.update_date"/>: ${resume.updateDate}
                        </span>
                    </div>
                </div>
            </#list>
        </div>
    </#if>


    <div class="mt-3">
        <@p.pager url page/>
    </div>
</@main.layout>
