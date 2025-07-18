INSERT INTO users (id, name, surname, role, age, email, password, phone_number, address, avatar, account_type)
VALUES (1, 'Иван', 'Иванов', 'APPLICANT', 25,
        'ivan@example.com', '1234', '0555123456', 'г. Бишкек',
        null, 'BASIC'),
       (2, 'Ирина', 'HR', 'EMPLOYER', 40,
        'hr@alfa.kg', 'admin', '0700111222',
        'г. Бишкек, ул. Советская', null, 'BUSINESS');

INSERT INTO resume (id, applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES (1, 1, 'Java Developer', 1,
        120000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 1, 'Frontend Developer', 1,
        100000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id)
VALUES (1, 'IT', null),
       (2, 'Маркетинг', null);

INSERT INTO vacancy (id, title, description, category_id, salary, exp_from, exp_to, is_active, employer_id,
                     created_date, update_time)
VALUES (1, 'Junior Java Developer', 'Работа в команде разработки',
        1, 110000, 0, 2, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Маркетолог', 'Поиск и привлечение клиентов',
        2, 90000, 1, 3, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO respondent_applicant (id, resume_id, vacancy_id, confirmation, create_date)
VALUES (1, 1, 1, false, CURRENT_TIMESTAMP);
