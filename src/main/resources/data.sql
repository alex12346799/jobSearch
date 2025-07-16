create table if not exists users
(
    id           INT PRIMARY KEY,
    name         VARCHAR(100),
    surname      VARCHAR(100),
    role         VARCHAR(50),
    age          INT,
    email        VARCHAR(100) UNIQUE,
    password     VARCHAR(100),
    phone_number VARCHAR(20),
    address      VARCHAR(255),
    avatar       VARCHAR(255),
    account_type VARCHAR(50)
);


create table if not exists resume
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    applicant_Id int,
    name         VARCHAR(100),
    category_id  INT,
    salary       DOUBLE,
    is_active    BOOLEAN,
    created_date TIMESTAMP,
    update_time  TIMESTAMP,
    FOREIGN KEY (applicant_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)

);

create table if not exists category
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(45),
    parent_id INT
);

CREATE TABLE vacancy
(
    id           INT PRIMARY KEY,
    title        VARCHAR(100),
    description  TEXT,
    category_id  INT,
    salary       DOUBLE,
    exp_from     INT,
    exp_to       INT,
    is_active    BOOLEAN,
    employer_id  INT,
    created_date TIMESTAMP,
    update_time  TIMESTAMP,
    FOREIGN KEY (employer_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);
CREATE TABLE respondent_applicant
(
    id           INT PRIMARY KEY,
    resume_id    INT,
    vacancy_id   INT,
    confirmation BOOLEAN,
    create_date  TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(id),
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);