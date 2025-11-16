<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#import "../pages/pages.ftlh" as p>

<@main.layout>
    <style>
        .vacancies-toolbar {
            display: flex;
            align-items: center;
            gap: 12px;
            margin: 20px 0 14px;
            flex-wrap: wrap;
        }


        .vacancies-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 16px;
        }

        @media (max-width: 992px) {
            .vacancies-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 576px) {
            .vacancies-grid {
                grid-template-columns: 1fr;
            }
        }


        .vacancy-card {
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

        .vacancy-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.08);
            border-color: #e6ebf2;
        }

        .vacancy-head {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            gap: 12px;
        }

        .vacancy-title {
            font-size: 1.05rem;
            font-weight: 800;
            color: #1f2937;
            line-height: 1.35;
        }

        .vacancy-category {
            font-size: .9rem;
            color: #6b7280;
        }

        .vacancy-salary {
            font-weight: 800;
            color: #10b981;
            font-size: 1rem;
        }

        .vacancy-meta {
            margin-top: 10px;
            font-size: .9rem;
            color: #6b7280;
        }


        .filters {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
            background: #fff;
            border: 1px solid #eef1f6;
            border-radius: 14px;
            padding: 14px 16px;
            box-shadow: 0 6px 14px rgba(0, 0, 0, 0.05);
        }

        .custom-select,
        .filters input {
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

        .custom-select:hover,
        .filters input:hover {
            border-color: #6c63ff;
            background-color: #fff;
            box-shadow: 0 2px 6px rgba(108, 99, 255, 0.25);
        }

        .custom-select:focus,
        .filters input:focus {
            outline: none;
            border-color: #6c63ff;
            box-shadow: 0 0 6px rgba(108, 99, 255, 0.4);
        }

        .btn {
            padding: 6px 14px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.2s ease-in-out;
        }

        .btn-primary {
            background-color: #6c63ff;
            border: none;
            color: #fff;
        }

        .btn-primary:hover {
            background-color: #594cf5;
            box-shadow: 0 4px 10px rgba(108, 99, 255, 0.35);
        }

        .btn-secondary {
            background-color: #f8fafc;
            border: 1px solid #e5e7eb;
            color: #475569;
        }

        .btn-secondary:hover {
            background-color: #fff;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .empty {
            text-align: center;
            color: #6b7280;
            background: #fff;
            border: 1px dashed #e5e7eb;
            border-radius: 14px;
            padding: 28px;
            margin-top: 20px;
        }

        .ms-auto {
            margin-left: auto;
        }
    </style>

    <div class="vacancies-toolbar">
        <div class="filters">
            <select id="filterCategory" class="custom-select">
                <option value=""><@spring.message"pages.pages_category_all"/></option>
                <#list categories as cat>
                    <option value="${cat.id}">${cat.name}</option>
                </#list>
            </select>

            <input id="salaryFrom" type="number" placeholder="<@spring.message 'pages.vacancies_from_salary'/>"
                   class="custom-select" style="width:150px;">
            <input id="salaryTo" type="number" placeholder="<@spring.message 'pages.vacancies_before_payday'/>" class="custom-select" style="width:150px;">

            <button id="applyFilter" class="btn btn-primary"><@spring.message"pages.vacancies_apply"/></button>
            <button id="clearFilter" class="btn btn-secondary"><@spring.message"pages.vacancies_clear"/></button>
        </div>

        <div class="ms-auto">
            <@p.pager url page/>
        </div>
    </div>


    <#if vacancy?size == 0>
        <div class="empty">Нет вакансий по выбранным параметрам.</div>
    <#else>
        <div class="vacancies-grid" id="vacanciesGrid">
            <#list vacancy as v>
                <a href="vacancies/${v.id}" class="vacancy-card"
                   data-category="${v.category.id}"
                   data-salary="${v.salary?string('0.##')}">

                    <div class="vacancy-head">
                        <div>
                            <h3 class="vacancy-title">${v.title}</h3>
                            <div class="vacancy-category">${v.category.name}</div>
                        </div>
                        <div class="vacancy-salary">${v.salary}</div>
                    </div>

                    <div class="vacancy-meta">
                        <p>${v.description}</p>
                        <p><strong>Работодатель:</strong> ${v.employer.name}</p>
                    </div>
                </a>
            </#list>
        </div>
    </#if>


    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const cards = Array.from(document.querySelectorAll('.vacancy-card'));
            const categorySelect = document.getElementById('filterCategory');
            const salaryFromInput = document.getElementById('salaryFrom');
            const salaryToInput = document.getElementById('salaryTo');
            const applyBtn = document.getElementById('applyFilter');
            const clearBtn = document.getElementById('clearFilter');

            const savedFilter = JSON.parse(localStorage.getItem('vacancyFilter')) || {};
            if(savedFilter.category) categorySelect.value = savedFilter.category;
            if(savedFilter.salaryFrom) salaryFromInput.value = savedFilter.salaryFrom;
            if(savedFilter.salaryTo) salaryToInput.value = savedFilter.salaryTo;

            function applyFilter() {
                const category = categorySelect.value;
                const salaryFrom = salaryFromInput.value ? parseFloat(salaryFromInput.value) : null;
                const salaryTo = salaryToInput.value ? parseFloat(salaryToInput.value) : null;

                localStorage.setItem('vacancyFilter', JSON.stringify({category, salaryFrom, salaryTo}));

                cards.forEach(card => {
                    const rowCategory = card.dataset.category;
                    const rowSalary = parseFloat(card.dataset.salary);
                    let show = true;
                    if(category && String(rowCategory) !== String(category)) show = false;
                    if(salaryFrom !== null && rowSalary < salaryFrom) show = false;
                    if(salaryTo !== null && rowSalary > salaryTo) show = false;
                    card.style.display = show ? '' : 'none';
                });
            }

            function clearFilter() {
                categorySelect.value = '';
                salaryFromInput.value = '';
                salaryToInput.value = '';
                localStorage.removeItem('vacancyFilter');
                cards.forEach(card => card.style.display = '');
            }
            applyFilter();
            applyBtn.addEventListener('click', applyFilter);
            clearBtn.addEventListener('click', clearFilter);
        });
    </script>
</@main.layout>
