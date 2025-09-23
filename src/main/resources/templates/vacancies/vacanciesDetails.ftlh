<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${vacanciesRequest.title!''}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background: #f4f6fb;
            font-family: 'Segoe UI', Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        .vacancy-container {
            background: #fff;
            padding: 36px;
            border-radius: 18px;
            box-shadow: 0 6px 32px rgba(0,0,0,0.10);
            max-width: 700px;
            width: 100%;
            margin-top: 40px;
        }
        .vacancy-header {
            margin-bottom: 24px;
        }
        .vacancy-title {
            font-size: 1.5rem;
            color: #2d3a4b;
            font-weight: 600;
            margin-bottom: 4px;
        }
        .vacancy-category,
        .vacancy-salary {
            font-size: 1rem;
            color: #6b7c93;
            margin-bottom: 2px;
        }
        .vacancy-info-list {
            margin: 0;
            padding: 0;
            list-style: none;
        }
        .vacancy-info-item {
            display: flex;
            margin-bottom: 14px;
        }
        .vacancy-info-label {
            width: 160px;
            color: #7a8ca3;
            font-weight: 500;
        }
        .vacancy-info-value {
            color: #2d3a4b;
        }
        .vacancy-description {
            margin-top: 18px;
            color: #2d3a4b;
        }
        .vacancy-status {
            margin-top: 12px;
            color: #a0aec0;
            font-size: 0.98rem;
        }
    </style>
</head>
<body>
<div class="vacancy-container">

    <!-- Заголовок вакансии -->
    <div class="vacancy-header">
        <div class="vacancy-title">${vacanciesRequest.title!''}</div>
        <div class="vacancy-category">Категория ID: ${vacanciesRequest.category.name!''}</div>
        <div class="vacancy-salary">Зарплата: ${vacanciesRequest.salary!0} ₽</div>
    </div>

    <!-- Основная информация -->
    <ul class="vacancy-info-list">
        <li class="vacancy-info-item">
            <span class="vacancy-info-label">Опыт работы:</span>
            <span class="vacancy-info-value">
                от ${vacanciesRequest.expFrom!0} до ${vacanciesRequest.expTo!0} лет
            </span>
        </li>
        <li class="vacancy-info-item">
            <span class="vacancy-info-label">Работодатель ID:</span>
            <span class="vacancy-info-value">${vacanciesRequest.employer.name!''}</span>
        </li>
    </ul>

    <!-- Описание вакансии -->
    <div class="vacancy-description">
        <strong>Описание:</strong>
        <p>${vacanciesRequest.description!''}</p>
    </div>

    <!-- Статус вакансии -->
    <div class="vacancy-status">
        Статус: <#if vacanciesRequest.isActive?? && vacanciesRequest.isActive()>Активна<#else>Неактивна</#if>
    </div>

</div>
</body>
</html>
