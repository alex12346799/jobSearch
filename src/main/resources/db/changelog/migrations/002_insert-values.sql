
INSERT INTO category (name, parent_id) VALUES ('IT', NULL);
INSERT INTO category (name, parent_id) VALUES ('Finance', NULL);


INSERT INTO users (email, name, surname, role, age, password, phone_number, address, avatar, account_type)
VALUES
    ('john@example.com', 'John', 'Doe', 'APPLICANT', 30, 'pass1', '1234567890', 'NYC', 'avatar1.png', 'STANDARD'),
    ('jane@example.com', 'Jane', 'Smith', 'EMPLOYER', 28, 'pass2', '0987654321', 'LA', 'avatar2.png', 'PREMIUM');


INSERT INTO resume (applicant_id, name, category_id, salary, is_active, created_date, update_date)
VALUES
    (1, 'Java Developer', 1, 60000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Financial Analyst', 2, 55000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO vacancy (title, description, category_id, salary, exp_from, exp_to, is_active, employer_id, created_date, update_date)
VALUES
    ('Senior Java Developer', 'Develop backend systems', 1, 70000, 3, 5, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Junior Accountant', 'Manage company accounts', 2, 45000, 0, 2, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO respondent_applicant (resume_id, vacancy_id, confirmation, create_date)
VALUES
    (1, 1, TRUE, CURRENT_TIMESTAMP),
    (2, 2, FALSE, CURRENT_TIMESTAMP);


INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
VALUES
    (1, 'MIT', 'Computer Science', '2015-09-01', '2019-06-01', 'Bachelor'),
    (2, 'Harvard', 'Finance', '2014-09-01', '2018-06-01', 'Bachelor');


INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES
    (1, 4, 'Google', 'Software Engineer', 'Backend development'),
    (2, 3, 'Deloitte', 'Financial Analyst', 'Financial reporting');
