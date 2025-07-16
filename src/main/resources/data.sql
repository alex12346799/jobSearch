create table if not exists users
(
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(100),
                       surname VARCHAR(100),
                       role VARCHAR(50),
                       age INT,
                       email VARCHAR(100) UNIQUE,
                       password VARCHAR(100),
                       phone_number VARCHAR(20),
                       address VARCHAR(255),
                       avatar VARCHAR(255),
                       account_type VARCHAR(50)
);
INSERT INTO users (id, name, surname, role, age, email, password, phone_number, address, avatar, account_type)
VALUES
    (1, 'Иван', 'Иванов', 'APPLICANT', 25,
     'ivan@example.com', '1234', '0555123456', 'г. Бишкек',
     null, 'BASIC'),
    (2, 'ОАО Альфа', 'HR', 'EMPLOYER', 40,
     'hr@alfa.kg', 'admin', '0700111222',
     'г. Бишкек, ул. Советская', null, 'BUSINESS');

create table if not exists resume
(
    id           int auto_increment primary key,
    applicant_Id  int,
    name         VARCHAR(100),
    category_id  INT,
    salary       DOUBLE,
    is_active    BOOLEAN,
    created_date TIMESTAMP,
    update_time  TIMESTAMP

);
INSERT INTO resume (id, applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES (1, 1, 'Java Developer', 1, 120000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 1, 'Frontend Developer', 1, 100000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);